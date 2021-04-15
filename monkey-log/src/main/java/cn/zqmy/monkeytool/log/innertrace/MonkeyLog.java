package cn.zqmy.monkeytool.log.innertrace;

import java.lang.annotation.*;

/**
 * 内部请求访问追踪日志分析注解
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @Description: 日志打印注解
 * @since 0.2.1
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MonkeyLog {

    /**
     * 方法描述 默认为空串
     *
     * @return
     */
    String description() default "";

    /**
     * 方法类型，0-查询方法，不打印查询结果
     *
     * @return
     */
    byte loggerType() default 1;
}
