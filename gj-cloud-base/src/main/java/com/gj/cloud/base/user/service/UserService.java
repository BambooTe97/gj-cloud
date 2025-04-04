package com.gj.cloud.base.user.service;

import com.gj.cloud.base.user.bean.UserBean;

import java.util.List;

public interface UserService {
    List<UserBean> selectAllList();
}
