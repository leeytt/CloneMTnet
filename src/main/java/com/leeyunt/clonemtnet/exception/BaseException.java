package com.leeyunt.clonemtnet.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * 异常基类
 * 本系统使用的自定义异常类
 * 比如在校验参数时,如果不符合要求,可以抛出此异常类
 * 拦截器可以统一拦截此异常,将其中json返回给前端
 *
 * @author leeyunt
 * @since 2020/01/07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {
    private Integer code;
    private String msg;

    /**
     *
     * 调用时可以在任何代码处直接throws这个BaseException,
     * 都会统一被拦截,并封装好json返回给前台
     * @param statusEnum 以错误的statusEnum做参数
     */
    public BaseException(StatusEnum statusEnum) {
        super(statusEnum.getMsg());
        this.code = statusEnum.getCode();
        this.msg = statusEnum.getMsg();
    }

    public BaseException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
