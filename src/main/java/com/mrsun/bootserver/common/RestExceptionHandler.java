package com.mrsun.bootserver.common;

import com.mrsun.bootserver.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 异常处理
 *
 * @author sunxiaogang
 */
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    /**
     * 业务异常处理
     *
     * @param exception 异常
     * @return 返回结果
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> businessExceptionHandler(BusinessException exception) {
        log.info("业务异常处理:{},{}", exception.getCode(), exception.getMessage());
        return Result.error(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(value = {BindException.class})
    public Result<?> argumentNotValidExceptionHandler(Exception e) {
        StringBuilder sb = new StringBuilder("参数错误：[");
        List<ObjectError> list = ((BindException) e).getAllErrors();
        for (ObjectError item : list) {
            sb.append(item.getDefaultMessage()).append(',');
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(']');
        return Result.error(400, sb.toString());
    }
}
