package cn.zqmy.monkeytool.common.exception;
/**
 * 参数无效异常
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 **/
public class ParamsNotValidException extends RuntimeException{
    /**
     * 自定义错误码
     */
    private Integer errorCode;

    public Integer getErrorCode() {
        return errorCode;
    }

    public ParamsNotValidException(Integer errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ParamsNotValidException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
