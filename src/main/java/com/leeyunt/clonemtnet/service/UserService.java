package com.leeyunt.clonemtnet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leeyunt.clonemtnet.entity.User;
import com.leeyunt.clonemtnet.security.UserDetails;
import com.leeyunt.clonemtnet.utils.ResultUtil;

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

    UserDetails loadUserByUsername(String username);

}
