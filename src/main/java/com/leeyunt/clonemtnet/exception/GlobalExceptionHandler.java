package com.leeyunt.clonemtnet.exception;

import com.leeyunt.clonemtnet.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 统一异常处理
 *
 * @author leeyunt
 * @since 2020/01/07
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String DEFAULT_ERROR_VIEW = "error";

    /**
     * 统一 json 异常处理
     *
     * @param exception JsonException
     * @return 统一返回 json 格式
     */
    @ExceptionHandler(value = JsonException.class)
    @ResponseBody
    public ResultUtil jsonErrorHandler(JsonException exception) {
        log.error("【JsonException】:{}", exception.getMsg());
        return ResultUtil.ofException(exception);
    }

    /**
     * 统一 页面 异常处理
     *
     * @param exception PageException
     * @return 统一跳转到异常页面
     */
    @ExceptionHandler(value = PageException.class)
    public ModelAndView pageErrorHandler(PageException exception) {
        log.error("【PageException】:{}", exception.getMsg());
        ModelAndView view = new ModelAndView();
        view.addObject("msg", exception.getMsg());
        view.setViewName(DEFAULT_ERROR_VIEW);
        return view;
    }

}
