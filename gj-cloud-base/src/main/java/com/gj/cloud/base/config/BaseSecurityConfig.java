package com.gj.cloud.base.config;

import com.gj.cloud.base.security.GjUserDetailsService;
import com.gj.cloud.base.work.user.service.BaseUserService;
import com.gj.cloud.security.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class BaseSecurityConfig {

    /**
     * 初始化身份验证管理器
     */
//    @Bean
//    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration config,
//                                                           AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
//        return config.getAuthenticationManager();
//    }

}
