package org.youi.datauth;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.youi.framework.context.annotation.Module;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Configuration("datauthClient.config")
@Module(name = "datauthClient",caption = "datauthClient")
@ComponentScan(basePackages = {"org.youi.datauth.service"})
@EnableFeignClients
public class ModuleConfig {

}
