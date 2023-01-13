package com.mrsun.bootserver.common.exception;

import com.mrsun.bootserver.common.enums.ErrorEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 业务异常
 *
 * @author sunxiaogang
 */
@Data
@NoArgsConstructor
public class BusinessException extends RuntimeException {
    protected int code;
    private String message;

    public BusinessException(ErrorEnum errorEnum) {
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage();
    }
}
