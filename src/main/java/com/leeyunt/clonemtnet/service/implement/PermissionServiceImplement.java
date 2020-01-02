package com.leeyunt.clonemtnet.service.implement;

import com.leeyunt.clonemtnet.entity.Permission;
import com.leeyunt.clonemtnet.dao.PermissionDao;
import com.leeyunt.clonemtnet.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台权限表 服务实现类
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
@Service
public class PermissionServiceImplement extends ServiceImpl<PermissionDao, Permission> implements PermissionService {

}
