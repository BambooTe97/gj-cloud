package com.gj.cloud.base.demo.service;

import com.gj.cloud.base.demo.bean.DemoBean;
import com.gj.cloud.base.demo.mapper.DemoMapper;
import com.gj.cloud.common.id.GjIdHelper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author houby@email.com
 * @date 2025/1/6
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoMapper mapper;
    @PostConstruct
    public void insertBatch() {
        DemoBean demoBean = new DemoBean();
        demoBean.setId(GjIdHelper.nextId());
        demoBean.setAge(30);
        demoBean.setUserName("张三");
        demoBean.setEnName("san zhang");
        demoBean.setSex("1");
        demoBean.setCreateName("admin");
        demoBean.setCreateDate(LocalDateTime.now());
        demoBean.setUpdateDate(LocalDateTime.now());
//        mapper.insert(demoBean);
    }

    @Override
    public List<DemoBean> selectAllList() {
        return null;
    }
}
