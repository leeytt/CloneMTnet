package com.leeyunt.clonemtnet.exception;

/**
 *
 * JSON异常
 *
 * @author leeyunt
 * @since 2020/01/07
 */
public class JsonException extends BaseException {

    public JsonException(StatusEnum statusEnum) {
        super(statusEnum);
    }

    public JsonException(Integer code, String msg) {
        super(code, msg);
    }
}
