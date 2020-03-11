package com.leeyunt.clonemtnet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leeyunt.clonemtnet.constant.CommonConstant;
import com.leeyunt.clonemtnet.dao.UserDao;
import com.leeyunt.clonemtnet.entity.User;
import com.leeyunt.clonemtnet.jwt.JwtTokenUtil;
import com.leeyunt.clonemtnet.security.UserDetailServiceImpl;
import com.leeyunt.clonemtnet.service.UserService;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * <p>
 * 运营后台用户表 服务实现类
 * </p>
 *
 * @author leeyunt
 * @since 2020-01-02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Resource
    private UserDao userDao;

    @Resource
    private UserDetailServiceImpl userDetailService;

    @Resource
    private JwtTokenUtil jwtTokenUtil;

//    /**
//     * 登录检查
//     */
//    @Override
//    public ResultUtil checkLogin(String username, String password) {
//        ResultUtil resultUtil = new ResultUtil();
//        if (null == username) {
//            resultUtil.setCode(0);
//            resultUtil.setMsg("用户名不能为空");
//            return resultUtil;
//        }else {
//            User user = userDao.findByUsername(username);
//            if (null == user) {
//                resultUtil.setCode(0);
//                resultUtil.setMsg("用户不存在");
//                return resultUtil;
//            }else {
//                if (user.getPassword().equals(password)) {
//                    /*用户名密码验证通过 获取token*/
//                    UserDetails userDetails = userDetailService.loadUserByUsername(username);
//                    String token = String.format("%s %s", CommonConstant.TOKEN_PREFIX, jwtTokenUtil.generateToken(userDetails));
//                    HashMap<String, String> hashMap = new HashMap<>();
//                    hashMap.put("id", String.valueOf(user.getId()));
//                    hashMap.put("username", user.getUsername());
//                    hashMap.put("token", token);
//                    resultUtil.setCode(200);
//                    resultUtil.setMsg("登录成功");
//                    resultUtil.setData(hashMap);
//                    return resultUtil;
//                }else {
//                    resultUtil.setCode(0);
//                    resultUtil.setMsg("密码错误");
//                    resultUtil.setData(null);
//                    return resultUtil;
//                }
//            }
//        }
//    }


    /**
     * 登录检查
     */
    @Override
    public ResultUtil checkLogin(String username, String password) {
        if (null == username) {
            return ResultUtil.ofFailMsg("用户名不能为空");
        }else {
            User user = userDao.findByUsername(username);
            if (null == user) {
                return ResultUtil.ofFailMsg("用户不存在");
            }else {
                if (user.getPassword().equals(password)) {
                    /*用户名密码验证通过 获取token*/
                    UserDetails userDetails = userDetailService.loadUserByUsername(username);
                    String token = String.format("%s %s", CommonConstant.TOKEN_PREFIX, jwtTokenUtil.generateToken(userDetails));
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", String.valueOf(user.getId()));
                    hashMap.put("username", user.getUsername());
                    hashMap.put("token", token);
                    return ResultUtil.ofSuccess(hashMap);
                }else {
                    return ResultUtil.ofFailMsg("密码错误");
                }
            }
        }
    }
}
