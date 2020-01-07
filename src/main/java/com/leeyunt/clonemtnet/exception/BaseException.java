package com.leeyunt.clonemtnet.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * 异常基类
 *
 * @author leeyunt
 * @since 2020/01/07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {
    private Integer code;
    private String msg;

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
