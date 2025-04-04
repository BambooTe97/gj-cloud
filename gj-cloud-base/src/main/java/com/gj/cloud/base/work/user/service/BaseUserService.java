package com.gj.cloud.base.work.user.service;

import com.github.pagehelper.PageInfo;
import com.gj.cloud.base.work.user.bean.BaseUserBean;
import com.gj.cloud.base.work.user.bean.BaseUserDTO;

import java.util.List;

public interface BaseUserService {

    int insert(BaseUserBean user);

    PageInfo<BaseUserBean> selectPagination(BaseUserDTO dto);

    BaseUserBean selectById(Long id);

    void update(Long id, BaseUserBean user);

    void deleteUsers(List<Long> userList);
}
