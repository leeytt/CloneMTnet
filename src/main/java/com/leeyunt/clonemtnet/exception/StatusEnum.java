package com.leeyunt.clonemtnet.exception;

import lombok.Getter;

/**
 *
 * 状态码枚举封装
 *
 * @author leeyunt
 * @since 2020/01/07
 */
@Getter
public enum StatusEnum {
    /**
     * 请求成功
     */
    OK_200(200, "请求成功"),

    /**
     * 请求失败
     */
    FAIL(-1, "请求失败"),

    /**
     * 请求处理异常
     */
    ERROR_400(400, "请求处理异常，请稍后再试"),

    /**
     * 未验证身份
     */
    ERROR_401(401, "请先验证身份"),

    /**
     * 权限不足
     */
    ERROR_403(403, "权限不足，禁止访问"),

    /**
     * 路径错误
     */
    ERROR_404(404, "404 Not Found"),

    /**
     * 服务器异常
     */
    ERROR_500(500, "服务器出错啦"),

    /**
     * 登陆已过期,请重新登陆
     */
    ERROR_20011(20011, "登录已过期，请重新登录");



    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态信息
     */
    private String msg;

    StatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
