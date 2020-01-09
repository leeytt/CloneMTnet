package com.leeyunt.clonemtnet.service.implement;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leeyunt.clonemtnet.dao.UserDao;
import com.leeyunt.clonemtnet.entity.User;
import com.leeyunt.clonemtnet.service.UserService;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    @Resource
    private UserDao userDao;

    /**
     * 登陆检查
     */
    @Override
    public ResultUtil checkLogin(String username, String password) {
        ResultUtil resultUtil = new ResultUtil();
        if (null == username) {
            resultUtil.setCode(0);
            resultUtil.setMsg("用户名不能为空");
            return resultUtil;
        }else {
            User user = new User();
            user = userDao.findByUsername(username);
            if (null == user) {
                resultUtil.setCode(0);
                resultUtil.setMsg("用户不存在");
                return resultUtil;
            }else {
                if (user.getPassword().equals(password)) {
                    resultUtil.setCode(200);
                    resultUtil.setMsg("成功");
                    resultUtil.setData(user);
                    return resultUtil;
                }else {
                    resultUtil.setCode(0);
                    resultUtil.setMsg("密码错误");
                    resultUtil.setData(null);
                    return resultUtil;
                }
            }
        }
    }
}
