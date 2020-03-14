package com.leeyunt.clonemtnet.jwt;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.leeyunt.clonemtnet.constant.CommonConstant;
import com.leeyunt.clonemtnet.security.UserDetailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * jwt过滤设置,辅助security进行认证
 *
 * @author leeyunt
 * @since 2020/01/17
 */
@Slf4j
@Component
public class    JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(jwtProperties.getTokenHeader());
        /*token必须以Beare开头*/
        if (ObjectUtils.isNotEmpty(authHeader) && authHeader.startsWith(CommonConstant.TOKEN_PREFIX)) {
            /*截取token*/
            final String authToken = authHeader.substring(CommonConstant.TOKEN_PREFIX.length() );
            /*从token中获取到username*/
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            if (ObjectUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
                /*获取到用户信息*/
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                /*校验token是否过期*/
                if (jwtTokenUtil.validateToken(authToken,userDetails)) {
                    log.info("token未过期,token:[{}],用户名:[{}]",authToken,username);
                    /*构建spring security认证需要的数据,存入上下文中*/
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
