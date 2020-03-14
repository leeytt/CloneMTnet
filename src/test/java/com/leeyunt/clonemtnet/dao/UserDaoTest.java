package com.leeyunt.clonemtnet.dao;

import com.leeyunt.clonemtnet.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserDaoTest {
    @Resource
    private UserDao userDao;

    @Test
    void saveTest() {
        User user = new User();
        user.setUsername("456");
        user.setPassword("123456");
        user.setNickname("leeyunt");
        user.setRoleId(2);
        userDao.insert(user);
    }
}