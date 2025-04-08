package com.gj.cloud.security.config;

import com.gj.cloud.security.component.JwtAuthenticationTokenFilter;
import com.gj.cloud.security.component.RestAuthenticationEntryPoint;
import com.gj.cloud.security.component.RestfulAccessDeniedHandler;
import com.gj.cloud.security.handler.FrameworkAuthenticationFailureHandler;
import com.gj.cloud.security.handler.FrameworkAuthenticationSuccessHandler;
import com.gj.cloud.security.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 对SpringSecurity的配置的扩展，支持自定义白名单资源路径和查询用户逻辑
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 通过 SecurityFilterChain 去配置 HttpSecurity
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(AbstractHttpConfigurer::disable) // 禁用表单
                .csrf(AbstractHttpConfigurer::disable) // 禁用 csrf 保护
                .cors(AbstractHttpConfigurer::disable) // 跨域请求
                .sessionManagement(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable) // 禁用登出
                .headers(AbstractHttpConfigurer::disable)
                // 不使用 session
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/v1/home/info").permitAll()
                        .requestMatchers("/api/v1/users/queries").permitAll()
                        .requestMatchers("/user/login").permitAll() // 只有 /user/login 的 url 可以任意访问请求
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/**").hasRole("USER")
                        // 任何请求都需要身份验证
                        .anyRequest().authenticated())
                // 异常处理
                .exceptionHandling((authEntry) ->
                        authEntry.authenticationEntryPoint(restAuthenticationEntryPoint()))
                // 添加 jwt 认证 token 过滤器
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new FrameworkAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new FrameworkAuthenticationFailureHandler();
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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
