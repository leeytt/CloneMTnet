package com.leeyunt.clonemtnet.security;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.leeyunt.clonemtnet.dao.RoleDao;
import com.leeyunt.clonemtnet.dao.UserDao;
import com.leeyunt.clonemtnet.entity.Role;
import com.leeyunt.clonemtnet.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 *
 * 实现spring security的userDetailService
 *
 * @author leeyunt
 * @since 2020/01/19
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

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
        /*通过role_id查询角色信息*/
        if(ObjectUtils.isNotEmpty(user.getRoleId())){
            QueryWrapper<Role> ew = new QueryWrapper<>();
            ew.eq("id",user.getRoleId());
            ew.last("limit 1");
            Role role = Optional.ofNullable(roleDao.selectOne(ew)).orElse(new Role());
            userDetail.setRole(role);
        }
        /*获取当前登录用户的信息（角色、权限、菜单）*/
//        JSONObject userInfo = userDao.getUserInfo(username);
//        userDetail.setUserInfo(userInfo);

        return userDetail;
    }
}
