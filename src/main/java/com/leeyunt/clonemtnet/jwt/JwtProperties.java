package com.leeyunt.clonemtnet.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * 读取定义的jwt参数 配置类
 *
 * @author leeyunt
 * @since 2020/01/17
 */
@ConfigurationProperties(prefix = "jwt")
@Component
@Data
public class JwtProperties {
    /**
     * 凭证(校验的变量名)
     * */
    private String tokenHeader;
    /**
     * JWT加解密使用的密钥
     * */
    private String secret;
    /**
     * JWT的过期时间(单位:s)
     * */
    private Integer expiration;

}
