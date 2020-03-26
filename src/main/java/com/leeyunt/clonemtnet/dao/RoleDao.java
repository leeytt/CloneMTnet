package com.leeyunt.clonemtnet.dao;

import com.leeyunt.clonemtnet.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台角色表 Mapper 接口
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
public interface RoleDao extends BaseMapper<Role> {
    /**
     * 根据id查询角色
     */
    Role findById(Integer id);

    /**
     * 根据map查询总记录数
     */
    long getRoleCount(Map<String, Object> map);

    /**
     * 根据map动态查询
     */
    List<Role> dynamicSelect(Map<String, Object> map);

    /**
     * 根据角色名去查询
     */
    Object findByRoleName(String roleName);

    /**
     * 动态更新角色
     */
    int dynamicUpdate(Role role);
}
