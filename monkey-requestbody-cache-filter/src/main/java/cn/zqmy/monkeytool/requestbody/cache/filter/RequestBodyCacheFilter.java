package cn.zqmy.monkeytool.requestbody.cache.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * RequestBody 缓存 Filter
 *
 * @Author: zhuoqianmingyue
 * @Date: 2020/04/15
 * @Description:
 **/
public class RequestBodyCacheFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public static final List<String> WRAP_VERBS = Arrays.asList("PUT", "POST", "PATCH", "DELETE");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {

            if (wrapRequest((HttpServletRequest) request)) {

                // create cache
                DefaultBaseRequestBodyWrapper wrapper = getInstance((HttpServletRequest) request);

                RequestBodyThreadLocal.set(wrapper.getBody());
                // continue
                chain.doFilter(wrapper, response);
            } else {
                chain.doFilter(request, response);
            }
        } finally {
            RequestBodyThreadLocal.clear();
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 该方法返回 request 包装 Wrapper实例 {@link DefaultBaseRequestBodyWrapper}
     * 您可能希望使用自己的特定实现来覆盖它
     *
     * @param request to wrap
     * @return RequestBodyWrapper
     * @throws IOException
     */
    protected DefaultBaseRequestBodyWrapper getInstance(HttpServletRequest request) throws IOException {
        return new DefaultBaseRequestBodyWrapper(request);
    }

    /**
     * 默认处理 PUT/POST/PATCH/DELETE 请求
     *
     * @param request 原生 request
     * @return flag, true 表示该请求需要包装
     */
    protected boolean wrapRequest(HttpServletRequest request) {

        return WRAP_VERBS.contains(request.getMethod());
    }
}
