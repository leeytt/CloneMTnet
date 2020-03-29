package com.leeyunt.clonemtnet.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leeyunt.clonemtnet.entity.Permission;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 后台权限表 Mapper 接口
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
public interface PermissionDao extends BaseMapper<Permission> {
    /**
     * 根据id查询权限
     */
    Permission findById(Integer id);

    /**
     * 根据map查询总记录数
     */
    long getPermissionCount(Map<String, Object> map);

    /**
     * 动态查询
     */
    List<Permission> dynamicSelect(Map<String, Object> map);

    /**
     * 动态更新权限
     */
    int dynamicUpdate(Permission permission);

    /**
     * 查询总记录数
     */
    long getCount();

    /**
     * 获取所有权限
     */
    List<Permission> findAll();

}
