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
package org.youi.service.registry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.shared.Application;
import com.netflix.eureka.EurekaServerContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */

@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient      //让该应用注册成为eureka客户端，以获得服务发现的能力
@RestController
public class EurekaStarter {

    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = "/appList" , method = RequestMethod.GET)
    @ResponseBody
    public String appList(){
        List<Application> records =  EurekaServerContextHolder.getInstance().getServerContext().getRegistry().getSortedApplications();
        try {
            return objectMapper.writeValueAsString(records);
        } catch (JsonProcessingException e) {
            //e.printStackTrace();
        }
        return "[]";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(EurekaStarter.class, args);
    }
}
