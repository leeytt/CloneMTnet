package com.leeyunt.clonemtnet.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leeyunt.clonemtnet.entity.User;

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

    /**
     * 获取当前登录用户的信息（角色、权限、菜单）
     * @return
     */
    JSONObject getUserInfo(String username);
}
