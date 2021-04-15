package cn.zqmy.monkeytool.log.innertrace;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 内部请求访问追踪日志分析切面类
 *
 * @author zhuoqianmingyue
 * @Date: 2021/04/15
 * @since 0.2.1
 */
@Order(-1000)
@Aspect
public class MonkeyLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonkeyLogAspect.class);

    private MonkeyLogProperties monkeyLogProperties;

    public void setMonkeyLogProperties(MonkeyLogProperties monkeyLogProperties) {
        this.monkeyLogProperties = monkeyLogProperties;
    }

    @Around(value = "@annotation(monkeyLog)")
    public Object doBasicProfiling(ProceedingJoinPoint pjp, MonkeyLog monkeyLog) throws Throwable {
        long startTime = System.currentTimeMillis();

        boolean isFirstInvoke = genFirstRequestTraceIdToMdc();

        String classAndMethodName = printRequestArgsLog(pjp, monkeyLog);

        return printResponseResultLog(pjp, monkeyLog, startTime, isFirstInvoke, classAndMethodName);
    }

    /**
     * 记录第一次请求链路ID(UUID)并设置到 MDC
     *
     * @return 是否为第一次请求 true 为第一次否则不是第一次请求
     */
    private boolean genFirstRequestTraceIdToMdc() {
        // 在日志中记录一次请求ID
        String id = MDC.get(monkeyLogProperties.getMonkeyTraceIdKey());
        boolean isFirstInvoke = false;
        if (StrUtil.isBlank(id)) {
            isFirstInvoke = true;
            id = UUID.fastUUID().toString().replace("-", "");
            MDC.put(monkeyLogProperties.getMonkeyTraceIdKey(), id);
        }
        return isFirstInvoke;
    }

    /**
     * 记录响应结果日志
     *
     * @param pjp
     * @param monkeyLog 内部请求访问追踪日志分析注解
     * @param startTime 执行开始时间
     * @param isFirstInvoke 是否为第一次请求
     * @param classAndMethodName 请求类和方法名称
     * @return 请求响应结果
     * @throws Throwable
     */
    private Object printResponseResultLog(ProceedingJoinPoint pjp, MonkeyLog monkeyLog, long startTime, boolean isFirstInvoke, String classAndMethodName) throws Throwable {
        Object object = null;
        try {
            object = pjp.proceed();
        } finally {

            long endTime = System.currentTimeMillis();
            long time = endTime - startTime;

            try {
                // 不打印查询结果
                String resultStr = "";
                if (monkeyLog.type() == 0) {
                    resultStr = "未打印";
                } else {
                    if (!(object instanceof HttpServletRequest) && !(object instanceof HttpServletResponse)) {
                        resultStr = JSONObject.toJSONString(object);
                    }
                }

                LOGGER.info(classAndMethodName + ";返回值:" + resultStr + ";方法耗时" + time + "ms;");
            } catch (Exception e) {
                LOGGER.error("打印返回值抛错：" + e.getMessage(), e);
            }
            if (isFirstInvoke) {
                MDC.remove(monkeyLogProperties.getMonkeyTraceIdKey());
            }
        }
        return object;
    }

    /**
     * 打印请求参数日志
     *
     * @param pjp
     * @param monkeyLog 内部请求访问追踪日志分析注解
     * @return 请求类和方法名称
     */
    private String printRequestArgsLog(ProceedingJoinPoint pjp, MonkeyLog monkeyLog) {
        //获取目标类ClassName
        String targetClassName = pjp.getTarget().getClass().getName();

        //获取访问方法名称
        StringBuilder classAndMethodStringBuilder = new StringBuilder();
        String methodName = targetClassName + "." + pjp.getSignature().getName();
        classAndMethodStringBuilder.append("[").append(methodName).append("]").append(monkeyLog.description());

        //获取参数信息
        try {
            Object[] args = pjp.getArgs();
            if (ArrayUtils.isNotEmpty(args)) {
                List<Object> argList = Arrays.asList(args);
                List<Object> logArgs = argList.stream().filter(arg ->
                        (!(arg instanceof Errors) &&
                                !(arg instanceof BeanPropertyBindingResult) &&
                                !(arg instanceof HttpServletRequest) &&
                                !(arg instanceof HttpServletResponse)))
                        .collect(Collectors.toList());

                LOGGER.info(classAndMethodStringBuilder.toString() + ";参数为:" + JSONObject.toJSONString(logArgs));
            }
        } catch (Exception e) {
            LOGGER.error("打印参数抛错：" + e.getMessage(), e);
        }
        return classAndMethodStringBuilder.toString();
    }
}
