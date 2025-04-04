package com.gj.cloud.gjcloudai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GjCloudAiApplication {

    public static void main(String[] args) {
        // 如果要直接访问官网 ChatGPT 需要设置代理
        String proxy = "127.0.0.1"; // 代理IP
        int port = 7890; // 代理端口
        System.setProperty("proxyType", "4");
        System.setProperty("proxyPort", Integer.toString(port));
        System.setProperty("proxyHost", proxy);
        System.setProperty("proxySet", "true");

        SpringApplication.run(GjCloudAiApplication.class, args);
    }

}
