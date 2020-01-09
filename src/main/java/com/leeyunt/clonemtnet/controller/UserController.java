package com.leeyunt.clonemtnet.controller;


import com.leeyunt.clonemtnet.entity.User;
import com.leeyunt.clonemtnet.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 运营后台用户表 前端控制器
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
@Api(tags = "运营后台用户表")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 获取用户列表
     * @return List<User>
     */
    @GetMapping("/userList")
    @ApiOperation(value = "获取列表", notes = "获取所有记录")
    public List<User> getAll(){
        return userService.list();
    }

    /**
     * 添加用户
     * @return Boolean
     */
    @PostMapping("/addUser")
    @ApiOperation(value = "添加用户", notes = "添加记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "账号", defaultValue = "admin", required = true),
            @ApiImplicitParam(name = "password", value = "密码", defaultValue = "123456", required = true)
    })
    public Boolean addUser(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userService.save(user);
    }

    /**
     * 根据id查询用户
     * @return User
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询", notes = "根据id查询记录")
    @ApiImplicitParam(name = "id", value = "用户id", defaultValue = "1", required = true)
    public User getUserById(@PathVariable("id") Integer id) {
        return userService.getById(id);
    }

    /**
     * 根据id动态更新用户
     */
    @PutMapping("/updateUser")
    @ApiOperation(value = "根据id动态更新", notes = "根据id动态更新记录")
    public Boolean updateUserById(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    /**
     * 根据id删除用户
     * @return boolean
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除", notes = "根据id删除记录")
    @ApiImplicitParam(name = "id", value = "用户id", defaultValue = "99", required = true)
    public boolean deleteUserById(@PathVariable("id") Integer id) {
        return userService.removeById(id);
    }

}
