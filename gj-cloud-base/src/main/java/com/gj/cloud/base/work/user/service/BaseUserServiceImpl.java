package com.gj.cloud.base.work.user.service;

import com.github.pagehelper.PageHelper;
import com.gj.cloud.base.work.user.bean.AdminUserDetails;
import com.gj.cloud.base.work.user.bean.BaseUserBean;
import com.gj.cloud.base.work.user.bean.BaseUserDTO;
import com.gj.cloud.base.work.user.bean.UmsAdminExample;
import com.gj.cloud.base.work.user.mapper.BaseUserMapper;
import com.gj.cloud.common.api.CommonPage;
import com.gj.cloud.common.api.CommonResult;
import com.gj.cloud.common.user.bean.UmsPermission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("baseUserServiceImpl")
public class BaseUserServiceImpl implements BaseUserService {

    @Autowired
    private BaseUserMapper mapper;

    @Override
    @Transactional
    public int insert(BaseUserBean baseUser) {
        return mapper.insert(baseUser);
    }

    @Override
    public CommonResult<CommonPage<BaseUserBean>> selectPagination(BaseUserDTO dto, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        UmsAdminExample example = new UmsAdminExample();
        UmsAdminExample.Criteria criteria = example.createCriteria();

        String name = dto.getUserName();
        if (!StringUtils.isEmpty(name)) {
            criteria.andUsernameLike("%" + name + "%");
            example.or(example.createCriteria().andNickNameLike("%" + name + "%"));
        }
        List<BaseUserBean> adminList = mapper.selectByExample(example);
        return CommonResult.success(CommonPage.restPage(adminList));
    }

    @Override
    public BaseUserBean selectById(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public void update(Long id, BaseUserBean user) {
        mapper.update(user);
    }

    @Override
    public void deleteUsers(List<Long> userList) {
        userList.forEach(id -> mapper.delete(id));
    }

    @Override
    public BaseUserBean getAdminByUsername(String username) {
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<BaseUserBean> adminList = mapper.selectByExample(example);
        if (adminList != null && !adminList.isEmpty()) {
            return adminList.get(0);
        }
        return null;
    }

    @Override
    public List<UmsPermission> getPermissionList(String adminId) {
        return List.of();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        //获取用户信息
        BaseUserBean admin = getAdminByUsername(username);
        if (admin != null) {
            List<UmsPermission> permissionList = getPermissionList(admin.getId());
            return new AdminUserDetails(admin, permissionList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
