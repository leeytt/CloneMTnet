package com.leeyunt.clonemtnet.security;

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
     * 用户角色列表
     */
    private List<Role> roleList;


    /**
     * 返回角色列表(暂定为权限),名称
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

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
