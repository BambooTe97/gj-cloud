package com.gj.cloud.gjcloudai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author houby@email.com
 * @date 2025/1/13
 */
@RestController
@RequestMapping("/ai")
public class AIController {
    @Autowired
    private ChatClient chatClient;

    @GetMapping("/chat")
    public String generation(@RequestParam(value = "userInput", defaultValue = "你好AI") String userInput) {
        return this.chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }

    @GetMapping(value = "/stream", produces = "text/html;charset=UTF-8")
    public Flux<String> stream(@RequestParam(value = "userInput", defaultValue = "你好AI") String userInput) {
        return chatClient.prompt()
                .user(userInput)
                .stream()
                .content();
    }

    @Autowired
    private ChatModel chatModel;

    @GetMapping("/chat/model")
    public String openAiChatModel(@RequestParam(value = "userInput", defaultValue = "你好AI") String userInput) {
        ChatResponse response = chatModel.call(
                new Prompt(
                        userInput,
                        OpenAiChatOptions.builder()
                                .withModel("gpt-4-o")
                                .withTemperature(0.4)
                                .build()
                ));
        return response.getResult().getOutput().getContent();
    }

    @Autowired
    private OpenAiImageModel openAiImageModel;

    @GetMapping("/textToImg")
    public String textToImg(@RequestParam(value = "userInput", defaultValue = "你好AI") String userInput) {
        ImageResponse response = openAiImageModel.call(
                new ImagePrompt(userInput,
                        OpenAiImageOptions.builder()
                                .withQuality("hd")
                                .withN(1)
                                .withHeight(1024)
                                .withWidth(1024).build())

        );
        return response.getResult().getOutput().getUrl();
    }

}
