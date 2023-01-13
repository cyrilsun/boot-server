package com.mrsun.bootserver.security;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import com.mrsun.bootserver.config.ApplicationProperties;
import com.mrsun.bootserver.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * token provider
 *
 * @author sunxiaogang
 */
@Slf4j
@Component
public class TokenProvider {

    private final ApplicationProperties applicationProperties;

    public TokenProvider(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    /**
     * 创建token
     *
     * @param username 用户名
     * @return token
     */
    public String createToken(String username) {
        DateTime now = DateTime.now();
        DateTime newTime = now.offsetNew(DateField.SECOND, applicationProperties.getJwt().getValidityInSeconds());
        Map<String, Object> payload = new HashMap<>();
        //签发时间
        payload.put(JWTPayload.ISSUED_AT, now);
        //过期时间
        payload.put(JWTPayload.EXPIRES_AT, newTime);
        //生效时间
        payload.put(JWTPayload.NOT_BEFORE, now);
        //载荷
        payload.put("username", username);
        return JWTUtil.createToken(payload, applicationProperties.getJwt().getKey().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 解析token获取用户名
     *
     * @param token token
     * @return
     */
    public String parseToken(String token) {
        String replaceToken = token.replaceAll(Constants.TOKEN_BEARER_SPACE, "");
        JWT jwt = JWTUtil.parseToken(replaceToken);
        jwt.setKey(applicationProperties.getJwt().getKey().getBytes(StandardCharsets.UTF_8));
        String username = jwt.getPayloads().getStr("username");
        log.debug("解析token获取用户名:{}", username);
        return username;
    }
}
