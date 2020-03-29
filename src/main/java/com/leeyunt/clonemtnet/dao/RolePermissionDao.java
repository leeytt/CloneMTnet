package com.leeyunt.clonemtnet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leeyunt.clonemtnet.entity.RolePermission;

import java.util.List;

/**
 * <p>
 * 角色-权限关联表 Mapper 接口
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
public interface RolePermissionDao extends BaseMapper<RolePermission> {
    /**
     * 根据角色id查询权限列表
     */
    List<RolePermission> findByRoleId(Integer id);

    /**
     * 获取数量
     */
    int getCount(Integer id);

    /**
     * 根据角色id删除所有角色-权限关联
     */
    int deleteByRoleId(Integer id);
}
