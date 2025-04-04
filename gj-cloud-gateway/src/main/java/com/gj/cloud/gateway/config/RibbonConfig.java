package com.gj.cloud.gateway.config;

import com.gj.cloud.gateway.component.GjRestTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfig {

    @Bean
    public GjRestTemplate restTemplate(DiscoveryClient discoveryClient) {
        return new GjRestTemplate(discoveryClient);
    }
}
