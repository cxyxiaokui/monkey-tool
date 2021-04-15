package cn.zqmy.monkeytool.log.innertrace;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * 内部追踪日志 配置类
 *
 * @author zhuoqianmingyue
 * @Date: 2021/04/15
 * @since 0.2.1
 **/
@ConfigurationProperties(prefix = "inner.trace.log")
public class MonkeyLogProperties {

    private static final String INNER_TRACE_ID_KEY =  "bsy_trace_id";

    private String innerTarceIdKey = INNER_TRACE_ID_KEY;

    public String getInnerTarceIdKey() {
        return innerTarceIdKey;
    }

    public void setInnerTarceIdKey(String innerTarceIdKey) {
        this.innerTarceIdKey = innerTarceIdKey;
    }
}