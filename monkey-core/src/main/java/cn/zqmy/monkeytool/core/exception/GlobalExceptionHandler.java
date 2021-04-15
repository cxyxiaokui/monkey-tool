package cn.zqmy.monkeytool.core.exception;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.zqmy.monkeytool.common.core.Result;
import cn.zqmy.monkeytool.common.core.ResultCode;
import cn.zqmy.monkeytool.common.core.ResultGenerator;
import cn.zqmy.monkeytool.common.exception.CustomErrorCodeException;
import cn.zqmy.monkeytool.common.exception.ParamsNotValidException;
import cn.zqmy.monkeytool.common.exception.ServiceException;
import cn.zqmy.monkeytool.notification.DefaultNotificationMessage;
import cn.zqmy.monkeytool.notification.dingding.DingDingNotification;
import cn.zqmy.monkeytool.notification.weixin.QyWeiXinNotification;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 统一异常处理 Handler
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 **/
@RestControllerAdvice
@EnableConfigurationProperties(GlobalExceptionProperties.class)
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final int QYWEIXIN_STACK_TRACE_STR_MAX_LENGTH = 3000;
    private GlobalExceptionProperties globalExceptionProperties;

    public GlobalExceptionHandler(GlobalExceptionProperties globalExceptionProperties) {
        this.globalExceptionProperties = globalExceptionProperties;
    }

    @Value("${spring.application.name:NA}")
    private String applicationName;

    @Value("${spring.profiles.active:NA}")
    private String activeProfile;

    /**
     * 系统异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e, HttpServletRequest request) {
        String rquestInfo = getRequestInfo(e, request);
        notification(rquestInfo, "系统异常");
        logger.error(rquestInfo, e);
        return ResultGenerator.genFailResult("访问：" + request.getRequestURI() + " 出错:" + e.getMessage());
    }

    /**
     * 自定义错误码异常
     */
    @ExceptionHandler(CustomErrorCodeException.class)
    public Result customErrorCodeException(CustomErrorCodeException e, HttpServletRequest request) {
        String rquestInfo = getRequestInfo(e, request);
        notification(rquestInfo, "自定义错误码异常错误码: " + e.getErrorCode());
        logger.error("自定义错误码业务异常:" + rquestInfo);
        return ResultGenerator.genFailResult(e.getErrorCode(), e.getMessage());
    }

    /**
     *  参数无效异常
     */
    @ExceptionHandler(ParamsNotValidException.class)
    public Result paramsNotValidException(ParamsNotValidException e, HttpServletRequest request) {
        logger.error("参数无效异常:{}" + e.getMessage());
        Integer errorCode = e.getErrorCode();
        if (errorCode == null) {
            errorCode = -1;
        }
        return ResultGenerator.genFailResult(errorCode, e.getMessage());
    }

    /**
     * 业务异常(ServletException)
     *
     * @return Result
     */
    @ExceptionHandler(ServiceException.class)
    public Result serviceException(ServiceException e, HttpServletRequest request) {
        String requestInfo = getRequestInfo(e, request);
        notification(requestInfo, "业务异常");
        logger.error("自定义业务异常:" + requestInfo);
        return ResultGenerator.genFailResult(e.getMessage());
    }


    /**
     * 捕捉校验异常(BindException)
     *
     * @return Result
     */
    @ExceptionHandler(BindException.class)
    public Result validException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        return this.getValidError(fieldErrors);
    }

    /**
     * 捕捉校验异常(MethodArgumentNotValidException)
     *
     * @return Result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result validException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        return this.getValidError(fieldErrors);
    }

    /**
     * 获取校验错误信息
     *
     * @param fieldErrors 字段错误信息
     * @return 统一API响应结果封装
     */
    private Result getValidError(List<FieldError> fieldErrors) {
        Result resultReturn = new Result();

        List<String> errorList = new ArrayList<>();
        StringBuilder errorMsg = new StringBuilder("校验异常(ValidException):");
        for (FieldError error : fieldErrors) {
            errorList.add(error.getField() + "-" + error.getDefaultMessage());
            errorMsg.append(error.getField()).append("-").append(error.getDefaultMessage()).append(".");
        }
        resultReturn.setMsg("errorMsg: " + errorMsg);
        resultReturn.setCode(ResultCode.FAIL);
        return resultReturn;
    }

    /**
     * 获取异常请求信息
     *
     * @param e       异常
     * @param request 请求
     * @return 异常信息
     */
    private String getRequestInfo(Exception e, HttpServletRequest request) {

        String requestUri = request.getRequestURI();
        Map<String, Object> urlParameterMap = RequestParamUtil.getUrlParameterMap(request);
        Map<String, Object> bodyParameterMap = RequestParamUtil.getBodyParameterMap(request);
        String urlParameterMapJsonStr = "";
        String bodyParameterMapJsonStr = "";
        String method = request.getMethod();

        try {
            if (CollectionUtil.isNotEmpty(urlParameterMap)) {
                urlParameterMapJsonStr = JSON.toJSONString(urlParameterMap);
            }
            if (CollectionUtil.isNotEmpty(bodyParameterMap)) {
                bodyParameterMapJsonStr = JSON.toJSONString(bodyParameterMap);
            }
        } catch (Exception ex) {
            logger.error("统一异常参数JSON处理失败！" + ex.getMessage(), ex);
        }
        String stackTraceStr = Throwables.getStackTraceAsString(e);

        if (StrUtil.isNotBlank(stackTraceStr) && stackTraceStr.length() >= QYWEIXIN_STACK_TRACE_STR_MAX_LENGTH) {
            stackTraceStr = stackTraceStr.substring(0, 3000);
        }

        return "访问：" + requestUri +
                "请求Method：" + method + " " + "请求参数：[" + urlParameterMapJsonStr + "]" + " " + "请求Body：[" + bodyParameterMapJsonStr + "]" + " " + " 出错:" + stackTraceStr;
    }

    /**
     * 通过钉钉或者微信发送异常通知
     *
     *
     * @param requestInfo 请求信息
     * @param exceptionName 异常名称
     */
    private void notification(String requestInfo, String exceptionName) {
        boolean notification = globalExceptionProperties.getOpen();
        if (notification) {
            String type = globalExceptionProperties.getType();
            if (GlobalExceptionProperties.WEIXIN_TYPE.equals(type)) {
                QyWeiXinNotification qyWeiXinNotification = SpringUtil.getBean("qyWeiXinNotification");
                DefaultNotificationMessage<String> defaultNotificationMessage = buildNotifiErrorMsg(requestInfo, exceptionName);
                qyWeiXinNotification.send("", defaultNotificationMessage);
            }

            if (GlobalExceptionProperties.DINGDING_TYPE.equals(type)) {
                DingDingNotification dingDingNotification = SpringUtil.getBean("dingDingNotification");
                DefaultNotificationMessage<String> defaultNotificationMessage = buildNotifiErrorMsg(requestInfo, exceptionName);
                dingDingNotification.send("", defaultNotificationMessage);
            }
        }
    }

    private DefaultNotificationMessage<String> buildNotifiErrorMsg(String rquestInfo, String exceptionName) {
        String serviceName = "服务名称：" + applicationName + "环境：" + activeProfile;
        DefaultNotificationMessage<String> defaultNotificationMessage = new DefaultNotificationMessage<>();
        defaultNotificationMessage.setTitle(serviceName);
        defaultNotificationMessage.setTriggerName("统一异常处理 Handler: " + exceptionName);
        defaultNotificationMessage.setData(rquestInfo);
        return defaultNotificationMessage;
    }
}
