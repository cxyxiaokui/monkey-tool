package cn.zqmy.monkeytool.common.core;

/**
 * 响应码枚举
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 **/
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(1),

    /**
     * 创建成功
     */
    NOCONTENT(0),

    /**
     * 失败
     */
    FAIL(-1);

    private int code;

    public int getCode() {
        return code;
    }

    ResultCode(int code) {
        this.code = code;
    }
}
