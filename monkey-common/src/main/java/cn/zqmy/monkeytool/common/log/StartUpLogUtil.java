package cn.zqmy.monkeytool.common.log;
/**
 * 启动日志输出信息工具类
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 **/
public class StartUpLogUtil {

    private StartUpLogUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 生成启动成功日志信息
     *
     * @param applicationName 应用名称
     * @return
     */
    public static String generateLogInfo(String applicationName) {
        
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SystemLogColorConstant.YELLOW + "(♥◠ ‿ ◠) ﾉﾞ").append(SystemLogColorConstant.END).append("：木有发现BUG ")
                .append(applicationName).append(" 启动成功 ").append(SystemLogColorConstant.YELLOW + "ლ ( ´ڡ` ლ) ﾞ")
                .append(SystemLogColorConstant.END).append(" ").append(SystemLogColorConstant.BULLUE).append(" OH YEAH ！")
                .append(SystemLogColorConstant.END);
        return stringBuilder.toString();
    }
}
