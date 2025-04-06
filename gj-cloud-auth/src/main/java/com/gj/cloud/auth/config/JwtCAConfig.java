package com.gj.cloud.auth.config;

import com.gj.cloud.auth.properties.JwtCAProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtCAProperties.class)
public class JwtCAConfig {
}
