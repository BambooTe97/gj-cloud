package com.gj.cloud.base.user.controller;

import com.gj.cloud.base.user.bean.UserBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/user")
public interface UserController {

    @GetMapping("/all")
    List<UserBean> selectAllList();
}
