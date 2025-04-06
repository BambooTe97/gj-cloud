package com.gj.cloud.base.work.user.controller;

import com.gj.cloud.base.work.user.bean.BaseUserBean;
import com.gj.cloud.base.work.user.bean.BaseUserDTO;
import com.gj.cloud.base.work.user.service.BaseUserService;
import com.gj.cloud.common.api.CommonPage;
import com.gj.cloud.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class BaseUserControllerImpl implements BaseUserController {
    @Autowired
    private BaseUserService baseUserService;

    @Override
    public int insert(BaseUserBean baseUser) {
        return baseUserService.insert(baseUser);
    }

    @Override
    public CommonResult<CommonPage<BaseUserBean>> selectPagination(BaseUserDTO dto, Integer pageSize, Integer pageNum) {
        return baseUserService.selectPagination(dto, pageSize, pageNum);
    }

    @Override
    public BaseUserBean selectById(Long id) {
        return baseUserService.selectById(id);
    }

    @Override
    public void update(Long id, BaseUserBean user) {
        baseUserService.update(id, user);
    }

    @Override
    public void deleteUsers(List<Long> userIdList) {
        baseUserService.deleteUsers(userIdList);
    }

}
