package com.leeyunt.clonemtnet.service.implement;

import com.leeyunt.clonemtnet.entity.User;
import com.leeyunt.clonemtnet.dao.UserDao;
import com.leeyunt.clonemtnet.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 运营后台用户表 服务实现类
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
@Service
public class UserServiceImplement extends ServiceImpl<UserDao, User> implements UserService {

}
