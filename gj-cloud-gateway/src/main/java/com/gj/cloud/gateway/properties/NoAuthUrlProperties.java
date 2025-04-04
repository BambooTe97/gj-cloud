package com.gj.cloud.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashSet;

@Data
@ConfigurationProperties("gj.gateway")
public class NoAuthUrlProperties {

    private LinkedHashSet<String> skipUrls;
}
