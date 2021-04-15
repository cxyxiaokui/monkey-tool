package cn.zqmy.monkeytool.notification.weixin;

/**
 * 企业微信消息通知数据体
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @since 0.3.1
 * @Description:
 **/
public class QyWeinXinMsg {
    /**
     * 消息格式 目前仅支持 markdown
     */
    private String msgtype;

    /**
     * markdown 数据格式
     */
    private Markdown markdown;

    public class Markdown {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Markdown getMarkdown() {
        return markdown;
    }

    public void setMarkdown(Markdown markdown) {
        this.markdown = markdown;
    }
}
