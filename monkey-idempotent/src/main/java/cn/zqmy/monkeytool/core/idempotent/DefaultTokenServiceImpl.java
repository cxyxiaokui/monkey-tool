package cn.zqmy.monkeytool.framework.idempotent;

import cn.hutool.core.util.StrUtil;
import cn.zqmy.monkeytool.cache.util.RedisStringUtil;
import cn.zqmy.monkeytool.framework.idempotent.exception.NoAccessException;
import cn.zqmy.monkeytool.framework.idempotent.exception.ParamValidException;
import cn.zqmy.monkeytool.framework.idempotent.properties.IdempotentProperties;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;

/**
 * 默认处理 接口幂等 TOKEN Service
 * 需要提前指定前缀生成TOKEN（generateToke），然后通过 checkToken 进行校验
 *
 * @Author: zhuoqianmingyue
 * @Date: 2020/11/09
 * @Description: 
 **/
public class DefaultTokenServiceImpl implements IdempotentTokenService {

    @Autowired
    private RedisStringUtil redisStringUtil;

    @Autowired
    private IdempotentProperties idempotentProperties;

    @Override
    public IdempotentProperties getIdempotentProperties() {
        return idempotentProperties;
    }

    public void setIdempotentProperties(IdempotentProperties idempotentProperties) {
        this.idempotentProperties = idempotentProperties;
    }

    /**
     * 创建 TOKEN
     * @param prefix Token 前缀
     * @param name 接口幂等名称
     * @return String TOKEN
     */
    @Override
    public String generateToke(String prefix,String name) {

        String uuid = UUID.randomUUID().toString();
        String token = String.format("%s:%s_%s", prefix, name, uuid);

        redisStringUtil.set(token,token,idempotentProperties.getTokenExpiredSecond());
        return token;
    }

    /**
     * 校验 TOKEN
     *
     * @param token TOKEN
     * @return boolean true 表示校验成功 false 表示校验失败
     */
    @Override
    public boolean checkToken(String token) {

        if (StrUtil.isBlank(token)) {
            throw new ParamValidException("Idempotent TOKEN is Blank");
        }

        if (!redisStringUtil.hasKey(token)) {
            throw new NoAccessException("Idempotent TOKEN is not exist");
        }

        redisStringUtil.del(token);
        return true;
    }
}
