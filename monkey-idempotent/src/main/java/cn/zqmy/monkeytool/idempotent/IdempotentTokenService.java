package cn.zqmy.monkeytool.idempotent;

import cn.zqmy.monkeytool.idempotent.properties.IdempotentProperties;

/**
 * Token 服务
 *
 * @Author: zhuoqianmingyue
 * @Date: 2020/10/06
 * @Description:
 **/
public interface IdempotentTokenService {

    /**
     * 创建 TOKEN
     *
     * @param prefix Token 前缀
     * @param name   接口幂等名称
     * @return String TOKEN
     */
    String generateToke(String prefix, String name);

    /**
     * 校验 TOKEN
     *
     * @param token TOKEN
     * @return boolean true 表示校验成功 false 表示校验失败
     */
    boolean checkToken(String token);

    /**
     * 获取接口幂等自定义配置信息
     *
     * @return IdempotentProperties
     */
    IdempotentProperties getIdempotentProperties();
}
