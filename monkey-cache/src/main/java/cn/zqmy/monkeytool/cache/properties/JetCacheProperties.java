package cn.zqmy.monkeytool.cache.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JetCache 配置类
 *
 * @Author: zhuoqianmingyue
 * @Date: 2021/04/15
 * @Description:
 **/
@ConfigurationProperties(prefix = "monkeycloud.cache")
public class JetCacheProperties {
    /**
     * 每个缓存实例的最大元素的全局配置
     */
    private int locallimit = 500;

    /**
     * 以毫秒为单位指定超时时间的全局配置（本地）默认是 2秒
     */
    private long localExpireAfterWrite = 2000L;

    /**
     * 指定多长时间没有访问，就让缓存失效，当前只有本地缓存支持 0表示不使用这个功能。
     */
    private long localExpireAfterAccess = 0L;

    /**
     * 指定多长时间没有访问，就让缓存失效，当前只有本地缓存支持 0表示不使用这个功能。
     */
    private long remoteExpireAfterAccess = 0L;
    /**
     * 以毫秒为单位指定超时时间的全局配置（远程）默认是 4个小时
     */
    private long remoteExpireAfterWrite = 14400000L;

    /**
     * 统计间隔 0 表示不统计
     */
    private int statIntervalMinutes = 0;

    /**
     * 为了不让name(@Cached和@CreateCache自动生成name的时候)太长，hiddenPackages指定的包名前缀被截掉
     */
    private String[] hiddenPackages;

    public int getLocallimit() {
        return locallimit;
    }

    public void setLocallimit(int locallimit) {
        this.locallimit = locallimit;
    }

    public long getLocalExpireAfterWrite() {
        return localExpireAfterWrite;
    }

    public void setLocalExpireAfterWrite(long localExpireAfterWrite) {
        this.localExpireAfterWrite = localExpireAfterWrite;
    }

    public long getLocalExpireAfterAccess() {
        return localExpireAfterAccess;
    }

    public void setLocalExpireAfterAccess(long localExpireAfterAccess) {
        this.localExpireAfterAccess = localExpireAfterAccess;
    }

    public int getStatIntervalMinutes() {
        return statIntervalMinutes;
    }

    public void setStatIntervalMinutes(int statIntervalMinutes) {
        this.statIntervalMinutes = statIntervalMinutes;
    }

    public String[] getHiddenPackages() {
        return hiddenPackages;
    }

    public void setHiddenPackages(String[] hiddenPackages) {
        this.hiddenPackages = hiddenPackages;
    }

    public long getRemoteExpireAfterAccess() {
        return remoteExpireAfterAccess;
    }

    public void setRemoteExpireAfterAccess(long remoteExpireAfterAccess) {
        this.remoteExpireAfterAccess = remoteExpireAfterAccess;
    }

    public long getRemoteExpireAfterWrite() {
        return remoteExpireAfterWrite;
    }

    public void setRemoteExpireAfterWrite(long remoteExpireAfterWrite) {
        this.remoteExpireAfterWrite = remoteExpireAfterWrite;
    }
}
