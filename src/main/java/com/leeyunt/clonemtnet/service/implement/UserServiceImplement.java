package com.leeyunt.clonemtnet.service.implement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leeyunt.clonemtnet.dao.RoleDao;
import com.leeyunt.clonemtnet.dao.UserDao;
import com.leeyunt.clonemtnet.entity.Role;
import com.leeyunt.clonemtnet.entity.User;
import com.leeyunt.clonemtnet.security.UserDetailImpl;
import com.leeyunt.clonemtnet.security.UserDetails;
import com.leeyunt.clonemtnet.service.UserService;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Resource
    private RoleDao roleDao;

    /**
     * 根据用户名查找User
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*查询数据库,通过username查询用户信息*/
        UserDetailImpl userDetail = new UserDetailImpl();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        wrapper.last("limit 1");
        User user = Optional.ofNullable(userDao.selectOne(wrapper)).orElse(new User());
        userDetail.setUser(user);
        /*查询角色信息*/
        if(ObjectUtils.isNotEmpty(user.getId())){
            QueryWrapper<Role> ew = new QueryWrapper<>();
            ew.last(String.format("inner join user_role ur on id = ur.role_id where ur.user_id = %s",user.getId()));
            List<Role> roles = Optional.ofNullable(roleDao.selectList(ew)).orElse(new ArrayList<>());
            userDetail.setRoleList(roles);
        }
        return userDetail;
    }

    /**
     * 登录检查
     */
    @Override
    public ResultUtil checkLogin(String username, String password) {
        ResultUtil resultUtil = new ResultUtil();
        if (null == username) {
            resultUtil.setCode(0);
            resultUtil.setMsg("用户名不能为空");
            return resultUtil;
        }else {
            User user = userDao.findByUsername(username);
            if (null == user) {
                resultUtil.setCode(0);
                resultUtil.setMsg("用户不存在");
                return resultUtil;
            }else {
                if (user.getPassword().equals(password)) {
                    resultUtil.setCode(200);
                    resultUtil.setMsg("登录成功");
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
