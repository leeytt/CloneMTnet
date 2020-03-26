package com.leeyunt.clonemtnet.service;

import com.leeyunt.clonemtnet.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leeyunt.clonemtnet.utils.ResultUtil;

/**
 * <p>
 * 后台角色表 服务类
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
public interface RoleService extends IService<Role> {
    /**
     * 动态查询
     */
    ResultUtil selectRole(Integer id, String roleName, String description, Boolean status, String orderByCase, Boolean desc, Integer pageNow, Integer pageSize);

    /**
     * 添加角色
     */
    ResultUtil addRole(String roleName, String description, Boolean status);

    /**
     * 动态更新角色
     */
    ResultUtil updateRoleById(Integer id, String roleName, String description, Boolean status);

    /**
     * 根据id查询角色
     */
    ResultUtil getRoleById(Integer id);
}
