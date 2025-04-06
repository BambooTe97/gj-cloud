package com.gj.cloud.base.work.user.service;

import com.github.pagehelper.PageInfo;
import com.gj.cloud.base.work.user.bean.BaseUserBean;
import com.gj.cloud.base.work.user.bean.BaseUserDTO;
import com.gj.cloud.common.api.CommonPage;
import com.gj.cloud.common.api.CommonResult;
import com.gj.cloud.common.user.bean.UmsPermission;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface BaseUserService {

    int insert(BaseUserBean user);

    CommonResult<CommonPage<BaseUserBean>> selectPagination(BaseUserDTO dto, Integer pageSize, Integer pageNum);

    BaseUserBean selectById(Long id);

    void update(Long id, BaseUserBean user);

    void deleteUsers(List<Long> userList);

    /**
     * 根据用户名获取后台管理员
     */
    BaseUserBean getAdminByUsername(String username);
    /**
     * 获取用户所有权限（包括角色权限和+-权限）
     */
    List<UmsPermission> getPermissionList(String adminId);
    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);
}
