package cn.zqmy.monkeytool.async.task.manager;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @since 0.3.1
 **/
@ConfigurationProperties(prefix = "monkeycloud.async.task.pool")
public class AsyncThreadPoolProperties {

    /**
     * 核心线程池大小默认值
     */
    private static final int DEFAULT_CORE_POOL_SIZE = 50;

    /**
     * 核心线程池大小
     */
    private int corePoolSize = DEFAULT_CORE_POOL_SIZE;


    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }
}
