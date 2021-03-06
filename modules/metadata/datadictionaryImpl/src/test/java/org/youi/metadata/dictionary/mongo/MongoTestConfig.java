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
package org.youi.metadata.dictionary.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.youi.framework.mongo.BaseDaoMongo;
import org.youi.metadata.dictionary.entity.MetaDataItem;
import org.youi.rowdata.xls.XlsRowFileExecutor;
import org.youi.tools.indexing.service.IndexingService;
import org.youi.tools.indexing.service.impl.IndexingServiceImpl;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Configuration
@EnableMongoRepositories(basePackages = "org.youi.metadata.dictionary.mongo",repositoryBaseClass = BaseDaoMongo.class)
@PropertySource("classpath:application-test.properties")
public class MongoTestConfig {

    /**
     * 数据项xls文件解析工具
     * @return
     */
    @Bean
    public XlsRowFileExecutor<MetaDataItem> metaDataItemXlsRowFileExecutor(){
        return new XlsRowFileExecutor<>();
    }

    @Bean
    public IndexingService indexingService(){
        return new IndexingServiceImpl();
    }
}
