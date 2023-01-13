package com.mrsun.bootserver.common;

import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description Result
 *
 * @author sunxiaogang
 * @since 2022-11-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private int code;
    private Boolean success;
    private String message;
    private T data;

    /**
     * 成功
     *
     * @return 返回结果
     */
    public static <T> Result<T> success() {
        return Result.success("操作成功", null);
    }

    /**
     * 成功
     *
     * @param data 返回数据
     * @return 返回结果
     */
    public static <T> Result<T> success(T data) {
        return Result.success("操作成功", data);
    }

    /**
     * 成功
     *
     * @param message 参数
     * @param data    参数
     * @return 结果
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(HttpStatus.HTTP_OK, true, message, data);
    }


    /**
     * 失败
     *
     * @return 结果
     */
    public static <T> Result<T> error() {
        return Result.error(HttpStatus.HTTP_INTERNAL_ERROR, "操作失败", null);
    }

    /**
     * 失败
     *
     * @param code    返回码
     * @param message 返回消息
     * @return 返回结果
     */
    public static <T> Result<T> error(int code, String message) {
        return Result.error(code, message, null);
    }

    /**
     * 失败
     *
     * @param code    返回码
     * @param message 返回消息
     * @param data    返回数据
     * @return 返回结果
     */
    public static <T> Result<T> error(int code, String message, T data) {
        return new Result<>(code, false, message, data);
    }

    /**
     * 成功
     *
     * @param rows 影响数据行数
     * @return 返回结果
     */
    public static <T> Result<T> success(Integer rows) {
        return rows > 0 ? success() : error();
    }
}
