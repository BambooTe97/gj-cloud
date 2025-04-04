package com.gj.cloud.base.work.user.service;

import com.github.pagehelper.PageInfo;
import com.gj.cloud.base.work.user.bean.BaseUserBean;
import com.gj.cloud.base.work.user.bean.BaseUserDTO;
import com.gj.cloud.base.work.user.mapper.BaseUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PageInfo<BaseUserBean> selectPagination(BaseUserDTO dto) {
        return null;
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

}
