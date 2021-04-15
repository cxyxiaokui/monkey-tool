package cn.zqmy.monkeytool.common.exception;

/**
 * 服务（业务）异常如“ 账号或密码错误 ”
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 **/
public class ServiceException extends RuntimeException {

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
