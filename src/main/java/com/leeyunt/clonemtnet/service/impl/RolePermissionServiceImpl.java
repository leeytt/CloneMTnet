package com.leeyunt.clonemtnet.service.impl;

import com.leeyunt.clonemtnet.entity.RolePermission;
import com.leeyunt.clonemtnet.dao.RolePermissionDao;
import com.leeyunt.clonemtnet.service.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色-权限关联表 服务实现类
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionDao, RolePermission> implements RolePermissionService {

}
