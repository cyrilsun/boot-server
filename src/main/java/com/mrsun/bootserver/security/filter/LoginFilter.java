package com.mrsun.bootserver.security.filter;

import cn.hutool.core.map.MapUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrsun.bootserver.common.ResponseUtil;
import com.mrsun.bootserver.common.Result;
import com.mrsun.bootserver.common.enums.ErrorEnum;
import com.mrsun.bootserver.common.exception.BusinessException;
import com.mrsun.bootserver.config.ApplicationProperties;
import com.mrsun.bootserver.config.CacheKeyConstants;
import com.mrsun.bootserver.config.Constants;
import com.mrsun.bootserver.entity.User;
import com.mrsun.bootserver.security.CustomerUserDetails;
import com.mrsun.bootserver.security.TokenProvider;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 登录过滤器
 *
 * @author sunxiaogang
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private TokenProvider tokenProvider;
    private RedissonClient redissonClient;
    private ApplicationProperties applicationProperties;


    public LoginFilter(AuthenticationManager authenticationManager1,
                       TokenProvider tokenProvider,
                       RedissonClient redissonClient,
                       ApplicationProperties applicationProperties) {
        this.authenticationManager = authenticationManager1;
        this.tokenProvider = tokenProvider;
        this.redissonClient = redissonClient;
        this.applicationProperties = applicationProperties;
        // 允许get/post请求
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/user/login", HttpMethod.POST.name()));
    }

    /**
     * 执行认证
     *
     * @param request  请求
     * @param response 响应
     * @return 权限
     * @throws AuthenticationException 异常
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = objectMapper.readValue(request.getInputStream(), User.class);
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
                            new ArrayList<>())
            );
            return authenticate;
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(ErrorEnum.CREDENTIALS_ERROR);
        }
    }

    /**
     * 认证成功后调用
     *
     * @param request    请求
     * @param response   响应
     * @param chain      调用链
     * @param authResult 权限结果
     * @throws IOException      IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomerUserDetails customerUserDetails = (CustomerUserDetails) authResult.getPrincipal();
        String username = customerUserDetails.getUsername();
        String token = tokenProvider.createToken(username);
        // 存入redis username: authorities
        RMap<String, List<GrantedAuthority>> mapCache = redissonClient.getMap(CacheKeyConstants.USER_LOGIN_CACHE);
        mapCache.put(username, customerUserDetails.getAuthorities());
        mapCache.expire(applicationProperties.getJwt().getValidityInSeconds(), TimeUnit.SECONDS);
        HashMap<String, Object> map = MapUtil.newHashMap(1);
        map.put("token", Constants.TOKEN_BEARER_SPACE + token);
        ResponseUtil.out(response, Result.success(map));
    }

    /**
     * 认证失败后调用
     *
     * @param request  请求
     * @param response 响应
     * @param failed   认证失败
     * @throws IOException      IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        if (failed instanceof BadCredentialsException) {
            ResponseUtil.out(response, Result.error(402, ErrorEnum.USERNAME_PASSWORD_ERROR.getMessage()));
        }
        ResponseUtil.out(response, Result.error(401, failed.getMessage()));
    }
}
