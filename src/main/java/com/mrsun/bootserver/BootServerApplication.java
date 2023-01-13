package com.mrsun.bootserver;

import com.mrsun.bootserver.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class BootServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootServerApplication.class, args);
    }

}
