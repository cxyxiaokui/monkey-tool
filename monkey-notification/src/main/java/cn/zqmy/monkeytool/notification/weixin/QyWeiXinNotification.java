package cn.zqmy.monkeytool.notification.weixin;

import cn.hutool.core.util.StrUtil;
import cn.zqmy.monkeytool.notification.Notification;
import cn.zqmy.monkeytool.notification.RobotProperties;
import com.alibaba.fastjson.JSON;
import cn.zqmy.monkeytool.notification.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 企业微信通知工具类
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @since 0.3.1
 **/
public class QyWeiXinNotification implements Notification {

    private static Logger LOGGER = LoggerFactory.getLogger(QyWeiXinNotification.class);

    @Autowired
    private QyWeiXinRobotClient qyWeiXinRobotClient;
    @Autowired
    private RobotProperties robotProperties;

    @Override
    public void send(String url, Message message) {
        String weixinMsgJsonStr = "";
        try{
            if (StrUtil.isBlank(url)) {
                url = robotProperties.getUrl();
            }
            check(url, message.getMessage());

            QyWeinXinMsg qyWeinXinMsg = new QyWeinXinMsg();
            qyWeinXinMsg.setMsgtype("markdown");
            QyWeinXinMsg.Markdown markdown = new QyWeinXinMsg().new Markdown();
            String messageStr = message.getMessage();
            markdown.setContent(messageStr);
            qyWeinXinMsg.setMarkdown(markdown);

            weixinMsgJsonStr = JSON.toJSONString(qyWeinXinMsg);

            qyWeiXinRobotClient.send(url, weixinMsgJsonStr);
        } catch (Exception e) {
            LOGGER.error("发送微信启动群失败！url:{},message:{}",url,weixinMsgJsonStr, e);
        }
    }
}
