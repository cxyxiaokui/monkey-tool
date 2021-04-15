package cn.zqmy.monkeytool.core.filters;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * API 鉴权配置
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @Description:
 **/
@ConfigurationProperties(prefix = "monkeycloud.cors")
public class MonkeyCorsProperties {

    /**
     * 指定预检请求的有效期默认值为 一天
     */
    public static final Long ACCESS_CONTROL_MAX_AGE_DEFAUTL = 86400L;

    /**
     * 指定预检请求的有效期默认值为 一天
     */
    public static final String FRAME_OPTIONS_DEFAUTL = "SAMEORIGIN";

    /**
     * 用来指定预检请求的有效期，单位为秒
     */
    private Long accessControlMaxAge = ACCESS_CONTROL_MAX_AGE_DEFAUTL;

    /**
     * 允许跨域域名 * 表示允许所有域名。
     */
    private List<String> accessControlAllowOrigins;

    /**
     * 是否浏览器可以在frame或iframe标签中渲染一个页面网站以来避免点击劫持
     * DENY: 不允许在 frame 中展示，即便是在相同域名的页面中嵌套也不允许。
     * SAMEORIGIN: 表示该页面可以在相同域名页面的 frame 中展示。
     * ALLOW-FROM uri: 表示该页面可以在指定来源的 frame 中展示 例如："ALLOW-FROM  http://domain.com"
     */
    private String frameOptions = FRAME_OPTIONS_DEFAUTL;

    public Long getAccessControlMaxAge() {
        return accessControlMaxAge;
    }

    public void setAccessControlMaxAge(Long accessControlMaxAge) {
        this.accessControlMaxAge = accessControlMaxAge;
    }

    public List<String> getAccessControlAllowOrigins() {
        return accessControlAllowOrigins;
    }

    public void setAccessControlAllowOrigins(List<String> accessControlAllowOrigins) {
        this.accessControlAllowOrigins = accessControlAllowOrigins;
    }

    public String getFrameOptions() {
        return frameOptions;
    }

    public void setFrameOptions(String frameOptions) {
        this.frameOptions = frameOptions;
    }
}
