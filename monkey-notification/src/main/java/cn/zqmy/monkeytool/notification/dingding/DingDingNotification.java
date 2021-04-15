package cn.zqmy.monkeytool.notification.dingding;


import cn.hutool.core.util.StrUtil;
import cn.zqmy.monkeytool.notification.Notification;
import com.alibaba.fastjson.JSON;
import cn.zqmy.monkeytool.notification.Message;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 钉钉通知工具类
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @since 0.3.1
 * @Description: https://ding-doc.dingtalk.com/doc#/serverapi2/qf2nxq
 **/
public class DingDingNotification implements Notification {

    /**
     * 发送成功消息
     */
    private static final String SUCESS_MSG = "ok";
    /**
     * 钉钉markdown 格式数据
     */
    private static final String DINGDING_MSG_MARKDOWN = "markdown";


    private static final Logger LOGGER = LoggerFactory.getLogger(DingDingNotification.class);
    private String url;

    public DingDingNotification(){}

    public DingDingNotification(String url){
        this.url = url;
    }

    @Override
    public void send(String url,Message message) {
        try {

            if (StrUtil.isBlank(url)) {
                url = this.url;
            }
            check(url,message.getMessage());

            DingTalkClient client = new DefaultDingTalkClient(url);
            OapiRobotSendRequest request = new OapiRobotSendRequest();

            request.setMsgtype(DINGDING_MSG_MARKDOWN);
            OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
            markdown.setTitle(message.getTitle());
            markdown.setText(message.getMessage());
            request.setMarkdown(markdown);

            OapiRobotSendResponse response = client.execute(request);
            String errmsg = response.getErrmsg();
            if (!SUCESS_MSG.equals(errmsg)) {
                throw new RuntimeException("DingDingNotification send error:" + errmsg);
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("DingDingNotification send message success ! url:{} message:{}",url,JSON.toJSONString(message));
            }

        } catch (Exception e) {
            LOGGER.error("项目启动通知发送失败！url:{} message:{}",url,JSON.toJSONString(message), e);
        }
    }


}
