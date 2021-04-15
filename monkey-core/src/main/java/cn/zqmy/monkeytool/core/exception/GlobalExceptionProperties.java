package cn.zqmy.monkeytool.core.exception;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 公共异常配置类
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @Description:
 **/
@ConfigurationProperties(prefix = "bsy.robot.notification.global.exception")
public class GlobalExceptionProperties {
    /**
     * 企业微信通知类型
     */
    public static final String WEIXIN_TYPE = "weixin";

    /**
     * 钉钉通知类型
     */
    public static final String DINGDING_TYPE = "dingding";

    /**
     * 通知是否开启的默认值 false
     */
    private static final boolean DEFAULT_OPEN = false;

    /**
     * 通知是否开启
     */
    private boolean open = DEFAULT_OPEN;

    /**
     * 通知的类型
     */
    private String type = WEIXIN_TYPE;

    public boolean getOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
