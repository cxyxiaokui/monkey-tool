package cn.zqmy.monkeytool.requestbody.cache.filter;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 默认 ReqeustBody 包装类
 *
 * @Author: zhuoqianmingyue
 * @Date: 2020/04/15
 * @Description:
 **/
public class DefaultBaseRequestBodyWrapper extends HttpServletRequestWrapper {

    private final String body;

    public DefaultBaseRequestBodyWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.body = getBodyString(request);
    }

    public String getBody() {
        return body;
    }

    public String getBodyString(final HttpServletRequest request) throws IOException {
        String contentType = request.getContentType();
        String bodyString = "";
        StringBuilder sb = new StringBuilder();

        if (isFormRequest(contentType)) {
            Map<String, String[]> parameterMap = request.getParameterMap();
            for (Map.Entry<String, String[]> next : parameterMap.entrySet()) {
                String[] values = next.getValue();
                String value = null;
                if (values != null) {
                    if (values.length == 1) {
                        value = values[0];
                    } else {
                        value = Arrays.toString(values);
                    }
                }
                sb.append(next.getKey()).append("=").append(value).append("&");
            }
            if (sb.length() > 0) {
                bodyString = sb.toString().substring(0, sb.toString().length() - 1);
            }
            return bodyString;
        } else {
            return IOUtils.toString(request.getInputStream());
        }
    }

    /**
     * 判断是否是 Form 表单请求
     *
     * @param contentType
     * @return
     */
    private boolean isFormRequest(String contentType) {
        return StrUtil.isNotBlank(contentType) && (contentType.contains("multipart/form-data") || contentType.contains("x-www-form-urlencoded"));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes());

        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public int read() {
                return bais.read();
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

}
