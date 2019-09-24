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
package org.youi.base;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.youi.agency.entity.Agency;
import org.youi.framework.context.ModulesRunnerBuilder;
import org.youi.framework.services.config.ServiceConfig;
import org.youi.service.boot.ResourceServerConfig;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@SpringBootApplication
@RestController
@EnableIntegration
@EnableEurekaClient
@EnableOAuth2Sso
@EnableSwagger2
public class BaseServerStarter {

    @ApiOperation(value = "获取机构列表",notes = "获取用户机构详细描述")
    @RequestMapping(value = "/getAgencies.json",method = RequestMethod.GET)
    public List<Agency> getAgencies(String id){
        ConfigServicePropertySourceLocator configServicePropertySourceLocator;
        List list = new ArrayList<>();
        Agency agency = new Agency();
        agency.setAreaId("111111");
        agency.setCode("code");
        list.add(agency);
        return list;
    }

    public static void main(String[] args) {
        new ModulesRunnerBuilder(ServiceConfig.class,
                ResourceServerConfig.class,
                BaseServerStarter.class).run(args);
    }

}
