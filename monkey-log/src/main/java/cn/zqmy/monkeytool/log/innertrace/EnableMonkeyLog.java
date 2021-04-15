package cn.zqmy.monkeytool.log.innertrace;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 是否启用 内部追踪日志注解
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @since 0.2.1
 **/
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(MonkeyLogAutoConfiguration.class)
public @interface EnableMonkeyLog {
}
