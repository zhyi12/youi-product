/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.product.datafile;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.youi.framework.context.ModulesRunnerBuilder;
import org.youi.framework.services.config.ServiceConfig;
import org.youi.service.boot.ResourceServerConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 */
@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class)
@EnableEurekaClient
@EnableOAuth2Sso
@EnableSwagger2
public class DataFileServerStarter {

    public static void main(String[] args) {
        new ModulesRunnerBuilder(ServiceConfig.class,
                ResourceServerConfig.class,
                DataFileServerStarter.class).run(args);
    }
}
