package cn.zqmy.monkeytool.cache.util;


import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhuoqianmingyue
 * @Date: 2021/04/15
 * @Description:
 **/
public class RedisListUtil<T> extends BaseRedisUtil {

    /**
     * 获取 key键 对应集合中 index索引的值
     *
     * @param key   键
     * @param index 索引
     * @return key键 对应集合中 index索引的值
     */
    public T index(String key, long index) {
        return (T) redisTemplate.opsForList().index(key, index);
    }

    /**
     * 根据 索引获取 list缓存值
     *
     * @param key   键
     * @param start 开始索引（下标从0开始）
     * @param end   偏移量（-1，则遍历全部数据）
     * @return
     */
    public List<T> range(String key, long start, long end) {
        return (List<T>) redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 将 value值放入 key对应的List集合 头部
     *
     * @param key   键
     * @param value 值
     */
    public void leftPush(String key, T value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 将 value值数组放入 key对应的List集合 头部
     *
     * @param key    键
     * @param values 值数组
     */
    public void leftPush(String key, T... values) {
        redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    public Long leftPush(String key, Collection<String> value) {
        return redisTemplate.opsForList().leftPushAll(key, value);
    }

    /**
     * 当list存在的时候才加入
     *
     * @param key
     * @param value
     * @return
     */
    public Long leftPushIfPresent(String key, String value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    /**
     * 将 value值放入 key对应的List集合 尾部
     *
     * @param key   键
     * @param value 值
     */
    public void rightPush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将 value值数组放入 key对应的List集合 尾部
     *
     * @param key    键
     * @param values 值数组
     */
    public void rightPush(String key, T... values) {
        redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * 当list存在的时候才加入
     *
     * @param key
     * @param value
     * @return
     */
    public Long rightPushIfPresent(String key, String value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    /**
     * 修改 key键对应的List集合中 索引为index的值
     *
     * @param key   键
     * @param index 索引
     * @param value 要更改的值
     */
    public void setIndex(String key, long index, T value) {
        redisTemplate.opsForList().set(key, index, value);
    }



    /**
     * 移出并获取列表的第一个元素
     *
     * @param key
     * @return 删除的元素
     */
    public T leftPop(String key) {
        return (T) redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key
     * @param timeout
     *            等待时间
     * @param unit
     *            时间单位
     * @return
     */
    public T leftPop(String key, long timeout, TimeUnit unit) {
        return (T) redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 移除并获取列表最后一个元素
     *
     * @param key
     * @return 删除的元素
     */
    public T rightPop(String key) {
        return (T) redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key
     * @param timeout
     *            等待时间
     * @param unit
     *            时间单位
     * @return
     */
    public T rightPop(String key, long timeout, TimeUnit unit) {
        return (T) redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     *
     * @param sourceKey
     * @param destinationKey
     * @return
     */
    public T rightPopAndLeftPush(String sourceKey, String destinationKey) {
        return (T) redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,
                destinationKey);
    }

    /**
     * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param sourceKey
     * @param destinationKey
     * @param timeout
     * @param unit
     * @return
     */
    public T rightPopAndLeftPush(String sourceKey, String destinationKey,
                                        long timeout, TimeUnit unit) {
        return (T) redisTemplate.opsForList().rightPopAndLeftPush(sourceKey,
                destinationKey, timeout, unit);
    }

    /**
     * 删除集合中值等于value得元素
     *
     * @param key
     * @param index
     *            index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素;
     *            index<0, 从尾部开始删除第一个值等于value的元素;
     * @param value
     * @return
     */
    public Long remove(String key, long index, T value) {
        return redisTemplate.opsForList().remove(key, index, value);
    }

    /**
     * 裁剪list
     *
     * @param key
     * @param start
     * @param end
     */
    public void trim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }


    /**
     * 获取 List缓存的长度
     *
     * @param key 键
     * @return
     */
    public long size(String key) {
        return redisTemplate.opsForList().size(key);
    }
}
