package com.gj.cloud.base.work.home.controller;

import com.github.pagehelper.Page;
import com.gj.cloud.base.constant.RequestMapperConstant;
import com.gj.cloud.base.work.home.bean.HomeBean;
import com.gj.cloud.base.work.home.bean.HomeDTO;
import org.springframework.web.bind.annotation.*;

@RequestMapping(RequestMapperConstant.USER)
public interface HomeController {

    @PostMapping
    void insert(@RequestBody HomeBean home);

    @PutMapping
    void update(@RequestBody HomeDTO home);

    @DeleteMapping("/{id}")
    void delete(@PathVariable String id);

    @PostMapping("/page")
    Page<HomeDTO> list(@RequestBody HomeDTO home);

    @GetMapping("/{id}")
    HomeDTO selectById(@PathVariable String id);
}
