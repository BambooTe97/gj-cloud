package com.gj.cloud.base.work.user.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BaseUserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7348915255286348144L;
    @ApiModelProperty("用户编码")
    private String id;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("单位编码")
    private String orgId;

    @ApiModelProperty("单位名称")
    private String orgName;

    @ApiModelProperty("出生日期")
    private LocalDate birthday;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机")
    private String mobile;

    @ApiModelProperty("电话")
    private String tel;

    @ApiModelProperty("职务")
    private String post;

    @ApiModelProperty("职称")
    private String title;

    @ApiModelProperty("学历")
    private String education;

    @ApiModelProperty("身份证")
    private String idCard;

    @ApiModelProperty("密码修改时间")
    private LocalDateTime passwordUpdatedTime;

    @ApiModelProperty("用户状态 (停用-deprecated 正常-activated 锁定-locked)")
    private String status;

}
