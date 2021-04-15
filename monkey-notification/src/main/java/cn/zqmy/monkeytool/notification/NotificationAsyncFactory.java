package cn.zqmy.monkeytool.notification;

import cn.hutool.extra.spring.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

/**
 * 消息通知异步任务工厂类
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @since 0.3.1
 **/
public class NotificationAsyncFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationAsyncFactory.class);

    /**
     * 异步发送钉钉消息
     *
     * @param url     消息提示服务地址
     * @param message 消息
     * @return 任务task
     */
    public static TimerTask sendDingDing(String url, Message message) {

        return new TimerTask() {
            @Override
            public void run() {
                Notification dingDingNotification = SpringUtil.getBean("dingDingNotification");
                if (dingDingNotification != null) {
                    dingDingNotification.send(url, message);
                }
            }
        };
    }

    /**
     * 异步发送企业微信
     *
     * @param url     消息提示服务地址
     * @param message 消息
     * @return 任务task
     */
    public static TimerTask sendQiYeWeiXin(String url, Message message) {

        return new TimerTask() {
            @Override
            public void run() {
                Notification dingDingNotification = SpringUtil.getBean("qyWeiXinNotification");
                if (dingDingNotification != null) {
                    dingDingNotification.send(url, message);
                }
            }
        };
    }
}
