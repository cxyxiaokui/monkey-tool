package cn.zqmy.monkeytool.apiversion.annotation;




import cn.zqmy.monkeytool.apiversion.config.VersionOperatorEnum;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 配置cv,terminal参数路由倒不同的处理方法
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TerminalVersion {

    /**
     * 客户端类型
     *
     */
    int[] terminals() default {};

    /**
     * 比较符
     *
     */
    VersionOperatorEnum op() default VersionOperatorEnum.NIL;

    /**
     * 版本号
     *
     */
    String version() default "";

}
