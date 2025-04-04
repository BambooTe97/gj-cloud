package com.gj.cloud.base.demo.controller;

import com.gj.cloud.base.demo.bean.DemoBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author houby@email.com
 * @date 2025/1/6
 */
@RequestMapping("/demo")
public interface DemoController {

    @PostMapping("/all")
    List<DemoBean> selectAllList();
}
