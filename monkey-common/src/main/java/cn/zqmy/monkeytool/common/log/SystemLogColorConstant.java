package cn.zqmy.monkeytool.common.log;

/**
 * 系统日志彩色日志参数数据字典类
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 **/
public class SystemLogColorConstant {

    private SystemLogColorConstant() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 黄颜色
     */
    public static final String YELLOW = "\033[33;3m";
    /**
     * 绿颜色
     */
    public static final String BULLUE = "\033[32;2m";
    /**
     * 颜色结束标志
     */
    public static final String END = "\033[0m";
}
