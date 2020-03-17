package com.leeyunt.clonemtnet.security;

import com.alibaba.fastjson.JSON;
import com.leeyunt.clonemtnet.exception.StatusEnum;
import com.leeyunt.clonemtnet.utils.ResultUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 自定义返回结果：没有权限访问时（code：403）
 *
 * @author leeyunt
 * @since 2020/01/17
 */
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().print(JSON.toJSONString(ResultUtil.ofStatusCode(StatusEnum.ERROR_403)));
        response.getWriter().flush();
    }
}
