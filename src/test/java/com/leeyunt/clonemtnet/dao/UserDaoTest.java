package com.leeyunt.clonemtnet.dao;

import com.leeyunt.clonemtnet.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class UserDaoTest {
    @Resource
    private UserDao userDao;

    @Test
    void saveTest() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setNickname("nickname");
        user.setRoleId(2);
        user.setHeadimgUrl("");
        user.setPhone("15223505812");
        user.setEmail("924721970@qq.com");
        user.setSex(true);
        user.setStatus(true);
        int res = userDao.insert(user);
        System.out.println("res: " + res);
    }

    @Test
    void getUserInfoTest() {
        Object userInfo = userDao.getUserInfo("aaa");
        System.out.println(userInfo);
    }

    @Test
    void selectById() {
        User res = userDao.selectById(1);
        System.out.println("res: " + res);
    }

    @Test
    void updateById() {
        User user = new User();
        user.setId(20);
        user.setUsername("username11111");
        user.setPassword("password");
        user.setNickname("nickname");
        user.setRoleId(2);
        user.setHeadimgUrl("");
        user.setPhone("15223505812");
        user.setEmail("924721970@qq.com");
        user.setSex(true);
        user.setStatus(true);
        int res = userDao.updateById(user);
        System.out.println("res: " + res);
    }

    @Test
    void selectByMap() {
        Map<String, Object> map = new HashMap<>();
            map.put("role_id", 1);
            map.put("status", 1);
        List<User> list = userDao.selectByMap(map);
        System.out.println("list: " + list);
    }

    @Test
    void getUserCount() {
        Map<String, Object> map = new HashMap<>();
        map.put("role_id", 1);
        map.put("status", 1);
        long count = userDao.getUserCount(map);
        System.out.println("count: " + count);
    }
}