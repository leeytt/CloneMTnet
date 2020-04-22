package com.leeyunt.clonemtnet.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * 读取定义的微信配置参数 配置类
 *
 * @author leeyunt
 * @since 2020/04/12
 */
@ConfigurationProperties(prefix = "wechat")
@Component
@Data
public class WechatAccountConfig {
    /**
     * 微信公众平台appID
     */
    private String mpAppId;

    /**
     * 微信公众平台appsecret密钥
     */
    private String mpAppSecret;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 商户证书路径
     */
    private String keyPath;

    /**
     * 微信通知异步地址
     */
    private String notifyUrl;
}
