package com.leeyunt.clonemtnet.dao;

import com.leeyunt.clonemtnet.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class RoleDaoTest {
    @Resource
    private RoleDao roleDao;

    @Test
    void getRoleCount() {
        Map<String, Object> map = new HashMap<>();
//        map.put("roleName", "普通用户");
        map.put("status", 1);
        long count = roleDao.getRoleCount(map);
        System.out.println("count: " + count);
    }

    @Test
    void dynamicSelect() {
        Map<String, Object> map = new HashMap<>();
//        map.put("roleName", "普通用户");
        map.put("status", 1);
        List<Role> list = roleDao.dynamicSelect(map);
        System.out.println("list: " + list);
    }

    @Test
    void findByRoleName() {
        roleDao.findByRoleName("系统管理");
    }

    @Test
    void dynamicUpdate() {
        Role role = new Role();
        role.setId(4);
        role.setRoleName("老师");
        role.setDescription("我是老师");
        roleDao.dynamicUpdate(role);
    }
}