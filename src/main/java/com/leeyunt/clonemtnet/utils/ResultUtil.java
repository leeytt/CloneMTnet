package com.leeyunt.clonemtnet.utils;

import com.leeyunt.clonemtnet.exception.BaseException;
import com.leeyunt.clonemtnet.exception.StatusEnum;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 统一定义返回结果类
 *
 * @author leeyunt
 * @since 2020/01/07
 */
@Data
public class ResultUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回状态码
     */
    private Integer code;//状态码，成功；1、失败；0

    /**
     * 返回消息
     */
    private String msg;//返回消息

    /**
     * 返回数据
     */
    private Object data;//返回数据

    /**
     * 无参构造函数
     */
    public ResultUtil() {

    }

    /**
     * 全参构造函数
     *
     * @param code    状态码
     * @param msg     返回消息
     * @param data    返回数据
     */
    private ResultUtil(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 构造一个自定义的API返回
     *
     * @param code    状态码
     * @param msg     返回消息
     * @param data    返回数据
     * @return ResultUtil
     */
    public static ResultUtil of(Integer code, String msg, Object data) {
        return new ResultUtil(code, msg, data);
    }

    /**
     * 构造一个成功且带数据的API返回
     *
     * @param data 返回数据
     * @return ResultUtil
     */
    public static ResultUtil ofSuccess(Object data) {
        return ofStatusCode(StatusEnum.OK_200, data);
    }

    /**
     * 构造一个成功且自定义消息的API返回
     *
     * @param msg 返回消息
     * @return ResultUtil
     */
    public static ResultUtil ofSuccessMsg(String msg) {
        return of(StatusEnum.OK_200.getCode(), msg, null);
    }

    /**
     * 构造一个失败且自定义消息的API返回
     *
     * @param msg 返回消息
     * @return ResultUtil
     */
    public static ResultUtil ofFailMsg(String msg) {
        return of(StatusEnum.FAIL.getCode(), msg, null);
    }

    /**
     * 构造一个有状态的API返回
     *
     * @param statusEnum 状态 {@link StatusEnum}
     * @return ResultUtil
     */
    public static ResultUtil ofStatusCode(StatusEnum statusEnum) {
        return ofStatusCode(statusEnum, null);
    }

    /**
     * 构造一个有状态且带数据的API返回
     *
     * @param statusEnum 状态 {@link StatusEnum}
     * @param data   返回数据
     * @return ResultUtil
     */
    public static ResultUtil ofStatusCode(StatusEnum statusEnum, Object data) {
        return of(statusEnum.getCode(), statusEnum.getMsg(), data);
    }

    /**
     * 构造一个异常且带数据的API返回
     *
     * @param t    异常
     * @param data 返回数据
     * @param <T>  {@link BaseException} 的子类
     * @return ResultUtil
     */
    public static <T extends BaseException> ResultUtil ofException(T t, Object data) {
        return of(t.getCode(), t.getMsg(), data);
    }

    /**
     * 构造一个异常且带数据的API返回
     *
     * @param t   异常
     * @param <T> {@link BaseException} 的子类
     * @return ResultUtil
     */
    public static <T extends BaseException> ResultUtil ofException(T t) {
        return ofException(t, null);
    }


}