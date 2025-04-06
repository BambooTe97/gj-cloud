package com.gj.cloud.base.config;

import com.gj.cloud.base.work.user.service.BaseUserService;
import com.gj.cloud.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig extends SecurityConfig {
    @Qualifier("baseUserServiceImpl")
    @Autowired
    private BaseUserService baseUserSerivce;

    @Bean
    public UserDetailsService userDetailsService() {
        //获取登录用户信息
        return username -> baseUserSerivce.loadUserByUsername(username);
    }
}
