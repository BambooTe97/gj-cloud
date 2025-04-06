package com.gj.cloud.base.work.user.mapper;

import com.github.pagehelper.PageInfo;
import com.gj.cloud.base.work.user.bean.BaseUserBean;
import com.gj.cloud.base.work.user.bean.BaseUserDTO;
import com.gj.cloud.base.work.user.bean.UmsAdminExample;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BaseUserMapper {
    /**
     * 新增
     */
    int insert(BaseUserBean user);

    BaseUserBean selectById(Long id);

    /**
     * 删除
     */
    int delete(Long id);

    /**
     * 修改
     */
    int update(BaseUserBean user);

    List<BaseUserBean> selectByExample(UmsAdminExample example);
}