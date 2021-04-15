package cn.zqmy.monkeytool.notification;

import cn.hutool.core.util.StrUtil;

/**
 * 通用通知接口
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @since 0.3.1
 **/
public interface Notification {

    /**
     * 发送消息
     * @param url 消息提示服务地址
     * @param message 消息
     */
    void send(String url,Message message);


    /**
     * 校验 url 和发送的消息
     *
     * @param url 消息提示服务地址
     * @param message 消息类
     */
    default void check(String url, String message) {
        if (StrUtil.isBlank(url)) {
            throw new RuntimeException("QyWeiXin send url is null");
        }
        if (StrUtil.isBlank(message)) {
            throw new RuntimeException("QyWeiXin send message is null");
        }
    }
}
