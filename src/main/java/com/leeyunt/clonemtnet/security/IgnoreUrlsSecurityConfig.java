package com.leeyunt.clonemtnet.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * 自定义不进行权限拦截的接口, 如登录, 注册等接口
 *
 * @author leeyunt
 * @since 2020/01/17
 */
@ConfigurationProperties(prefix = "security.ignore")
@Component
@Data
public class IgnoreUrlsSecurityConfig {
    private List<String> urls;
}
