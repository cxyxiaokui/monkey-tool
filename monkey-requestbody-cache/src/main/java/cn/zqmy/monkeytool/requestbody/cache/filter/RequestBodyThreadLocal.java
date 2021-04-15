package cn.zqmy.monkeytool.requestbody.cache.filter;

/**
 * RequestBody 线程本地缓存。
 *
 * @Author: zhuoqianmingyue
 * @Date: 2020/04/15
 * @Description:
 **/
public class RequestBodyThreadLocal {

    private static final ThreadLocal<String> LOCAL = new ThreadLocal<>();

    /**
     * 设置请求缓存
     * @param requestId
     */
    public static void set(String requestId) {
        LOCAL.set(requestId);
    }

    /**
     * 获取请求缓存
     * @return
     */
    public static String get() {
        return LOCAL.get();
    }

    /**
     * 清除请求缓存
     */
    public static void clear() {
        LOCAL.remove();
    }

}
