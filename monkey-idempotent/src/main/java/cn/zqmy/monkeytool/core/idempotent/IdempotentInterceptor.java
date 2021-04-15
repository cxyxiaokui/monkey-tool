package cn.zqmy.monkeytool.framework.idempotent;

import cn.hutool.json.JSONUtil;
import cn.zqmy.monkeytool.framework.idempotent.annotaion.Idempotent;
import cn.zqmy.monkeytool.framework.idempotent.common.Result;
import cn.zqmy.monkeytool.framework.idempotent.common.ResultGenerator;
import cn.zqmy.monkeytool.framework.idempotent.exception.NoAccessException;
import cn.zqmy.monkeytool.framework.idempotent.exception.ParamValidException;
import cn.zqmy.monkeytool.framework.idempotent.properties.IdempotentProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

/**
 * 自动处理接口幂等拦截器
 *
 * @Author: zhuoqianmingyue
 * @Date: 2020/11/09
 * @Description:
 **/
public class IdempotentInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(IdempotentInterceptor.class);

    @Autowired
    private IdempotentTokenService idempotentTokenService;

    /**
     * 接口幂等拦截器核心处理逻辑
     *
     * @param request HTTP Request
     * @param response HTTP Response
     * @param handler 拦截处理器
     * @return 是否拦截 true 通过不拦截否则拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        Idempotent annotation = method.getAnnotation(Idempotent.class);
        if (annotation != null) {
            IdempotentProperties idempotentProperties = idempotentTokenService.getIdempotentProperties();

            try {
                String name = annotation.name();
                String tokenPrefix = idempotentProperties.getTokenPrefix();
                String token = request.getHeader(tokenPrefix + ":" + name);

                return idempotentTokenService.checkToken(token);
            } catch (ParamValidException | NoAccessException e) {
                Result result = ResultGenerator.genFailResult(annotation.errorCode(), annotation.msg());
                returnJson(response, JSONUtil.toJsonStr(result));
                return false;
            } catch (Exception e) {
                logger.error("tokenService checkToken error", e);
                Result result = ResultGenerator.genFailResult(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
                returnJson(response, JSONUtil.toJsonStr(result));
                return false;
            }
        }

        return true;
    }

    /**
     * 将错误数据返回客户端
     *
     * @param response Http 响应
     * @param resultJson 错误信息 json
     */
    private void returnJson(HttpServletResponse response, String resultJson) {
        IdempotentProperties idempotentProperties = idempotentTokenService.getIdempotentProperties();
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(idempotentProperties.getErrorHttpStatus());

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(JSONUtil.toJsonStr(resultJson).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            logger.error("autoIdempotentInterceptor returnJson error", e);
        }
    }
}
