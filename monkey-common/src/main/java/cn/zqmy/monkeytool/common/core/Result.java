package cn.zqmy.monkeytool.common.core;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;

/**
 * 统一API响应结果封装
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 *
 **/
public class Result<T> {

    @ApiModelProperty(value = "HTTP响应码或自定义错误Code")
    private int code;
    @ApiModelProperty(value = "响应信息")
    private String msg;
    @ApiModelProperty(value = "返回对象")
    private T data;


    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        return this;
    }


    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
