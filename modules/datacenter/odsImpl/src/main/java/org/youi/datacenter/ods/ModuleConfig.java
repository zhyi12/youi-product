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
package org.youi.datacenter.ods;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.youi.datacenter.ods.config.OdsProperties;
import org.youi.framework.context.annotation.Module;
import org.youi.framework.mongo.BaseDaoMongo;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Configuration("datacenter.ods.config")
@Module(name = "datacenter.ods",caption = "datacenter.ods")
@ComponentScan({"org.youi.datacenter.ods.service.impl"})
@EnableMongoRepositories(basePackages = "org.youi.datacenter.ods.mongo",repositoryBaseClass = BaseDaoMongo.class)
@EnableConfigurationProperties(OdsProperties.class)
public class ModuleConfig {

}
