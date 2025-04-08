package com.gj.cloud.security.core;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collections;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class GjUser extends User {
    @Serial
    private static final long serialVersionUID = 1929476565382031879L;

    private String orgId; // 单位编码
    private String orgName; // 单位名称
    private String loginId; // 登录用户编码
    private String loginName; // 登录用户名称

    public GjUser(UserDetails user, String loginName, String orgId, String orgName) {
        super(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(),
                user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());

        this.setLoginId(user.getUsername());
        this.setLoginName(loginName);
        this.setOrgId(orgId);
        this.setOrgName(orgName);
    }

    public GjUser(String username) {
        super(username, "", Collections.emptyList());

        this.setLoginId(username);
        this.setLoginName("");
    }
}
