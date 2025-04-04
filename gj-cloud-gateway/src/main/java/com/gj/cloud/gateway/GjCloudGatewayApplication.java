package com.gj.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class GjCloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GjCloudGatewayApplication.class, args);
	}

}
