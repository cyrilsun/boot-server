package com.mrsun.bootserver.config;

import com.mrsun.bootserver.security.TokenProvider;
import com.mrsun.bootserver.security.UserDetailServiceImpl;
import com.mrsun.bootserver.security.filter.AuthFilter;
import com.mrsun.bootserver.security.filter.LoginFilter;
import com.mrsun.bootserver.security.handler.AnonymousAuthenticationHandler;
import com.mrsun.bootserver.security.handler.LogoutHandlerImpl;
import com.mrsun.bootserver.security.handler.LogoutSuccessHandlerImpl;
import com.mrsun.bootserver.service.PermissionService;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

/**
 * security config
 *
 * @author sunxiaogang
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailServiceImpl userDetailServiceImpl;
    @Resource
    private AnonymousAuthenticationHandler anonymousAuthenticationHandler;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private TokenProvider tokenProvider;
    @Resource
    private PermissionService permissionService;
    @Resource
    private ApplicationProperties applicationProperties;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 处理认证
     *
     * @param http HttpSecurity
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.exceptionHandling()
                .authenticationEntryPoint(anonymousAuthenticationHandler)
                .and().csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/api/user/logout")
                .addLogoutHandler(new LogoutHandlerImpl(tokenProvider, redissonClient))
                .logoutSuccessHandler(new LogoutSuccessHandlerImpl())
                .and()
                // 登录filter
                .addFilter(new LoginFilter(authenticationManager(), tokenProvider, redissonClient, applicationProperties))
                // 权限filter
                .addFilter(new AuthFilter(authenticationManager(), tokenProvider, redissonClient, permissionService, applicationProperties))
                .httpBasic();
    }

    /**
     * 配置认证
     *
     * @param auth auth
     * @throws Exception 异常
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailServiceImpl).passwordEncoder(passwordEncoder());
    }
}
