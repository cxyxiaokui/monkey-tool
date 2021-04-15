package cn.zqmy.monkeytool.notification;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 钉钉机器人配置类
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @since 0.3.1
 **/
@ConfigurationProperties(prefix = "monkeycloud.robot.notification")
public class RobotProperties {
    private static final String WEIXIN_TYPE = "weixin";
    private static final String DINGDING_TYPE = "dingding";

    /**
     * 机器人URL
     */
    private String url = "";

    private String type = WEIXIN_TYPE;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
