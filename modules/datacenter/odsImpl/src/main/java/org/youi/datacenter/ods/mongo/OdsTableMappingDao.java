/*
 * Copyright 2018-2024 the original author or authors.
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
package org.youi.datacenter.ods.mongo;

import org.youi.datacenter.ods.entity.OdsTableMapping;
import org.youi.framework.mongo.DaoMongo;

import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public interface OdsTableMappingDao extends DaoMongo<OdsTableMapping,String> {

    /**
     * 获取配置集合
     * @param catalog
     * @param schema
     * @return
     */
    List<OdsTableMapping> findByCatalogAndSchema(String catalog, String schema);

    /**
     * 业务主键获取配置
     * @param catalog
     * @param schema
     * @param tableName
     * @return
     */
    OdsTableMapping findByCatalogAndSchemaAndTableName(String catalog, String schema, String tableName);
}
