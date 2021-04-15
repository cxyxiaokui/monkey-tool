package cn.zqmy.monkeytool.apiversion.annotation;

import java.lang.annotation.*;

/**
 * 通过此注解，自动为 requestMapping 合并一个以版本号开头的路径；
 * 建议：大版本在类上配置，小版本可以通过配置在方法上，此时将替换类上面的大版本配置
 *
 * @author zhuoqianmingyue
 * @date 2020/10/20
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ApiVersion {

    /**
     * 版本号
     */
    String value() default "";
}
