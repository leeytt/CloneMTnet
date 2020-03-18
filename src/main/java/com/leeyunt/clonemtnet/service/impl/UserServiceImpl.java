package com.leeyunt.clonemtnet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leeyunt.clonemtnet.constant.CommonConstant;
import com.leeyunt.clonemtnet.dao.UserDao;
import com.leeyunt.clonemtnet.entity.User;
import com.leeyunt.clonemtnet.exception.StatusEnum;
import com.leeyunt.clonemtnet.jwt.JwtTokenUtil;
import com.leeyunt.clonemtnet.security.UserDetailImpl;
import com.leeyunt.clonemtnet.security.UserDetailServiceImpl;
import com.leeyunt.clonemtnet.service.UserService;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import org.springframework.security.core.context.SecurityContextHolder;
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
                    /*用户名密码验证通过 生成token返回*/
                    UserDetails userDetails = userDetailService.loadUserByUsername(username);
                    String token = String.format("%s %s", CommonConstant.TOKEN_PREFIX, jwtTokenUtil.generateToken(userDetails));
                    HashMap<String, String> data = new HashMap<>();
//                    data.put("userId", String.valueOf(user.getId()));
//                    data.put("username", user.getUsername());
                    data.put("token", token);
                    return ResultUtil.ofSuccess(data);
                }else {
                    return ResultUtil.ofFailMsg("密码错误");
                }
            }
        }
    }

    /**
     * 获取当前登录用户的信息（角色、权限、菜单）
     * */
    @Override
    public ResultUtil getUserInfo() {
        /*1、从spring security上下文获取username*/
        UserDetailImpl userDetail = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetail.getUser().getUsername();
        /*2、根据username去获取此用户信息*/
        Object userInfo = userDao.getUserInfo(username);
        HashMap<String, Object> data = new HashMap<>();
        data.put("userInfo", userInfo);
        /*3、返回用户的信息）*/
        return ResultUtil.ofSuccess(data);
    }

    /**
     * 登出
     */
    @Override
    public ResultUtil logout() {
        SecurityContextHolder.clearContext();
        return ResultUtil.ofStatusCode(StatusEnum.OK_200);
    }


//    @Override
//    public ResultUtil getOneUser(String username) throws UsernameNotFoundException {
//        /*用户信息*/
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("username",username);
//        wrapper.last("limit 1");
//        User user = Optional.ofNullable(userDao.selectOne(wrapper)).orElse(new User());
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("userInfo", user);
//        /*用户角色*/
//        if(ObjectUtils.isNotEmpty(user.getRoleId())){
//            QueryWrapper<Role> ew = new QueryWrapper<>();
//            ew.eq("id",user.getRoleId());
//            ew.last("limit 1");
//            Role role = Optional.ofNullable(roleDao.selectOne(ew)).orElse(new Role());
//            hashMap.put("role", role);
//        }
//        /*用户权限*/
//        /*用户菜单*/
//        return ResultUtil.ofSuccess(hashMap);
//    }

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

}
