package com.gj.cloud.common.user.mapper;

import com.gj.cloud.common.user.bean.UmsMemberBean;
import com.gj.cloud.common.user.bean.UmsMemberExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsMemberMapper {
    long countByExample(UmsMemberExample example);

    int deleteByExample(UmsMemberExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsMemberBean record);

    int insertSelective(UmsMemberBean record);

    List<UmsMemberBean> selectByExample(UmsMemberExample example);

    UmsMemberBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsMemberBean record, @Param("example") UmsMemberExample example);

    int updateByExample(@Param("record") UmsMemberBean record, @Param("example") UmsMemberExample example);

    int updateByPrimaryKeySelective(UmsMemberBean record);

    int updateByPrimaryKey(UmsMemberBean record);
}
