package cn.zqmy.monkeytool.http.configuration;

import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import cn.zqmy.monkeytool.http.properties.RestClientProperties;
import org.apache.http.client.HttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RestClient 自动配置类
 *
 * @Author: zhuoqianmingyue
 * @Date: 2020/11/20
 * @Description: 生成带线程池的 HttpClient
 **/
@Configuration
@EnableConfigurationProperties(RestClientProperties.class)
public class RestClientAutoConfiguration {

    private RestClientProperties httpClientProperties;

    public RestClientAutoConfiguration(RestClientProperties httpClientProperties) {
        this.httpClientProperties = httpClientProperties;
    }

    @Bean
    public HttpClient httpClient() throws HttpProcessException {

        HCB hcb = HCB.custom()
                .timeout(httpClientProperties.getTimeout())
                .pool(httpClientProperties.getMaxTotal(), httpClientProperties.getMaxPerRoute())
                .ssl()
                .retry(httpClientProperties.getTryTimes());

        return hcb.build();
    }
}
