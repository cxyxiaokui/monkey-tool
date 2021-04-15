package cn.zqmy.monkeytool.notification;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认消息格式
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @since 0.3.1
 **/
public class DefaultNotificationMessage<T> implements Message<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultNotificationMessage.class);

    /**
     * 获取消息标题
     *
     */
    private String title;

    /**
     * 触发器名称
     *
     */
    private String triggerName;

    /**
     * 设置消息信息
     */
    private T data;


    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public T getData() {
        return data;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String getMessage() {
        String now = DateUtil.now();
        String messageDataStr = "";
        try {
            messageDataStr = JSON.toJSONString(data);
        } catch (Exception e) {
            LOGGER.error("NotificationMessage to JSON fail",e);
        }


        return "## ["+ title +"监控报警]\n" +
                "触发器："+ triggerName+ "\n\n" +
                "报警信息：" + messageDataStr + "\n\n" +
                "报警时间:" + now + " \n";
    }
}
