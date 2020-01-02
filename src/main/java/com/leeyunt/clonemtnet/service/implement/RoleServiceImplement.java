package com.leeyunt.clonemtnet.service.implement;

import com.leeyunt.clonemtnet.entity.Role;
import com.leeyunt.clonemtnet.dao.RoleDao;
import com.leeyunt.clonemtnet.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台角色表 服务实现类
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
@Service
public class RoleServiceImplement extends ServiceImpl<RoleDao, Role> implements RoleService {

}
