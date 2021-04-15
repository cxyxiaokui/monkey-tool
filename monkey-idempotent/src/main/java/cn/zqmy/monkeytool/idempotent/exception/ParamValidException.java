package cn.zqmy.monkeytool.idempotent.exception;

/**
 * 参数校验异常
 * @Author: zhuoqianmingyue
 * @Date: 2020/02/10
 * @Description:
 **/
public class ParamValidException extends RuntimeException{
    public ParamValidException() {
    }

    public ParamValidException(String message) {
        super(message);
    }

    public ParamValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
