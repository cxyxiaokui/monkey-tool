package cn.zqmy.monkeytool.framework.idempotent.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;

/**
 * 接口幂等配置信息类
 *
 * @Author: zhuoqianmingyue
 * @Date: 2020/10/09
 * @Description:
 **/
@ConfigurationProperties(prefix = "monkeycloud.idempotent")
public class IdempotentProperties {

    /**
     * TOKEN 默认的过期时间：TOKEN_DEFAULT_EXPIRATION_TIME = 60 * 60 * 4L
     */
    public final static Long TOKEN_DEFAULT_EXPIRATION_TIME = 60 * 60 * 4L;

    /**
     * TOKEN 前缀 默认是项目名称
     */
    private String tokenPrefix;


    /**
     * TOKEN 失效时间 单位毫秒默认是 4小时
     */
    private Long tokenExpiredSecond = TOKEN_DEFAULT_EXPIRATION_TIME;

    /**
     * 失败 HTTP 状态码
     */
    private int errorHttpStatus = HttpStatus.OK.value();

    /**
     * 鉴权拦截器的序号的默认值
     */
    private static final int DEFAULT_IDEMPOTENT_INTERCEPTOR_ORDER_SN = 1;
    /**
     * 鉴权拦截器的序号
     */
    private int idempotentInterceptOrderSn = DEFAULT_IDEMPOTENT_INTERCEPTOR_ORDER_SN;

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    public Long getTokenExpiredSecond() {
        return tokenExpiredSecond;
    }

    public void setTokenExpiredSecond(Long tokenExpiredSecond) {
        this.tokenExpiredSecond = tokenExpiredSecond;
    }

    public int getErrorHttpStatus() {
        return errorHttpStatus;
    }

    public void setErrorHttpStatus(int errorHttpStatus) {
        this.errorHttpStatus = errorHttpStatus;
    }

    public int getIdempotentInterceptOrderSn() {
        return idempotentInterceptOrderSn;
    }

    public void setIdempotentInterceptOrderSn(int idempotentInterceptOrorderSn) {
        this.idempotentInterceptOrderSn = idempotentInterceptOrorderSn;
    }
}
