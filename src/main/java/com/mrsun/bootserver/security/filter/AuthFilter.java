package com.mrsun.bootserver.security.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.mrsun.bootserver.common.ResponseUtil;
import com.mrsun.bootserver.common.Result;
import com.mrsun.bootserver.config.ApplicationProperties;
import com.mrsun.bootserver.config.CacheKeyConstants;
import com.mrsun.bootserver.config.Constants;
import com.mrsun.bootserver.security.TokenProvider;
import com.mrsun.bootserver.service.PermissionService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限过滤器
 *
 * @author sunxiaogang
 */
public class AuthFilter extends BasicAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;
    private RedissonClient redissonClient;
    private PermissionService permissionService;
    private ApplicationProperties applicationProperties;


    /**
     * 构造器
     *
     * @param authenticationManager authenticationManager
     * @param tokenProvider         tokenProvider
     * @param redissonClient        redissonClient
     * @param permissionService     permissionService
     * @param applicationProperties applicationProperties
     */
    public AuthFilter(AuthenticationManager authenticationManager,
                      TokenProvider tokenProvider,
                      RedissonClient redissonClient,
                      PermissionService permissionService,
                      ApplicationProperties applicationProperties) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.redissonClient = redissonClient;
        this.permissionService = permissionService;
    }

    /**
     * 权限操作
     *
     * @param request  请求
     * @param response 响应
     * @param chain    调用链
     * @throws IOException      IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(Constants.TOKEN_AUTHORIZATION);
        if (StrUtil.isNotEmpty(token)) {
            String username = tokenProvider.parseToken(token);
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            RMap<String, List<GrantedAuthority>> mapCache = redissonClient.getMap(CacheKeyConstants.USER_LOGIN_CACHE);
            // 从缓存中获取权限信息
            if (mapCache.isExists() && CollectionUtil.isNotEmpty(mapCache.get(username))) {
                grantedAuthorities = mapCache.get(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, token, grantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                chain.doFilter(request, response);
                return;
            }
        }
        ResponseUtil.out(response, Result.error(401, "无权限"));
    }
}
