package cn.zqmy.monkeytool.apiversion.annotation;

import java.lang.annotation.*;

/**
 * 通过此注解，可以通过接口 header 中的cv,terminal参数路由倒不同的处理方法
 * （handler method，基于RequestMappingHandlerMapping中的getCustom**Condition方法扩展）
 *
 * @author zhuoqianmingyue
 * @date 2020/10/20
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ClientVersion {

    /**
     * 配置cv,terminal参数路由倒不同的处理方法
     *
     * @return
     */
    TerminalVersion[] value() default {};

    /**
     * 从string表达式解析cv,terminal参数路由倒不同的处理方法
     *
     * @return
     */
    String[] expression() default {};
}
