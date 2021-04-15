package cn.zqmy.monkeytool.idempotent.common;

import org.springframework.http.HttpStatus;

/**
 * 响应结果生成工具
 *
 * @Author: zhuoqianmingyue
 * @Date: 2020/03/26
 * @Description:
 **/
public class ResultGenerator {

    private ResultGenerator() {}

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    /**
     * 生成成功响应结果（无数据）
     *
     * @return Result
     */
    public static Result genSuccessResult() {
        return new Result()
                .setCode(HttpStatus.NO_CONTENT.value())
                .setMsg(DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * 生成成功响应结果（有数据）
     *
     * @param data 请求响应的结果数据
     * @return Result 统一API响应结果
     */
    public static <T> Result genSuccessResult(T data) {
        return new Result().setCode(HttpStatus.OK.value()).setMsg(DEFAULT_SUCCESS_MESSAGE).setData(data);
    }

    /**
     * 生成异常响应
     *
     * @param message 异常信息描述
     * @return Result 统一API响应结果
     */
    public static Result genFailResult(String message) {
        return new Result()
                .setCode(HttpStatus.BAD_REQUEST.value())
                .setMsg(message);
    }
    /**
     * 生成异常响应
     *
     * @param message 异常信息描述
     * @return Result 统一API响应结果
     */
    public static Result genFailResult(HttpStatus httpStatus,String message) {
        return new Result()
                .setCode(httpStatus.value())
                .setMsg(message);
    }
    /**
     * 生成异常响应
     *
     * @param code HTTP响应码或自定义错误Code
     * @param message 异常信息描述
     * @return Result 统一API响应结果
     */
    public static Result genFailResult(Integer code,String message) {
        return new Result()
                .setCode(code)
                .setMsg(message);
    }
}
