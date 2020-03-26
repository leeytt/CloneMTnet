package com.leeyunt.clonemtnet.dao;

import com.leeyunt.clonemtnet.entity.Permission;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class PermissionDaoTest {
    @Resource
    private PermissionDao permissionDao;
    @Test
    void findById() {
        permissionDao.findById(2);
    }

    @Test
    void getPermissionCount() {
        Map<String, Object> map = new HashMap<>();
        map.put("parentId", 1);
        long count = permissionDao.getPermissionCount(map);
    }

    @Test
    void dynamicSelect() {
        Map<String, Object> map = new HashMap<>();
        map.put("parentId", 0);
        List<Permission> list = permissionDao.dynamicSelect(map);
    }

    @Test
    void dynamicUpdate() {
        Permission permission = new Permission();
        permission.setId(15);
        permission.setMenuName("订单管理");
        permissionDao.dynamicUpdate(permission);
    }

    @Test
    void findAll() {
        List<Permission> list = permissionDao.findAll();
        System.out.println(list);
    }

    @Test
    void getCount() {
        long count = permissionDao.getCount();
        System.out.println(count);
    }
}