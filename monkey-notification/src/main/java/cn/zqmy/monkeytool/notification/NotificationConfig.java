package cn.zqmy.monkeytool.notification;

import cn.zqmy.monkeytool.notification.dingding.DingDingNotification;
import cn.zqmy.monkeytool.notification.weixin.QyWeiXinNotification;
import cn.zqmy.monkeytool.notification.weixin.QyWeiXinRobotClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息通知配置类
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @since 0.3.1
 **/
@Configuration
@EnableConfigurationProperties(RobotProperties.class)
public class NotificationConfig {

    private RobotProperties robotProperties;

    public NotificationConfig(RobotProperties robotProperties) {
        this.robotProperties = robotProperties;
    }

    /**
     * 钉钉群通知
     * @return
     */
    @Bean("dingDingNotification")
    public Notification dingDingNotification() {
        DingDingNotification dingDingNotification = new DingDingNotification(robotProperties.getUrl());
        return dingDingNotification;
    }

    /**
     * 企业微信通知
     * @return
     */
    @Bean("qyWeiXinNotification")
    public Notification weiXinNotification() {
        QyWeiXinNotification qyWeiXinNotification = new QyWeiXinNotification();
        return qyWeiXinNotification;
    }

    /**
     * 企业微信通知
     * @return
     */
    @Bean
    public QyWeiXinRobotClient qyWeiXinRobotClient() {
        QyWeiXinRobotClient qyWeiXinRobotClient = new QyWeiXinRobotClient();
        return qyWeiXinRobotClient;
    }
}
