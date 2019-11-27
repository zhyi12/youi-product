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
package org.youi.datacenter.ods.service;

import org.youi.datacenter.ods.entity.OdsTableMapping;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;

import java.util.List;

/**
 * 贴源数据库映射配置
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public interface OdsTableMappingManager {

    /**
     * 主键查询
     * @param id
     * @return
     */
    @EsbServiceMapping
    OdsTableMapping getOdsTableMapping(@ServiceParam(name = "id") String id);

    @EsbServiceMapping
    OdsTableMapping getOdsTableMappingByTable(
            @ServiceParam(name = "catalog") String catalog,
            @ServiceParam(name = "schema") String schema,
            @ServiceParam(name = "tableName") String tableName);

    /**
     *
     * @param odsTableMapping
     * @return
     */
    @EsbServiceMapping
    OdsTableMapping saveOdsTableMapping(OdsTableMapping odsTableMapping);

    /**
     * 主键删除
     * @param id
     */
    @EsbServiceMapping
    void removeOdsTableMapping(@ServiceParam(name = "id") String id);

    /************************************ 微服务内部方法 ************************************/
    /**
     *
     * @param catalog
     * @param schema
     * @return
     */
    List<OdsTableMapping> getTableMappingsByDataSource(String catalog,String schema);
}
