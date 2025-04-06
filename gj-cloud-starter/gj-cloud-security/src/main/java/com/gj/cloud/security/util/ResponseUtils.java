package com.gj.cloud.security.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gj.cloud.common.api.CommonResult;
import com.gj.cloud.common.exception.BusinessException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ResponseUtils {
    private static ObjectMapper objectMapper;

    public ResponseUtils(ObjectMapper objectMapper) {
        ResponseUtils.objectMapper = objectMapper;
    }

    public static void responseJson(HttpServletResponse response, CommonResult<?> result){
        try {
            response.getWriter().write(
                    objectMapper.writeValueAsString(result)
            );
        } catch (IOException e) {
            throw new BusinessException("序列化JSON失败" + e.getMessage(), e);
        }
    }
}
