package com.leeyunt.clonemtnet.security;

import com.alibaba.fastjson.JSONObject;
import com.leeyunt.clonemtnet.entity.Role;
import com.leeyunt.clonemtnet.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * 实现spring security的userDetail
 *
 * @author leeyunt
 * @since 2020/01/19
 */
@Data
public class UserDetailImpl implements UserDetails {

    /**
     * 用户信息(基本信息)
     */
    private User user;

    /**
     * 角色信息
     */
    private Role role;

    /**
     * 菜单、权限列表
     */
    private JSONObject userInfo;


    /**
     * 返回角色菜单、权限列表
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (Role role : roleList) {
//            authorities.add(new SimpleGrantedAuthority(role.getRoleName() ) );
//        }
        authorities.add(new SimpleGrantedAuthority(role.getRoleName() ) );
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    /*账户是否未过期,过期无法验证*/
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*指定用户是否解锁,锁定的用户无法进行身份验证*/
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*指示是否已过期的用户的凭据(密码),过期的凭据防止认证*/
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*是否可用,禁用的用户不能身份验证*/
    @Override
    public boolean isEnabled() {
        return true;
    }
}
