package com.gj.cloud.security.bean;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class SecurityOAuth2User implements Serializable {
    @Serial
    private static final long serialVersionUID = -560943626477220837L;

    private String loginId; // 登录用户编码
    private String loginName; // 登录用户名称
    private String orgId; // 单位编码
    private String orgName; // 单位名称
    private String password; // 登录密码

    private boolean accountExpired = false; // 账号过期
    private boolean accountLocked = false; // 账号锁定
    private boolean disabled = false; // 账号废弃（同deprecated）

    /*
     * 锁定账号的时间（单位：分钟）
     * 当用户登录时，密码输错次数满足锁定阈值时，会在redis中标识该账号被锁定。等待时间超过设定的时间后，该标识自动过期。
     */
    private int lockedMaxMinutes;
}
