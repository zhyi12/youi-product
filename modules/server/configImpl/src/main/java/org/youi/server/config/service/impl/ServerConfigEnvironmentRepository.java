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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.youi.server.config.entity.ServerProperty;
import org.youi.server.config.mongo.ServerPropertyDao;

import java.util.*;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class ServerConfigEnvironmentRepository implements EnvironmentRepository {

    @Autowired(required = false)
    private ServerPropertyDao serverPropertyDao;

    @Override
    public Environment findOne(String application, String profile, String label) {
        String[] profiles = StringUtils.commaDelimitedListToStringArray(profile);
        Environment environment = new Environment(application, profiles, label, null,
                null);
        if(!"app".equals(application)){
            List<ServerProperty> serverProperties = serverPropertyDao.findByServerIdAndProfile(application,profile);
            Map<String,String> properties = new HashMap<>();
            serverProperties.forEach(serverProperty -> properties.put(serverProperty.getPropName(),serverProperty.getPropValue()));
            //配置微服务的服务参数
            environment.add(new PropertySource(application + "-"+profile, properties));
        }
        return environment;
    }
}
