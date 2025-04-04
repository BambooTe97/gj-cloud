package com.gj.cloud.auth.user.domain;

import com.gj.cloud.common.user.bean.UmsMemberBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;

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
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return umsMember.getStatus()==1;
    }
    public UmsMemberBean getUmsMember() {
        return umsMember;
    }
}
