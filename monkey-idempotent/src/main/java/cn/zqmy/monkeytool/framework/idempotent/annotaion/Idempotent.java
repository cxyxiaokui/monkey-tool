package cn.zqmy.monkeytool.framework.idempotent.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明需要接口幂等的注解
 *
 * @Author: zhuoqianmingyue
 * @Date: 2020/03/26
 * @Description: @see cn.zqmy.monkeytool.framework.idempotent.IdempotentInterceptor
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {

    /**
     * 接口幂等的名称（TokenKey）
     *
     * 该名称 和 Token 前缀组合使用，例如：TokenKey = Token 前缀 + name
     */
    String name();

    /**
     * TOKEN 无效返回错误码
     *
     */
    int errorCode() default -1;

    /**
     * 错误信息描述
     */
    String msg() default "Idempotent Token is invalid!";
}
