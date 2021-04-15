package cn.zqmy.monkeytool.notification.weixin;

import cn.zqmy.monkeytool.http.BaseRestClient;
import cn.zqmy.monkeytool.http.properties.RestClientProperties;
import com.alibaba.fastjson.TypeReference;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * 微信机器人客户端
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @since 0.3.1
 **/
public class QyWeiXinRobotClient extends BaseRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(QyWeiXinRobotClient.class);
    @Autowired
    private HttpClient httpClient;
    @Autowired
    private RestClientProperties restClientProperties;

    private String url;
    private static final String SUCCESS_MSG = "ok";

    @PostConstruct
    public void setHttpClient() {
        super.setCustomHttpClient(httpClient);
    }


    public void send(String url, String message) throws HttpProcessException {
        HttpConfig httpConfig = HttpConfig
                .custom()
                .url(url)
                .json(message)
                .timeout(restClientProperties.getTimeout());

        QyWeiXinResult qyWeixinResult = post(httpConfig, null, new TypeReference<QyWeiXinResult>() {
        });
        String errorMsg = qyWeixinResult.getErrorMsg();
        if (!SUCCESS_MSG.equals(errorMsg)) {
            throw new RuntimeException("QyWeiXinRobotClient send error:" + errorMsg);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("QyWeiXinNotification send message success ! url:{} message:{}", url, message);
        }
    }
}
