package cn.zqmy.monkeytool.framework.idempotent.common;

import com.alibaba.fastjson.JSON;

/**
 * 统一API响应结果封装
 *
 * @Author: zhuoqianmingyue
 * @Date: 2020/03/26
 **/
public class Result<T> {

    /**
     * HTTP响应码或自定义错误Code
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 返回对象
     */
    private T data;


    Result setCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        return this;
    }


    public Integer getCode() {
        return code;
    }

    Result setCode(int code) {
        this.code = code;
        return this;
    }

    public T getData() {
        return data;
    }

    Result setData(T data) {
        this.data = data;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
