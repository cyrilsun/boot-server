package com.mrsun.bootserver.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 权限门面实现类
 *
 * @author sunxiaogang
 */
@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
