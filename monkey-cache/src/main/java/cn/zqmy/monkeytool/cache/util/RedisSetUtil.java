package cn.zqmy.monkeytool.cache.util;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Redis Set 操作工具类
 *
 * @Author: zhuoqianmingyue
 * @Date: 2021/04/15
 * @Description:
 **/
public class RedisSetUtil<T> extends BaseRedisUtil {

    /**
     * 将数据放在 Set缓存
     *
     * @param key    键
     * @param values 值数组
     * @return 成功个数
     */
    public long addSet(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 移除 key键 对应的Set集合中 value数组
     *
     * @param key    键
     * @param values 要移除的值数组
     * @return 移除成功的个数
     */
    public long remove(String key, T... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    /**
     * 移除并返回集合的一个随机元素
     *
     * @param key
     * @return
     */
    public T pop(String key) {
        return (T) redisTemplate.opsForSet().pop(key);
    }

    /**
     * 将元素value从一个集合移到另一个集合
     *
     * @param key
     * @param value
     * @param destKey
     * @return
     */
    public Boolean move(String key, T value, String destKey) {
        return redisTemplate.opsForSet().move(key, value, destKey);
    }

    /**
     * 获取 key键 对应的Set集合的长度
     *
     * @param key 键
     * @return
     */
    public long size(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 查找 key键 对应的Set集合中 是否包含value值
     *
     * @param key   键
     * @param value 值
     * @return 包含：true；不包含：false
     */
    public boolean isMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }


    /**
     * 获取 key键 对应的 Set集合
     *
     * @param key 键
     * @return key键 对应的 Set集合
     */
    public Set<T> members(String key) {
        return (Set<T>) redisTemplate.opsForSet().members(key);
    }


    /**
     * 获取两个集合的交集
     *
     * @param key
     * @param otherKey
     * @return
     */
    public Set<T> intersect(String key, String otherKey) {
        return (Set<T>) redisTemplate.opsForSet().intersect(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的交集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public Set<T> intersect(String key, Collection<String> otherKeys) {
        return (Set<T>) redisTemplate.opsForSet().intersect(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的交集存储到destKey集合中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long intersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey,
                destKey);
    }

    /**
     * key集合与多个集合的交集存储到destKey集合中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long intersectAndStore(String key, Collection<String> otherKeys,
                                  String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKeys,
                destKey);
    }

    /**
     * 获取两个集合的并集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public Set<T> union(String key, String otherKeys) {
        return (Set<T>) redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * 获取key集合与多个集合的并集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public Set<T> union(String key, Collection<String> otherKeys) {
        return (Set<T>) redisTemplate.opsForSet().union(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的并集存储到destKey中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long unionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * key集合与多个集合的并集存储到destKey中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long sUnionAndStore(String key, Collection<String> otherKeys,
                               String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * 获取两个集合的差集
     *
     * @param key
     * @param otherKey
     * @return
     */
    public Set<T> difference(String key, String otherKey) {
        return (Set<T>) redisTemplate.opsForSet().difference(key, otherKey);
    }

    /**
     * 获取key集合与多个集合的差集
     *
     * @param key
     * @param otherKeys
     * @return
     */
    public Set<T> difference(String key, Collection<String> otherKeys) {
        return (Set<T>) redisTemplate.opsForSet().difference(key, otherKeys);
    }

    /**
     * key集合与otherKey集合的差集存储到destKey中
     *
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long sDifference(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey,
                destKey);
    }

    /**
     * key集合与多个集合的差集存储到destKey中
     *
     * @param key
     * @param otherKeys
     * @param destKey
     * @return
     */
    public Long sDifference(String key, Collection<String> otherKeys,
                            String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKeys,
                destKey);
    }


    /**
     * 将数据放在 Set缓存，并设置 失效时间
     *
     * @param key     键
     * @param values  值数组
     * @param timeout 失效时间（单位：秒，小于等于0 表示 永久有效）
     * @return
     */
    public long addSet(String key, T[] values, long timeout) {
        long count = redisTemplate.opsForSet().add(key, values);
        expire(key, timeout);
        return count;
    }

    /**
     * 随机获取集合中的一个元素
     *
     * @param key
     * @return
     */
    public T randomMember(String key) {
        return (T) redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 随机获取集合中count个元素
     *
     * @param key
     * @param count
     * @return
     */
    public List<T> randomMembers(String key, long count) {
        return (List<T>) redisTemplate.opsForSet().randomMembers(key, count);
    }

    /**
     * 随机获取集合中count个元素并且去除重复的
     *
     * @param key
     * @param count
     * @return
     */
    public Set<T> sDistinctRandomMembers(String key, long count) {
        return (Set<T>) redisTemplate.opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * @param key
     * @param options
     * @return
     */
    public Cursor<T> sScan(String key, ScanOptions options) {
        return (Cursor<T>) redisTemplate.opsForSet().scan(key, options);
    }
}
