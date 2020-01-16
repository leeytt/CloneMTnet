package com.leeyunt.clonemtnet.dao;

import com.leeyunt.clonemtnet.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 运营后台用户表 Mapper 接口
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
public interface UserDao extends BaseMapper<User> {
    /**
     * 登录检查
     */
    User findByUsername(String username);
}
