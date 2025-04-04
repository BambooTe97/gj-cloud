package com.gj.cloud.security.config;

import com.gj.cloud.security.component.JwtAuthenticationTokenFilter;
import com.gj.cloud.security.component.RestAuthenticationEntryPoint;
import com.gj.cloud.security.component.RestfulAccessDeniedHandler;
import com.gj.cloud.security.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 对SpringSecurity的配置的扩展，支持自定义白名单资源路径和查询用户逻辑
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 通过 SecurityFilterChain 去配置 HttpSecurity
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 禁用 csrf 保护
//        http.csrf().disable()
//                .formLogin().disable() // 禁用表单
//                .logout().disable() // 禁用登出
//                .sessionManagement().disable()
//                .headers().disable();
//                .cors().disable();  // 跨域请求
        http.formLogin(Customizer.withDefaults());

        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/user/login").permitAll() // 只有 /user/login 的 url 可以任意访问请求
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/**").hasRole("USER")
                 // 任何请求都需要身份验证
                .anyRequest().authenticated())
                // 异常处理
                .exceptionHandling(Customizer.withDefaults())
//                .authenticationEntryPoint(restAuthenticationEntryPoint())
//                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public IgnoreUrlsConfig ignoreUrlsConfig() {
        return new IgnoreUrlsConfig();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }
    @Bean
    public RestfulAccessDeniedHandler restfulAccessDeniedHandler() {
        return new RestfulAccessDeniedHandler();
    }

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }
}
