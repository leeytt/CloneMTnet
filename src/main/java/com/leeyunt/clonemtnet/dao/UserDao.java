package com.leeyunt.clonemtnet.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leeyunt.clonemtnet.entity.User;

import java.util.List;
import java.util.Map;

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


    /**
     * 根据id查询用户
     */
    User findById(Integer id);

    /**
     * 根据map动态查询
     */
    List<User> dynamicSelect(Map<String, Object> map);

    /**
     * 根据map查询总记录数
     */
    long getUserCount(Map<String, Object> map);

    /**
     * 根据id动态更新
     */
    int dynamicUpdate(User user);
}
