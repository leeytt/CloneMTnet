package com.leeyunt.clonemtnet.service;

import com.leeyunt.clonemtnet.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leeyunt.clonemtnet.utils.ResultUtil;

/**
 * <p>
 * 后台权限表 服务类
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 动态查询
     */
    ResultUtil selectPermission(Integer id, Integer parentId, String menuCode, String menuName, Integer sort, String permissionCode, String permissionName, Boolean requiredPermission, String orderByCase, Boolean desc, Integer pageNow, Integer pageSize);

    /**
     * 添加权限
     */
    ResultUtil addPermission(Integer parentId, String menuCode, String menuName, Integer sort, String permissionCode, String permissionName, Boolean requiredPermission);

    /**
     * 动态更新权限
     */
    ResultUtil updatePermissionById(Integer id, Integer parentId, String menuCode, String menuName, Integer sort, String permissionCode, String permissionName, Boolean requiredPermission);

    /**
     * 根据id查询权限
     */
    ResultUtil getPermissionById(Integer id);

    /**
     * 获取权限树
     */
    ResultUtil selectPermissionTree();
}
