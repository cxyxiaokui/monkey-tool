package cn.zqmy.monkeytool.log.innertrace;

import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * 内部追踪日志 配置类
 *
 * @author zhuoqianmingyue
 * @Date: 2021/04/15
 * @since 0.2.1
 **/
@ConfigurationProperties(prefix = "monkeycloud.log")
public class MonkeyLogProperties {

    private static final String TRACE_ID_KEY =  "monkey_trace_id";

    private String monkeyTraceIdKey = TRACE_ID_KEY;

    public String getMonkeyTraceIdKey() {
        return monkeyTraceIdKey;
    }

    public void setMonkeyTraceIdKey(String monkeyTraceIdKey) {
        this.monkeyTraceIdKey = monkeyTraceIdKey;
    }
}