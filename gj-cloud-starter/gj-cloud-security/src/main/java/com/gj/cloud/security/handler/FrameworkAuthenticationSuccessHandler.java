package com.gj.cloud.security.handler;

import com.gj.cloud.common.api.CommonResult;
import com.gj.cloud.security.util.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class FrameworkAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("{} 登录成功", authentication.getPrincipal() + "-" + authentication.getName());
        ResponseUtils.responseJson(response, CommonResult.success("登录成功"));
    }
}
