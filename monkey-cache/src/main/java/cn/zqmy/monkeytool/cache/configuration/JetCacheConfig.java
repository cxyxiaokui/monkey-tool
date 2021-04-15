package cn.zqmy.monkeytool.cache.configuration;

import cn.zqmy.monkeytool.cache.properties.JetCacheProperties;
import cn.zqmy.monkeytool.cache.util.*;
import com.alicp.jetcache.CacheBuilder;
import com.alicp.jetcache.anno.CacheConsts;
import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.support.GlobalCacheConfig;
import com.alicp.jetcache.anno.support.SpringConfigProvider;
import com.alicp.jetcache.embedded.CaffeineCacheBuilder;
import com.alicp.jetcache.embedded.EmbeddedCacheBuilder;
import com.alicp.jetcache.redis.RedisCacheBuilder;
import com.alicp.jetcache.support.FastjsonKeyConvertor;
import com.alicp.jetcache.support.JavaValueDecoder;
import com.alicp.jetcache.support.JavaValueEncoder;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.util.Pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * JetCache 以及 Redis 操作工具类配置类
 *
 * @Author: zhuoqianmingyue
 * @Date: 2020/01/13
 * @Description:
 **/
@Configuration
@EnableCreateCacheAnnotation
@EnableConfigurationProperties(JetCacheProperties.class)
public class JetCacheConfig {

    private JetCacheProperties jetCacheProperties;

    public JetCacheConfig(JetCacheProperties jetCacheProperties) {
        this.jetCacheProperties = jetCacheProperties;
    }

    /**
     * Redis 服务主机IP
     */
    @Value("${spring.redis.host:localhost}")
    private String host;
    /**
     * Redis 服务端口号
     */
    @Value("${spring.redis.port:6379}")
    private Integer port;

    @Value("${spring.redis.password}")
    private String password;
    /**
     * 资源池确保最少空闲的连接数
     */
    @Value("${spring.redis.jedis.pool.minIdle:2}")
    private Integer minIdle;
    /**
     * 资源池允许最大空闲的连接数
     */
    @Value("${spring.redis.jedis.pool.maxIdle:10}")
    private Integer maxIdle;
    /**
     * 资源池中最大连接数
     */
    @Value("${spring.redis.jedis.pool.maxActive:10}")
    private Integer maxActive;
    /**
     * 当资源池连接用尽后，调用者的最大等待时间(单位为毫秒)
     */
    @Value("${spring.redis.jedis.pool.maxWait:1000}")
    private Long maxWait;

    /**
     * 将Jedis Pool 声明为Spring 容器
     *
     * @return
     */
    @Bean("jetCacheJedisPool")
    public Pool<Jedis> pool() {
        GenericObjectPoolConfig pc = new GenericObjectPoolConfig();
        pc.setMinIdle(minIdle);
        pc.setMaxIdle(maxIdle);
        pc.setMaxTotal(maxActive);
        pc.setMaxWaitMillis(maxWait);
        //向资源池借用连接时是否做连接有效性检测(ping)，无效连接会被移除
        pc.setTestOnBorrow(false);
        //向资源池归还连接时是否做连接有效性检测(ping)，无效连接会被移除
        pc.setTestOnReturn(false);
        //是否开启空闲资源监测
        pc.setTestWhileIdle(true);
        //	资源池中资源最小空闲时间(单位为毫秒)，达到此值后空闲资源将被移除
        pc.setMinEvictableIdleTimeMillis(60000);
        //空闲资源的检测周期(单位为毫秒)
        pc.setTimeBetweenEvictionRunsMillis(30000);
        return new JedisPool(pc, host, port, 3000, password);
    }

    @Bean("jetCacheSpringConfigProvider")
    public SpringConfigProvider springConfigProvider() {
        return new SpringConfigProvider();
    }

    @Bean
    public GlobalCacheConfig config(@Autowired @Qualifier("jetCacheSpringConfigProvider") SpringConfigProvider configProvider,
                                    @Autowired @Qualifier("jetCacheJedisPool") Pool<Jedis> pool) {
        //本地缓存采用 Caffeine
        Map<String, CacheBuilder> localBuilders = new HashMap<>(1);
        EmbeddedCacheBuilder localBuilder = CaffeineCacheBuilder
                .createCaffeineCacheBuilder()
                .keyConvertor(FastjsonKeyConvertor.INSTANCE)
                //每个缓存实例的最大元素的全局配置，仅local类型的缓存需要指定。注意是每个缓存实例的限制，而不是全部，比如这里指定100，然后用@CreateCache创建了两个缓存实例（并且注解上没有设置localLimit属性），那么每个缓存实例的限制都是100
                .limit(jetCacheProperties.getLocallimit())
                //以毫秒为单位指定超时时间的全局配置(以前为defaultExpireInMillis)
                .expireAfterWrite(jetCacheProperties.getLocalExpireAfterWrite(), TimeUnit.MILLISECONDS)
                //需要jetcache2.2以上，以毫秒为单位，指定多长时间没有访问，就让缓存失效，当前只有本地缓存支持。0表示不使用这个功能。
                .expireAfterAccess(jetCacheProperties.getLocalExpireAfterAccess(), TimeUnit.MILLISECONDS);

        localBuilders.put(CacheConsts.DEFAULT_AREA, localBuilder);

        //远程缓存 Redis
        Map<String, CacheBuilder> remoteBuilders = new HashMap<>(1);
        RedisCacheBuilder remoteCacheBuilder = RedisCacheBuilder.createRedisCacheBuilder()
                .keyConvertor(FastjsonKeyConvertor.INSTANCE)
                .valueEncoder(JavaValueEncoder.INSTANCE)
                .valueDecoder(JavaValueDecoder.INSTANCE)
                .jedisPool(pool)
                .expireAfterAccess(jetCacheProperties.getRemoteExpireAfterAccess(), TimeUnit.MILLISECONDS)
                .expireAfterWrite(jetCacheProperties.getRemoteExpireAfterWrite(), TimeUnit.MILLISECONDS)
                .cachePenetrateProtect(false)
                .cacheNullValue(false);

        remoteBuilders.put(CacheConsts.DEFAULT_AREA, remoteCacheBuilder);

        GlobalCacheConfig globalCacheConfig = new GlobalCacheConfig();
        globalCacheConfig.setConfigProvider(configProvider);
        globalCacheConfig.setLocalCacheBuilders(localBuilders);
        globalCacheConfig.setRemoteCacheBuilders(remoteBuilders);
        //统计间隔 0 表示不统计
        globalCacheConfig.setStatIntervalMinutes(jetCacheProperties.getStatIntervalMinutes());
        globalCacheConfig.setAreaInCacheName(false);
        //@Cached和@CreateCache自动生成name的时候，为了不让name太长，hiddenPackages指定的包名前缀被截掉
        globalCacheConfig.setHiddenPackages(jetCacheProperties.getHiddenPackages());

        return globalCacheConfig;
    }

    /**
     * Reids 基础操作工具类
     *
     * @return BaseRedisUtil
     */
    @Bean
    public BaseRedisUtil baseRedisUtil() {
        return new BaseRedisUtil();
    }

    /**
     * Redis Hash 操作工具类
     *
     * @return RedisHashUtil
     */
    @Bean
    public RedisHashUtil hashRedisUtil() {
        return new RedisHashUtil();
    }

    /**
     * Redis List 操作工具类
     *
     * @return
     */
    @Bean
    public RedisListUtil listRedisUtil() {
        return new RedisListUtil();
    }

    /**
     * Redis Set 操作工具类
     *
     * @return RedisSetUtil
     */
    @Bean
    public RedisSetUtil setRedisUtil() {
        return new RedisSetUtil();
    }

    @Bean
    public RedisStringUtil stringRedisUtil() {
        return new RedisStringUtil();
    }

    /**
     * Redis Zset 操作工具类
     *
     * @return RedisZsetUtil
     */
    @Bean
    public RedisZsetUtil zsetRedisUtil() {
        return new RedisZsetUtil();
    }
}
