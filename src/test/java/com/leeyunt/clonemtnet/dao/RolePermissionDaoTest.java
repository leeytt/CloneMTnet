package com.leeyunt.clonemtnet.dao;

import com.leeyunt.clonemtnet.entity.RolePermission;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class RolePermissionDaoTest {
    @Resource
    private RolePermissionDao rolePermissionDao;

 @Test
    void getMenusIdByRoleId() {
     List<RolePermission> list = rolePermissionDao.findByRoleId(2);
     System.out.println(list);
    }
}