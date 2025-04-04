package com.gj.cloud.base.demo.mapper;

import com.gj.cloud.base.demo.bean.DemoBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author houby@email.com
 * @date 2025/1/6
 */
@Mapper
public interface DemoMapper {
    @Insert("insert into demo(id, username, enname, age, sex, address, createname, createdate, updatedate) values(#{id}, #{userName}, #{enName}, #{age}, #{sex}, #{address}, #{createName}, #{createDate}, #{updateDate})")
    void insert(DemoBean bean);
}
