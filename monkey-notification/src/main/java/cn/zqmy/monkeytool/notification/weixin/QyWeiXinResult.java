package cn.zqmy.monkeytool.notification.weixin;

/**
 * 企业微信结果类
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @since 0.3.1
 **/
public class QyWeiXinResult {

    private Integer errorCode;
    private String errorMsg;
    private String type;
    private String media_id;
    private String created_at;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
