package com.gj.cloud.base.demo.service;

import com.gj.cloud.base.demo.bean.DemoBean;

import java.util.List;

/**
 * @author houby@email.com
 * @date 2025/1/6
 */
public interface DemoService {
    List<DemoBean> selectAllList();
}
