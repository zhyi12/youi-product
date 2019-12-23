/*
 * YOUI框架
 * Copyright 2016 the original author or authors.
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
package org.youi.metadata.metadimension;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.youi.framework.context.annotation.Module;
import org.youi.framework.mongo.BaseDaoMongo;
import org.youi.metadata.metadimension.nls.Messages;
/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Configuration("metadimension.config")
@Module(name = "metadimension",caption = "metadimension")
@ComponentScan(basePackages = {"org.youi.metadata.metadimension.service.impl"})
@EnableMongoRepositories(basePackages = "org.youi.metadata.metadimension.mongo",repositoryBaseClass = BaseDaoMongo.class)
public class ModuleConfig{
    @Bean
    public Messages messages(){
        return new Messages();
    }
}
