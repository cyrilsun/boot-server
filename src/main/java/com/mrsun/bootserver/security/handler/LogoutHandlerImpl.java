package com.mrsun.bootserver.security.handler;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.mrsun.bootserver.config.CacheKeyConstants;
import com.mrsun.bootserver.config.Constants;
import com.mrsun.bootserver.security.TokenProvider;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 注销处理实现类
 *
 * @author sunxiaogang
 */
public class LogoutHandlerImpl implements LogoutHandler {

    private TokenProvider tokenProvider;

    private RedissonClient redissonClient;

    /**
     * 构造器
     *
     * @param tokenProvider  tokenProvider
     * @param redissonClient redissonClient
     */
    public LogoutHandlerImpl(TokenProvider tokenProvider, RedissonClient redissonClient) {
        this.tokenProvider = tokenProvider;
        this.redissonClient = redissonClient;
    }

    /**
     * 注销时执行业务
     *
     * @param request        请求
     * @param response       响应
     * @param authentication 权限
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 1.请求头获取token
        String token = request.getHeader(Constants.TOKEN_AUTHORIZATION);
        // 2.解析token
        String username = tokenProvider.parseToken(token);
        // 3.清除缓存中权限数据
        if (StrUtil.isNotEmpty(username)) {
            RMap<String, List<GrantedAuthority>> mapCache = redissonClient.getMap(CacheKeyConstants.USER_LOGIN_CACHE);
            if (mapCache.isExists() && CollectionUtil.isNotEmpty(mapCache.get(username))) {
                mapCache.remove(username);
            }
        }
    }

}
