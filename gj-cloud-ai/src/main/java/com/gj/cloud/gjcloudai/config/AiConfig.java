package com.gj.cloud.gjcloudai.config;

import com.gj.cloud.gjcloudai.functions.LocationNameFunction;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

/**
 * @author houby@email.com
 * @date 2025/1/13
 */
@Configuration
public class AiConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder) {

        return builder
                // 设置一个默认的系统信息，使ai能够回答某个领域的问题
                .defaultSystem("you are a friendly chat bot that answers")
                .build();
    }

    @Bean
    // 提示问这种类型问题的时候才调用
    @Description("某个地方有多少某个名字的人")
    public Function<LocationNameFunction.Request, LocationNameFunction.Response> locationNameFunction() {
        return new LocationNameFunction();
    }
}
