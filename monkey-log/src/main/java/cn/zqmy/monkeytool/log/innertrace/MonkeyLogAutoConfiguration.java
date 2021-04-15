package cn.zqmy.monkeytool.log.innertrace;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 内部追踪日志配置类
 *
 * @author zhuoqianmingyue
 * @Date: 2021/04/15
 * @since 0.2.1
 **/
@Configuration
@EnableConfigurationProperties(MonkeyLogProperties.class)
public class MonkeyLogAutoConfiguration {

    private MonkeyLogProperties monkeyLogProperties;

    public MonkeyLogAutoConfiguration(MonkeyLogProperties monkeyLogProperties) {
        this.monkeyLogProperties = monkeyLogProperties;
    }

    @Bean
    public MonkeyLogAspect innerTraceLogAspect() {
        MonkeyLogAspect monkeyLogAspect = new MonkeyLogAspect();
        monkeyLogAspect.setMonkeyLogProperties(monkeyLogProperties);
        return monkeyLogAspect;
    }
}
