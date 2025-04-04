package com.gj.cloud.gjcloudai.controller;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author houby@email.com
 * @date 2025/1/13
 */
@RestController
public class FunctionCallController {

    @Autowired
    private OpenAiChatModel chatModel;

    @RequestMapping("/ai/fc")
    public String fc(@RequestParam(value = "msg", defaultValue = "中国有多少叫张三的人") String msg) {
        OpenAiChatOptions ai = OpenAiChatOptions.builder()
                // 设置了实现 Function 接口的 beanName
                .withFunction("locationNameFunction")
//                .withModel("gpt-3.5-turbo")
                .withModel("gpt-4-turbo")
                .build();
        ChatResponse response = chatModel.call(new Prompt(msg, ai));

        return response.getResult().getOutput().getContent();
    }

}
