package cn.zqmy.monkeytool.notification;
/**
 * 消息类接口
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @since 0.3.1
 **/
public interface Message<T> {
    /**
     * 获取消息标题
     */
    String getTitle();

    /**
     * 获取具体的消息信息
     * @return
     */
    String getMessage();

    /**
     * 触发器名称
     */
    String getTriggerName();

    /**
     * 设置消息信息
     */
    void setData(T data);
}
