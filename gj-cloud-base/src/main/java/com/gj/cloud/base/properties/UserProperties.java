package com.gj.cloud.base.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("gj.user")
public class UserProperties {
    private Integer lockedMaxMinutes;
    private Long maxFailedLoginAttempts = 3L;
}
