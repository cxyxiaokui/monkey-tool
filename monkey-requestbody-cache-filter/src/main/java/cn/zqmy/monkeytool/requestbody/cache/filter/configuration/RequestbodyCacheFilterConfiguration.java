package cn.zqmy.monkeytool.requestbody.cache.filter.configuration;

import cn.zqmy.monkeytool.requestbody.cache.filter.RequestBodyCacheFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * RequestBody Cache Filter 配置类
 *
 * @Author: zhuoqianmingyue
 * @Date: 2020/04/15
 * @Description:
 **/
@Configuration
public class RequestbodyCacheFilterConfiguration {

    /**
     * RequestBody Cache Filter 加入 Spring 容器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean requestBodyCacheFilter() {

        FilterRegistrationBean registration = new FilterRegistrationBean();
        RequestBodyCacheFilter requestBodyCacheFilter = new RequestBodyCacheFilter();
        registration.setFilter(requestBodyCacheFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registration;
    }
}
