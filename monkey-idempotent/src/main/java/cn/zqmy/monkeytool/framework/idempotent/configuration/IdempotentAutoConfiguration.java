package cn.zqmy.monkeytool.framework.idempotent.configuration;

import cn.hutool.core.util.StrUtil;
import cn.zqmy.monkeytool.framework.idempotent.properties.IdempotentProperties;
import cn.zqmy.monkeytool.framework.idempotent.DefaultTokenServiceImpl;
import cn.zqmy.monkeytool.framework.idempotent.IdempotentInterceptor;
import cn.zqmy.monkeytool.framework.idempotent.IdempotentTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 接口幂等自动配置类
 *
 * @Author: zhuoqianmingyue
 * @Date: 2020/03/26
 * @Description:
 **/
@Configuration
@EnableConfigurationProperties(IdempotentProperties.class)
public class IdempotentAutoConfiguration implements WebMvcConfigurer {

    @Value("${spring.application.name:NA}")
    private String applicationName;

    private IdempotentProperties idempotentProperties;

    public IdempotentAutoConfiguration(IdempotentProperties idempotentProperties) {
        this.idempotentProperties = idempotentProperties;
    }

    @Bean
    @ConditionalOnMissingBean(IdempotentTokenService.class)
    public IdempotentTokenService idempotentTokenService() {
        DefaultTokenServiceImpl defaultTokenService = new DefaultTokenServiceImpl();
        String tokenPrefix = idempotentProperties.getTokenPrefix();
        if (StrUtil.isBlank(tokenPrefix)) {
            idempotentProperties.setTokenPrefix(applicationName);
        }
        defaultTokenService.setIdempotentProperties(idempotentProperties);
        return defaultTokenService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(idempotentInterceptor()).order(idempotentProperties.getIdempotentInterceptOrderSn()).addPathPatterns("/**");
    }

    @Bean
    public IdempotentInterceptor idempotentInterceptor() {
        return new IdempotentInterceptor();
    }
}
