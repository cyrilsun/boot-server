package com.mrsun.bootserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * application config param
 *
 * @author sunxiaogang
 */
@Data
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    private JwtConfig jwt;

    @Data
    public static class JwtConfig {
        private String key;
        private Integer validityInSeconds;
    }
}
