package com.gj.cloud.base.work.user.mapper;

import com.gj.cloud.base.work.user.bean.BaseUserBean;
import com.gj.cloud.base.work.user.bean.UmsAdminExample;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BaseUserMapper {
    /**
     * 新增
     */
    int insert(BaseUserBean user);

    BaseUserBean selectById(Long id);

    BaseUserBean queryUserByUsername(String username);

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