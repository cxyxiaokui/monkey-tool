package cn.zqmy.monkeytool.apiversion.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.*;

/**
 * 组合注解，实现了RequestMapping的功能，同时提供了ApiVersion、ClientVersion两种注解的配置
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping
@ApiVersion
@ClientVersion
public @interface VersionMapping {
    /**
     * Alias for {@link RequestMapping#name}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String name() default "";

    /**
     * Alias for {@link RequestMapping#value}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] value() default {};

    /**
     * Alias for {@link RequestMapping#path}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] path() default {};

    /**
     * Alias for {@link RequestMapping#params}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] params() default {};

    /**
     * Alias for {@link RequestMapping#headers}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] headers() default {};

    /**
     * Alias for {@link RequestMapping#consumes}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] consumes() default {};

    /**
     * Alias for {@link RequestMapping#produces}.
     */
    @AliasFor(annotation = RequestMapping.class)
    String[] produces() default {};

    @AliasFor(annotation = ApiVersion.class, attribute = "value")
    String apiVersion() default "";

    @AliasFor(annotation = ClientVersion.class, attribute = "value")
    TerminalVersion[] terminalVersion() default {};

    @AliasFor(annotation = ClientVersion.class, attribute = "expression")
    String[] terminalExpression() default {};

}
