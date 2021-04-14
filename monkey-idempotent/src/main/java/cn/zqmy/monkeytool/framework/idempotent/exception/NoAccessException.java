package cn.zqmy.monkeytool.framework.idempotent.exception;

/**
 * 无访问权限异常
 *
 * @Author: zhuoqianmingyue
 * @Date: 2021/02/08
 * @Description:
 **/
public class NoAccessException extends RuntimeException {
    public NoAccessException() {
    }

    public NoAccessException(String message) {
        super(message);
    }

    public NoAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
