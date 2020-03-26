package com.leeyunt.clonemtnet.controller;


import com.leeyunt.clonemtnet.service.UserService;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * <p>
 * 用户管理 前端控制器
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 动态查询
     */
    @PostMapping("/selectUser")
    @ApiOperation(value = "动态查询", notes = "通过各项参数查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID"),
            @ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "password", value = "密码"),
            @ApiImplicitParam(name = "nickname", value = "昵称"),
            @ApiImplicitParam(name = "roleId", value = "角色ID"),
            @ApiImplicitParam(name = "headimgUrl", value = "头像url"),
            @ApiImplicitParam(name = "phone", value = "手机号"),
            @ApiImplicitParam(name = "email", value = "邮箱"),
            @ApiImplicitParam(name = "birthday", value = "生日"),
            @ApiImplicitParam(name = "sex", value = "性别"),
            @ApiImplicitParam(name = "status", value = "状态"),
            @ApiImplicitParam(name = "orderByCase", value = "排序", defaultValue = "create_time"),
            @ApiImplicitParam(name = "desc", value = "排序方式", defaultValue = "1"),
            @ApiImplicitParam(name = "pageNow", value = "页码", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", defaultValue = "10")})
    public ResultUtil selectUser(Integer id, String username, String password, String nickname, Integer roleId, String headimgUrl, String phone, String email, LocalDate birthday, Boolean sex, Boolean status, String orderByCase, Boolean desc, Integer pageNow, Integer pageSize) {
        if (null != orderByCase) {
            if (desc == null) {
                desc = false;
            }
            orderByCase += desc ? " desc" : " asc";
        } else {
            orderByCase = "create_time";
            if (desc == null) {
                desc = false;
            }
            orderByCase += desc ? " desc" : " asc";
        }
        return userService.selectUser(id, username, password, nickname, roleId, headimgUrl, phone, email, birthday, sex, status, orderByCase, desc, pageNow, pageSize);
    }

    /**
     * 添加用户
     */
    @PostMapping("/addUser")
    @ApiOperation(value = "添加用户", notes = "添加记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", defaultValue = "user01", required = true),
            @ApiImplicitParam(name = "password", value = "密码", defaultValue = "123456"),
            @ApiImplicitParam(name = "nickname", value = "昵称", defaultValue = "用户01"),
            @ApiImplicitParam(name = "roleId", value = "角色ID"),
            @ApiImplicitParam(name = "headimgUrl", value = "头像url"),
            @ApiImplicitParam(name = "phone", value = "手机号"),
            @ApiImplicitParam(name = "email", value = "邮箱"),
            @ApiImplicitParam(name = "birthday", value = "生日"),
            @ApiImplicitParam(name = "sex", value = "性别"),
            @ApiImplicitParam(name = "status", value = "状态")})
    public ResultUtil addUser(String username, String password, String nickname, Integer roleId, String headimgUrl, String phone, String email, LocalDate birthday, Boolean sex, Boolean status) {
        //不提供密码,123456为默认密码
        if (null == password) {
            password = "123456";
        }
        return userService.addUser(username, password, nickname, roleId, headimgUrl, phone, email, birthday, sex, status);
    }

    /**
     * 动态更新用户信息
     */
    @PutMapping("/updateUser")
    @ApiOperation(value = "更新用户", notes = "根据id动态更新记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = true),
            @ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "password", value = "密码"),
            @ApiImplicitParam(name = "nickname", value = "昵称"),
            @ApiImplicitParam(name = "roleId", value = "角色ID"),
            @ApiImplicitParam(name = "headimgUrl", value = "头像url"),
            @ApiImplicitParam(name = "phone", value = "手机号"),
            @ApiImplicitParam(name = "email", value = "邮箱"),
            @ApiImplicitParam(name = "birthday", value = "生日"),
            @ApiImplicitParam(name = "sex", value = "性别"),
            @ApiImplicitParam(name = "status", value = "状态")})
    public ResultUtil updateUserById(Integer id, String username, String password, String nickname, Integer roleId, String headimgUrl, String phone, String email, LocalDate birthday, Boolean sex, Boolean status) {
        if (null == id) {
            return ResultUtil.ofFailMsg("用户id不能为空");
        }
        return userService.updateUserById(id, username, password, nickname, roleId, headimgUrl, phone, email, birthday, sex, status);
    }

    /**
     * 根据id查询用户
     */
    @GetMapping("/getUser")
    @ApiOperation(value = "根据id查询", notes = "根据id查询记录")
    @ApiImplicitParam(name = "id", value = "用户id", required = true)
    public ResultUtil getUserById(Integer id) {
        return userService.getUserById(id);
    }

    /**
     * 根据id删除用户
     */
    @DeleteMapping("/removeUser")
    @ApiOperation(value = "根据id删除", notes = "根据id删除记录")
    @ApiImplicitParam(name = "id", value = "用户id", required = true)
    public ResultUtil removeUserById(Integer id) {
        try {
            boolean res = userService.removeById(id);
            if (res) {
                return ResultUtil.ofSuccessMsg("删除成功");
            }
            return ResultUtil.ofFailMsg("删除失败");
        }catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.ofFailMsg("删除User出错！");
        }
    }

}
