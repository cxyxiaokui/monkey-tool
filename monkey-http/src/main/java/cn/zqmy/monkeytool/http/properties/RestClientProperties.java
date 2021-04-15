package cn.zqmy.monkeytool.http.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * Rest Client 配置类
 * @Author: zhuoqianmingyue
 * @Date: 2020/11/20
 * @Description:
 **/
@ConfigurationProperties(prefix = "monkeycloud.rest")
public class RestClientProperties {

    private static final int DEFAULT_TIMEOUT = 1000;

    private final int DEFAULT_MAX_PERROUTE = 10;

    private final int DEFAULT_MAX_TOTAL = 500;

    private final int DEFAULT_TRYTIMES = 3;
    /**
     * 超时时间
     */
    private Integer timeout = DEFAULT_TIMEOUT;

    /**
     * 设置每个路由的并发数
     */
    private Integer maxPerRoute = DEFAULT_MAX_PERROUTE;

    /**
     * 最大连接数
     */
    private Integer maxTotal = DEFAULT_MAX_TOTAL;

    /**
     * 重试次数
     */
    private Integer tryTimes = DEFAULT_TRYTIMES;

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getMaxPerRoute() {
        return maxPerRoute;
    }

    public void setMaxPerRoute(Integer maxPerRoute) {
        this.maxPerRoute = maxPerRoute;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getTryTimes() {
        return tryTimes;
    }

    public void setTryTimes(Integer tryTimes) {
        this.tryTimes = tryTimes;
    }
}
