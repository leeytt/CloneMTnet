package com.leeyunt.clonemtnet.exception;

/**
 *
 * 页面异常
 *
 * @author leeyunt
 * @since 2020/01/07
 */
public class PageException extends BaseException {

    public PageException(StatusEnum statusEnum) {
        super(statusEnum);
    }

    public PageException(Integer code, String msg) {
        super(code, msg);
    }
}
