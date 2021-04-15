package cn.zqmy.monkeytool.common.core;

/**
 * 响应结果生成工具
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 **/
public class ResultGenerator {
    private ResultGenerator() {
    }

    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    /**
     * 生成无数据返回成功响应
     *
     * @return
     */
    public static <T> Result<T> genSuccessResult() {
        return new Result<T>()
                .setCode(ResultCode.SUCCESS)
                .setMsg(DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * 生成有数据返回成功响应
     *
     * @param data 请求响应的数据结果
     * @return
     */
    public static <T> Result<T> genSuccessResult(T data) {
        return new Result<T>()
                .setCode(ResultCode.SUCCESS)
                .setMsg(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    /**
     * 生成异常响应
     *
     * @param message 异常信息
     * @return
     */
    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMsg(message);
    }
    /**
     * 生成异常响应
     *
     * @param message 异常信息
     * @return
     */
    public static Result genFailResult(ResultCode resultCode,String message) {
        return new Result()
                .setCode(resultCode)
                .setMsg(message);
    }
    /**
     * 生成异常响应
     *
     * @param message 异常信息
     * @return
     */
    public static Result genFailResult(int code,String message) {
        return new Result()
                .setCode(code)
                .setMsg(message);
    }
}
