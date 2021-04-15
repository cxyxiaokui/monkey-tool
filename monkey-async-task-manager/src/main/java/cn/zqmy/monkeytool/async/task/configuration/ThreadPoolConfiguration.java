package cn.zqmy.monkeytool.async.task.configuration;

import cn.zqmy.monkeytool.async.task.manager.ShutdownManager;
import cn.zqmy.monkeytool.async.task.manager.Threads;
import cn.zqmy.monkeytool.async.task.manager.AsyncThreadPoolProperties;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 线程池配置
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 * @since 0.3.1
 **/
@Configuration
@EnableConfigurationProperties(AsyncThreadPoolProperties.class)
public class ThreadPoolConfiguration {

    private AsyncThreadPoolProperties asyncThreadPoolProperties;

    public ThreadPoolConfiguration(AsyncThreadPoolProperties asyncThreadPoolProperties) {
        this.asyncThreadPoolProperties = asyncThreadPoolProperties;
    }

    /**
     * 执行周期性或定时任务
     */
    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduledExecutorService() {
        return new ScheduledThreadPoolExecutor(asyncThreadPoolProperties.getCorePoolSize(),
                new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                Threads.printException(r, t);
            }
        };
    }

    @Bean
    public ShutdownManager shutdownManager() {
        return new ShutdownManager();
    }
}
