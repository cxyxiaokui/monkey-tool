package cn.zqmy.monkeytool.cache.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 基础操作工具类
 * @Author: zhuoqianmingyue
 * @Date: 2021/04/15
 * @Description:
 **/
public class BaseRedisUtil {

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效时间（expire key seconds）
     *
     * @param key     建
     * @param timeout 失效时间（单位：秒，小于等于0 表示 永久有效）
     */
    public void expire(String key, long timeout) {
        try {
            if (timeout > 0) {
                redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            throw new RuntimeException("指定缓存失效时间 异常：", e);
        }
    }

    /**
     * 取 key键 的失效时间（ttl key）
     *
     * @param key 键
     * @return 失效时间（单位：秒）
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断 key键 是否存在（exists key）
     *
     * @param key 键
     * @return 存在：true；不存在：false
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除key键数组的缓存（del key）
     *
     * @param keys 要删除缓存的key键 数组
     */
    public void del(String... keys) {
        if(null != keys && keys.length > 0){
            redisTemplate.delete(CollectionUtils.arrayToList(keys));
        }
    }

    /**
     * 按照 key值前缀 批量删除 缓存
     *
     * @param prex key值前缀
     */
    public void delByPrex(String prex) {
        Set<String> keys = redisTemplate.keys(prex);
        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }


    /**
     * 从当前数据库中随机返回一个 key
     *
     * @return
     */
    public String randomKey() {
        return redisTemplate.randomKey();
    }

    /**
     * 修改 key 的名称
     *
     * @param oldKey
     * @param newKey
     */
    public void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 仅当 newkey 不存在时，将 oldKey 改名为 newkey
     *
     * @param oldKey
     * @param newKey
     * @return
     */
    public Boolean renameIfAbsent(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 返回 key 所储存的值的类型
     *
     * @param key
     * @return
     */
    public DataType type(String key) {
        return redisTemplate.type(key);
    }

    /**
     * 将当前数据库的 key 移动到给定的数据库 db 当中
     *
     * @param key
     * @param dbIndex
     * @return
     */
    public Boolean move(String key, int dbIndex) {
        return redisTemplate.move(key, dbIndex);
    }

    /**
     * 使用scan命令 查询某些前缀的key
     *
     * @param key
     * @return
     */
    public Set<String> scan(String key) {
        return this.redisTemplate.execute((RedisCallback<Set<String>>) connection -> {

            Set<String> binaryKeys = new HashSet<>();

            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(key).count(1000).build());
            while (cursor.hasNext()) {
                binaryKeys.add(new String(cursor.next()));
            }
            return binaryKeys;
        });
    }

    /**
     * 使用scan命令 查询某些前缀的key 有多少个
     *
     * @param key
     * @return
     */
    public Long scanSize(String key) {
        return this.redisTemplate.execute((RedisCallback<Long>) connection -> {
            long count = 0L;
            Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(key).count(1000).build());
            while (cursor.hasNext()) {
                cursor.next();
                count++;
            }
            return count;
        });
    }
}
