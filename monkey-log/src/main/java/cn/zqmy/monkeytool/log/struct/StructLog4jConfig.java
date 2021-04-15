package cn.zqmy.monkeytool.log.struct;

import com.github.structlog4j.StructLog4J;
import com.github.structlog4j.json.JsonFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;


/**
 * JSON 结构化日志配置类
 *
 * @author zhuoqianmingyue
 * @Date: 2021/04/15
 * @since 0.2.1
 **/
@Configuration
public class StructLog4jConfig {

    @Value("${spring.profiles.active:NA}")
    private String activeProfile;

    @Value("${spring.application.name:NA}")
    private String applicationName;

    /**
     * 初始化结构化日志必须携带应用和环境名称
     */

    @PostConstruct
    public void init() {

        StructLog4J.setFormatter(JsonFormatter.getInstance());

        StructLog4J.setMandatoryContextSupplier(() -> new Object[]{
                "env", activeProfile,
                "service", applicationName});
    }
}
