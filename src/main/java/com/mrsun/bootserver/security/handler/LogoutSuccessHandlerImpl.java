package com.mrsun.bootserver.security.handler;

import com.mrsun.bootserver.common.ResponseUtil;
import com.mrsun.bootserver.common.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注销成功处理类
 *
 * @author sunxiaogang
 */
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ResponseUtil.out(response, Result.success());
    }
}
