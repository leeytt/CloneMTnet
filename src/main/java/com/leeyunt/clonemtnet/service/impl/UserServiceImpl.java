package com.leeyunt.clonemtnet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leeyunt.clonemtnet.constant.CommonConstant;
import com.leeyunt.clonemtnet.dao.RoleDao;
import com.leeyunt.clonemtnet.dao.UserDao;
import com.leeyunt.clonemtnet.entity.Role;
import com.leeyunt.clonemtnet.entity.User;
import com.leeyunt.clonemtnet.exception.StatusEnum;
import com.leeyunt.clonemtnet.jwt.JwtTokenUtil;
import com.leeyunt.clonemtnet.security.UserDetailImpl;
import com.leeyunt.clonemtnet.security.UserDetailServiceImpl;
import com.leeyunt.clonemtnet.service.UserService;
import com.leeyunt.clonemtnet.utils.Page;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private RoleDao roleDao;

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

    /**
     * 获取用户列表
     */
    @Override
    public ResultUtil listUser() {
        List<User> list = userDao.listUser();
        if (list != null) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("list", list);
            return ResultUtil.ofSuccess(data);
        }else {
          return ResultUtil.ofFailMsg("未能获取到数据");
        }
    }

    /**
     * 添加用户
     */
    @Override
    public ResultUtil addUser(String username, String password, String nickname, Integer roleId, String headimgUrl, String phone, String email, LocalDate birthday, Boolean sex, Boolean status) {
        if (null != userDao.findByUsername(username)) {
            return ResultUtil.ofFailMsg("用户名已存在");
        } else {
            User user = new User();
            comParam(username, password, nickname, roleId, headimgUrl, phone, email, birthday, sex, status, user);
            try {
                int res = userDao.insert(user);
                if (res == 1) {
                    return ResultUtil.ofSuccessMsg("添加成功");
                } else {
                    return ResultUtil.ofFailMsg("添加失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.ofFailMsg("insertUser出错！");
            }
        }
    }

    /**
     * 动态更新用户信息
     */
    @Override
    public ResultUtil updateUserById(Integer id, String username, String password, String nickname, Integer roleId, String headimgUrl, String phone, String email, LocalDate birthday, Boolean sex, Boolean status) {
        if (null == userDao.selectById(id)) {
            return ResultUtil.ofFailMsg("ID不存在");
        } else {
            if (null != userDao.findByUsername(username)) {
                return ResultUtil.ofFailMsg("用户名已存在");
            }
            User user = new User();
            user.setId(id);
            comParam(username, password, nickname, roleId, headimgUrl, phone, email, birthday, sex, status, user);
            try {
                int res = userDao.updateById(user);
                if (res == 1) {
                    return ResultUtil.ofSuccessMsg("更新成功");
                } else {
                    return ResultUtil.ofFailMsg("更新失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResultUtil.ofFailMsg("dynamicUpdateUser出错！");
            }
        }
    }

    /**
     * 根据id查询用户
     */
    @Override
    public ResultUtil getUserById(Integer id) {
        User user = userDao.findById(id);
        if (null != user) {
            HashMap<String, Object> data = new HashMap<>();
            data.put("user", user);
            if (null != user.getRoleId()) {
                Role role = roleDao.findById(user.getRoleId());
                data.put("role", role);
            }
            return ResultUtil.ofSuccess(data);
        }
        return ResultUtil.ofFailMsg("未能获取id=" + id + "的数据");
    }

    /**
     * 动态查询
     */
    @Override
    public ResultUtil selectUser(Integer id, String username, String nickname, Integer roleId, String headimgUrl, String phone, String email, LocalDate birthday, Boolean sex, Boolean status, String orderByCase, Boolean desc, Integer pageNow, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        if (null != id)
            map.put("id", id);
        if (null!= username)
            map.put("username", username);
        if (null!= nickname)
            map.put("nickname", nickname);
        if (null!= roleId)
            map.put("role_id", roleId);
        if (null!= headimgUrl)
            map.put("headimg_url", headimgUrl);
        if (null!= phone)
            map.put("phone", phone);
        if (null!= email)
            map.put("email", email);
        if (null!= birthday)
            map.put("birthday", birthday);
        if (null!= sex)
            map.put("sex", sex);
        if (null!= status)
            map.put("status", status);
        if (null != orderByCase)
            map.put("orderByCase", orderByCase);
        if (null != pageNow && null != pageSize) {
            map.put("startPos", (pageNow - 1) * pageSize);
            map.put("pageSize", pageSize);
        }
        try {
            List<User> list = userDao.dynamicSelect(map);
            long count = userDao.getUserCount(map);
            if (count ==0 ) {
                return ResultUtil.ofFailMsg("未查询到结果");
            }
            HashMap<String, Object> data = new HashMap<>();
            pageSize = null != pageSize ? pageSize : (int) (count);
            pageNow = null != pageNow ? pageNow : 1;
            if (pageSize != 0) {
                Page page = new Page(count, pageNow);
                page.setPageSize(pageSize);
                data.put("page", page);
            }
            if (list.size() > 0) {
                data.put("list", list);
                return ResultUtil.ofSuccess(data);
            } else {
                return ResultUtil.ofStatusCode(StatusEnum.FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.ofFailMsg("dynamic查询User出错！");
        }
    }


    /**
     * 公共Setter参数
     */
    private void comParam(String username, String password, String nickname, Integer roleId, String headimgUrl, String phone, String email, LocalDate birthday, Boolean sex, Boolean status, User user) {
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setRoleId(roleId);
        user.setHeadimgUrl(headimgUrl);
        user.setPhone(phone);
        user.setEmail(email);
        user.setBirthday(birthday);
        user.setSex(sex);
        user.setStatus(status);
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
