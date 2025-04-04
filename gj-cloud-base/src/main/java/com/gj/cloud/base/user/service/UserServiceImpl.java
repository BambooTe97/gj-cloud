package com.gj.cloud.base.user.service;

import com.gj.cloud.base.user.bean.UserBean;
import com.gj.cloud.base.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserBean> selectAllList() {
        return userMapper.queryAllUser();
    }
}
