package cn.zqmy.monkeytool.common.exception;

/**
 * 自定义错误码异常
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 **/
public class CustomErrorCodeException extends RuntimeException {

    /**
     * 自定义错误码
     */
    private Integer errorCode;

    public Integer getErrorCode() {
        return errorCode;
    }

    public CustomErrorCodeException(Integer errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public CustomErrorCodeException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
