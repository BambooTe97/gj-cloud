package com.gj.cloud.base.user.controller;

import com.gj.cloud.base.user.bean.UserBean;
import com.gj.cloud.base.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;

    @Override
    public List<UserBean> selectAllList() {
        return userService.selectAllList();
    }
}
