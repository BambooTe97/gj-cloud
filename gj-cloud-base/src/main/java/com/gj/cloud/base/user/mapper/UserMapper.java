package com.gj.cloud.base.user.mapper;

import com.gj.cloud.base.user.bean.UserBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author houby@email.com
 * @date 2025/1/6
 */
@Mapper
public interface UserMapper {
    @Insert("insert into demo(id, name, email) values(#{id}, #{name}, #{email})")
    void insert(UserBean bean);

    List<UserBean> queryAllUser();
}
