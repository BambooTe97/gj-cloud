package com.gj.cloud.base.work.user.controller;

import com.gj.cloud.base.constant.RequestMapperConstant;
import com.gj.cloud.base.work.user.bean.BaseUserBean;
import com.gj.cloud.base.work.user.bean.BaseUserDTO;
import com.gj.cloud.common.api.CommonPage;
import com.gj.cloud.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户相关接口")
@RequestMapping(RequestMapperConstant.BASE_USER_PATH)
public interface BaseUserController {

    @PostMapping
    @ApiOperation("新增用户")
    int insert(@ApiParam("用户信息") @RequestBody BaseUserBean user);

    @PostMapping("/queries")
    @ApiOperation("分页查询用户列表")
    CommonResult<CommonPage<BaseUserBean>> selectPagination(@ApiParam("标准用户查询信息") @RequestBody BaseUserDTO dto,
                                                            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum);

    @GetMapping("/{id}")
    @ApiOperation("查询用户详情")
    BaseUserBean selectById(@ApiParam("用户编码") @PathVariable("id") Long id);

    @PutMapping("/{id}")
    @ApiOperation("修改保存用户")
    void update(@ApiParam("用户编码") @PathVariable("id") Long id, @ApiParam("用户信息") @RequestBody BaseUserBean user);

    @DeleteMapping
    @ApiOperation("删除用户")
    void deleteUsers(@ApiParam("用户编码列表") @RequestBody List<Long> userIdList);


//    @PutMapping(value = "/{id}/action/change-password")
//    @ApiOperation("修改用户密码")
//    void changePassword(@ApiParam("用户编码") @PathVariable("id") String id, @ApiParam("用户密码") @RequestBody String password);
//
//    @PutMapping("/action/password-reset")
//    @ApiOperation("初始化用户密码")
//    void resetPassword(@ApiParam("用户编码列表") @RequestBody List<String> userIdList);
//
//    @PutMapping("/action/deprecate")
//    @ApiOperation("停用用户")
//    void deprecate(@ApiParam("用户编码列表") @RequestBody List<String> userIdList);
//
//    @PutMapping("/action/activate")
//    @ApiOperation("启用用户")
//    void activate(@ApiParam("用户编码列表") @RequestBody List<String> userIdList);
//
//    @PutMapping("/action/lock")
//    @ApiOperation("锁定用户")
//    void lock(@ApiParam("用户编码列表") @RequestBody List<String> userIdList);
//
//    @PutMapping("/{id}/action/insert-roles")
//    @ApiOperation("用户添加到指定角色")
//    Long insertRoleUserByUserId(@ApiParam("用户编码") @PathVariable("id") String userId, @ApiParam("角色编码列表") @RequestBody List<Long> roleIdList);
//
//    @PutMapping("/{id}/action/delete-roles")
//    @ApiOperation("从指定角色中删除用户")
//    void deleteRoleUserByUserId(@ApiParam("用户编码") @PathVariable("id") String userId, @ApiParam("角色编码列表") @RequestBody List<Long> roleIdList);
//
//    @PostMapping(value = "/action/validate-password")
//    @ApiOperation("校验用户密码")
//    String validateUserPassword(@ApiParam("用户信息") @RequestBody BaseUserBean user);

}
