/*
 * YOUI框架
 * Copyright 2012 the original author or authors.
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
package org.youi.server.account.service;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.youi.framework.context.annotation.Module;
import org.youi.server.caller.config.ServerCallerProperties;
import org.youi.server.caller.service.ServerRestTemplate;

/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 上午11:48:47</p>
 */
@Configuration("server.account.config")
@Module(name = "server.account",caption = "server.account")
@ComponentScan(basePackages = {"org.youi.server.account.service.impl"})
@EnableConfigurationProperties({ServerCallerProperties.class,OAuth2ClientProperties.class})
public class ModuleConfig {

    @Bean("serverRestTemplate")
    public ServerRestTemplate serverRestTemplate(){
        ServerRestTemplate restTemplate = new ServerRestTemplate();
//        restTemplate.setRequestFactory();
        return restTemplate;
    }

    @Bean
    public ServerCallerProperties serverCallerProperties(){
        return new ServerCallerProperties();
    }

}