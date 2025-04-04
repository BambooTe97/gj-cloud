package com.gj.cloud.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * jwt 证书配置
 */
@Data
@ConfigurationProperties("gj.jwt")
public class JwtCAProperties {
    /**
     * 证书名称
     */
    private String keyPairName;

    /**
     * 证书别名
     */
    private String keyPairAlias;

    /**
     * 证书私钥
     */
    private String keyPairSecret;

    /**
     * 证书存储密钥
     */
    private String keyPairStoreSecret;
}
