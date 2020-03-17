package com.leeyunt.clonemtnet.security;

import com.alibaba.fastjson.JSON;
import com.leeyunt.clonemtnet.exception.StatusEnum;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 自定义返回结果：未登录访问时（code：401）
 *
 * @author leeyunt
 * @since 2020/01/17
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().print(JSON.toJSONString(ResultUtil.ofStatusCode(StatusEnum.ERROR_401)));
        response.getWriter().flush();

    }
}
