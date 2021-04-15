package cn.zqmy.monkeytool.core.filters;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 跨域拦截器
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 **/
public class CorsFilter implements Filter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        MonkeyCorsProperties monkeyCorsProperties = SpringUtil.getBean(MonkeyCorsProperties.class);

        //允许跨域域名 * 表示允许所有域名。
        List<String> accessControlAllowOrigin = monkeyCorsProperties.getAccessControlAllowOrigins();
        if (CollectionUtil.isNotEmpty(accessControlAllowOrigin)) {
            accessControlAllowOrigin.forEach((domain)->{
                response.setHeader("Access-Control-Allow-Origin", domain);
            });
        }

        //如果没有设置默认为任意域名
        if (CollectionUtil.isEmpty(accessControlAllowOrigin)) {
            response.setHeader("Access-Control-Allow-Origin", "*");
        }

        //服务器支持的所有跨域请求的方法
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        //response.setHeader("Access-Control-Allow-Credentials", "true");
        //服务器支持的所有头信息字段
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type, Cookie, Authorization");

        //确认是否浏览器可以在frame或iframe标签中渲染一个页面
        String frameOptions = monkeyCorsProperties.getFrameOptions();
        if (StrUtil.isBlank(frameOptions)) {
            frameOptions = MonkeyCorsProperties.FRAME_OPTIONS_DEFAUTL;
        }
        response.addHeader("x-frame-options", frameOptions);

        //用来指定预检请求的有效期，单位为秒
        Long accessControlMaxAge = monkeyCorsProperties.getAccessControlMaxAge();
        if (accessControlMaxAge == null) {
            accessControlMaxAge = MonkeyCorsProperties.ACCESS_CONTROL_MAX_AGE_DEFAUTL;
        }
        response.setHeader("Access-Control-Max-Age", String.valueOf(accessControlMaxAge));

        // 如果是OPTIONS则结束请求
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
