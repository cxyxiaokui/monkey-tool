package cn.zqmy.monkeytool.apiversion.config;

/**
 * 符号枚举类
 *
 * @author zhuoqianmingyue
 * @date 2020/04/15
 */
public enum VersionOperatorEnum {
    /**
     * 无值
     */
    NIL(""),
    /**
     * 小于
     */
    LT("<"),
    /**
     * 大于
     */
    GT(">"),
    /**
     * 小等
     */
    LTE("<="),
    /**
     * 大等
     */
    GTE(">="),
    /**
     * 不等
     */
    NE("!="),
    /**
     * 等
     */
    EQ("==");
    private String code;

    VersionOperatorEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static VersionOperatorEnum parse(String code) {
        for (VersionOperatorEnum operator : VersionOperatorEnum.values()) {
            if (operator.getCode().equalsIgnoreCase(code)) {
                return operator;
            }
        }
        return null;
    }

}
