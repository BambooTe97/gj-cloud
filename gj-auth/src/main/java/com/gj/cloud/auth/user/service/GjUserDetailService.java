package com.gj.cloud.auth.user.service;

import com.gj.cloud.auth.user.domain.MemberDetails;
import com.gj.cloud.common.user.bean.UmsMemberBean;
import com.gj.cloud.common.user.bean.UmsMemberExample;
import com.gj.cloud.common.user.mapper.UmsMemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Component
public class GjUserDetailService implements UserDetailsService {

    @Autowired
    private UmsMemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(StringUtils.isEmpty(username)) {
            log.warn("用户登陆用户名为空:{}",username);
            throw new UsernameNotFoundException("用户名不能为空");
        }

        UmsMemberBean umsMember = getByUsername(username);

        if(null == umsMember) {
            log.warn("根据用户名没有查询到对应的用户信息:{}",username);
        }

        log.info("根据用户名:{}获取用户登陆信息:{}",username,umsMember);

        return new MemberDetails(umsMember);
    }

    public UmsMemberBean getByUsername(String username) {
        UmsMemberExample example = new UmsMemberExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsMemberBean> memberList = memberMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(memberList)) {
            return memberList.get(0);
        }
        return null;
    }
}
