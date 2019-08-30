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
package org.youi.product.exchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.youi.framework.context.ModulesRunnerBuilder;
import org.youi.framework.services.config.ServiceConfig;
import org.youi.product.exchange.engine.TcpMessageGateway;
import org.youi.service.boot.ResourceServerConfig;

/**
 *
 */
@SpringBootApplication
@RestController
@EnableIntegration
@EnableEurekaClient
@EnableOAuth2Sso
@EnableAutoConfiguration(exclude = IntegrationAutoConfiguration.class)
public class ExchangeServerStarter {

    @Autowired
    private TcpMessageGateway tcpMessageGateway;

    @RequestMapping(path = "/tcp/{serverName}/{serviceName}.json")
    @ResponseBody
    public String index(@PathVariable("serverName") String serverName,
                        @PathVariable("serviceName") String serviceName){

        tcpMessageGateway.sendMessage("message:"+serverName+"."+serviceName);
        return serverName+"."+serviceName;
    }

    public static void main(String[] args) {
        new ModulesRunnerBuilder(ServiceConfig.class,
                ResourceServerConfig.class,
                ExchangeServerStarter.class).run(args);
    }
}
