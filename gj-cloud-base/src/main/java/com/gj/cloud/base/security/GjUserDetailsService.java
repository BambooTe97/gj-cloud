package com.gj.cloud.base.security;

import com.gj.cloud.autoconfigure.redis.RedisManager;
import com.gj.cloud.base.work.user.service.BaseUserService;
import com.gj.cloud.common.exception.BusinessException;
import com.gj.cloud.security.bean.SecurityOAuth2User;
import com.gj.cloud.security.core.GjUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GjUserDetailsService implements UserDetailsService {
    private final BaseUserService baseUserService;
    private final RedisManager redisManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityOAuth2User securityUser = baseUserService.selectSecurityOAuth2User(username);

        if (securityUser == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        if (securityUser.getLockedMaxMinutes() > 0) {
            throw new BusinessException("账户已经被锁定");
        }

        String password = securityUser.getPassword();
        if (redisManager.isExists("customize_grant_type", username)) {
            password = "{bcrypt}" + new BCryptPasswordEncoder().encode("123456");
            redisManager.evict("customize_grant_type", username);
        }


        return new GjUser(User.withUsername(securityUser.getLoginId())
                .password(password)
                .accountExpired(securityUser.isAccountExpired())
                .accountLocked(securityUser.isAccountLocked())
                .credentialsExpired(false)
                .disabled(securityUser.isDisabled())
                .authorities(new String[0])
                .build(), securityUser.getLoginName(), securityUser.getOrgId(), securityUser.getOrgName());
    }
}
