package com.gj.cloud.auth.user.domain;

import com.gj.cloud.common.user.bean.UmsMemberBean;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

@Getter
public class MemberDetails implements UserDetails {
    @Serial
    private static final long serialVersionUID = -689866517002747312L;
    private final UmsMemberBean umsMember;

    public MemberDetails(UmsMemberBean umsMember) {
        this.umsMember = umsMember;
    }

    /**
     * 权限列表
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("TEST"));
    }

    @Override
    public String getPassword() {
        return umsMember.getPassword();
    }

    @Override
    public String getUsername() {
        return umsMember.getUsername();
    }

    @Override
    public boolean isEnabled() {
        return umsMember.getStatus()==1;
    }
}
