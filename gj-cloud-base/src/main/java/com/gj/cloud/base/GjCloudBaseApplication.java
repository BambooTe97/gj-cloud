package com.gj.cloud.base;

import com.gj.cloud.base.work.user.bean.BaseUserBean;
import com.gj.cloud.base.work.user.controller.BaseUserController;
import com.gj.cloud.base.work.user.mapper.BaseUserMapper;
import com.gj.cloud.base.work.user.service.BaseUserService;
import com.gj.cloud.common.spring.SpringContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GjCloudBaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(GjCloudBaseApplication.class, args);
    }

}
