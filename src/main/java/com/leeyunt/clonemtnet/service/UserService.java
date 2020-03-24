package com.leeyunt.clonemtnet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leeyunt.clonemtnet.entity.User;
import com.leeyunt.clonemtnet.utils.ResultUtil;

import java.time.LocalDate;

/**
 * <p>
 * 运营后台用户表 服务类
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
public interface UserService extends IService<User> {
    /**
     * 登录检查
     */
    ResultUtil checkLogin(String username, String password);

    /**
     * 获取当前登录用户的信息（角色、权限、菜单）
     */
    ResultUtil getUserInfo();

    /**
     * 登出
     */
    ResultUtil logout();

    /**
     * 添加用户
     */
    ResultUtil addUser(String username, String password, String nickname, Integer roleId, String headimgUrl, String phone, String email, LocalDate birthday, Boolean sex, Boolean status);

    /**
     * 动态更新用户信息
     */
    ResultUtil updateUserById(Integer id, String username, String password, String nickname, Integer roleId, String headimgUrl, String phone, String email, LocalDate birthday, Boolean sex, Boolean status);

    /**
     * 根据id查询用户
     */
    ResultUtil getUserById(Integer id);

    /**
     * 动态查询
     */
    ResultUtil selectUser(Integer id, String username, String password, String nickname, Integer roleId, String headimgUrl, String phone, String email, LocalDate birthday, Boolean sex, Boolean status, String orderByCase, Boolean desc, Integer pageNow, Integer pageSize);
}
