package com.gj.cloud.base.work.home.controller;

import com.github.pagehelper.Page;
import com.gj.cloud.base.work.home.bean.HomeBean;
import com.gj.cloud.base.work.home.bean.HomeDTO;
import com.gj.cloud.common.api.CommonResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeControllerImpl implements HomeController {
    @Override
    public void insert(HomeBean home) {

    }

    @Override
    public void update(HomeDTO home) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Page<HomeDTO> list(HomeDTO home) {
        Page<HomeDTO> page = new Page<>();
        List<HomeDTO> homeDTOS = page.stream().toList();
        return null;
    }

    @Override
    public HomeDTO selectById(String id) {
        HomeDTO result = new HomeDTO();
        result.setMsg("欢迎来到首页");
        return result;
    }

    @Override
    public CommonResult<String> homePage() {
        return CommonResult.success("欢迎来到首页");
    }
}
