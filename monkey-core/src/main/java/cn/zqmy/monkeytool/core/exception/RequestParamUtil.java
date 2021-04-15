package cn.zqmy.monkeytool.core.exception;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.zqmy.monkeytool.requestbody.cache.filter.RequestBodyThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @Version
 */
public class RequestParamUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestParamUtil.class);

    /**
     * 获取 query string
     *
     * @param request
     * @return
     */
    public static Map<String, Object> getUrlParameterMap(HttpServletRequest request) {

        Map<String, Object> urlParamMap = new HashMap<>(16);

        Map<String, String[]> parameterMap = request.getParameterMap();

        if (!CollectionUtil.isEmpty(parameterMap)) {
            parameterMap.forEach((k, v) -> urlParamMap.put(k, v[0]));
        }

        return urlParamMap;
    }

    /**
     * 获取request body 数据
     *
     * @param request
     * @return
     */
    public static Map<String, Object> getBodyParameterMap(HttpServletRequest request) {
        String requestBodyStr = RequestBodyThreadLocal.get();
        Map<String, Object> effectiveBodyMap = requestBodyStrToMap(requestBodyStr);
        return effectiveBodyMap;
    }

    public static Map<String, Object> requestBodyStrToMap(String requestBodyStr) {
        HashMap<String, Object> effectiveBodyMap = new HashMap<>(16);
        if (StringUtils.isNotBlank(requestBodyStr) && JSONUtil.isJsonObj(requestBodyStr)) {
            HashMap<String, Object> bodyMap = JSON.parseObject(requestBodyStr, HashMap.class, Feature.OrderedField);
            if (CollectionUtil.isNotEmpty(bodyMap)) {
                for (Map.Entry<String, Object> entry : bodyMap.entrySet()) {
                    effectiveBodyMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        return effectiveBodyMap;
    }

    /**
     * 获取参数字符串 k=v&k=v... （排序后）
     *
     * @param request
     * @return
     */
    public static String getRequestParamStr(HttpServletRequest request) throws Exception {

        Map<String, Object> authenticatorParamMap = new HashMap<>(16);

        // url param
        authenticatorParamMap.putAll(getUrlParameterMap(request));

        // body param
        authenticatorParamMap.putAll(getBodyParameterMap(request));

        return getOrderMapStr(authenticatorParamMap);
    }

    /**
     * Map 排序后并拼成请求链接
     *
     * @param authenticatorParamMap
     * @return
     * @throws Exception
     */
    public static String getOrderMapStr(Map<String, Object> authenticatorParamMap) throws Exception {
        TreeMap<String, Object> authenticatorParamTreeMap = new TreeMap<>();
        authenticatorParamTreeMap.putAll(authenticatorParamMap);

        StringBuilder sb = new StringBuilder();
        if (!CollectionUtil.isEmpty(authenticatorParamTreeMap)) {
            for (Map.Entry<String, Object> entry : authenticatorParamTreeMap.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value == null) {
                    value = "";
                }

                String encodeKey = specialUrlEncode(key);
                if (ObjectUtil.isNull(value)) {
                    value = "";
                }
                if (notString(value)) {
                    value = JSON.toJSONString(value);
                }

                sb.append(encodeKey).append("=").append(value).append("&");
            }
            // 去掉最尾端&
            sb.deleteCharAt(sb.lastIndexOf("&"));
        }
        return sb.toString();
    }

    private static boolean notString(Object value) {
        return value != null && !(value instanceof String);
    }


    /**
     * 是否是Map类型的JSON 串
     *
     * @param rquestBodyStr
     * @return
     */
    public static boolean isMapJson(String rquestBodyStr) {
        return JSONUtil.isJson(rquestBodyStr) && !JSONUtil.isJsonArray(rquestBodyStr);
    }

    /**
     * 特殊字符或中文 Encode
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static String specialUrlEncode(String value) throws Exception {
        return java.net.URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~");
    }

    /**
     * 特殊字符或中文 Decode
     *
     * @param value
     * @return
     * @throws Exception
     */
    public static String specialUrlDecode(String value) throws Exception {
        return java.net.URLDecoder.decode(value, "UTF-8");
    }
}
