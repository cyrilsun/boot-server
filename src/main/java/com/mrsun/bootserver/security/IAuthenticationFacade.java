package com.mrsun.bootserver.security;

import org.springframework.security.core.Authentication;

/**
 * 权限门面
 *
 * @author sunxiaogang
 */
public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
