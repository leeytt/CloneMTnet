package com.leeyunt.clonemtnet.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * <p>
 * 微信授权 前端控制器
 * </p>
 *
 * @author leeyunt
 * @since 2020-04-12
 */
@Slf4j
@Api(tags = "微信授权")
@RestController
@RequestMapping("/weixin")
public class WeixinController {
    /**
     * shouq
     */
    @GetMapping("auth")
    public void auth(@RequestParam("code") String code) {
        log.info("_______进入微信授权.......");
        log.info("code={}", code);

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxc160a456814049c4&secret=81223d0bcfe607c163eef39b5ec9a784&code="+ code +"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("response={}", response);

    }
}
