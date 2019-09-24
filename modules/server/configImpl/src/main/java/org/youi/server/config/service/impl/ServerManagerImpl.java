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
package org.youi.server.config.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.server.config.entity.ServerInfo;
import org.youi.server.config.service.ServerManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("serverManager")
public class ServerManagerImpl implements ServerManager {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${eureka.client.serviceUrl.defaultZone}")
    private String eurekaServiceUrl;

    @Override
    @EsbServiceMapping
    public List<ServerInfo> getServerInfos() {
        List<ServerInfo> serverInfos = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String jsonStr  = restTemplate.getForObject(eurekaServiceUrl.replace("/eureka/","")+"/appList",String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            serverInfos = objectMapper.readValue(jsonStr,serverInfos.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverInfos;
    }
}
