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
package org.youi.datacenter.ods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youi.datacenter.ods.entity.OdsTableMapping;
import org.youi.datacenter.ods.mongo.OdsTableMappingDao;
import org.youi.datacenter.ods.service.OdsTableMappingManager;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;

import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("odsTableMappingManager")
public class OdsTableMappingManagerImpl implements OdsTableMappingManager {

    @Autowired(required = false)
    private OdsTableMappingDao odsTableMappingDao;//ods table mapping

    @EsbServiceMapping
    public OdsTableMapping getOdsTableMappingByTable(
            @ServiceParam(name = "catalog") String catalog,
            @ServiceParam(name = "schema") String schema,
            @ServiceParam(name = "tableName") String tableName){
        return odsTableMappingDao.findByCatalogAndSchemaAndTableName(catalog,schema,tableName);
    }
    /**
     *
     * @param odsTableMapping
     * @return
     */
    @EsbServiceMapping
    public OdsTableMapping saveOdsTableMapping(OdsTableMapping odsTableMapping){
        return odsTableMappingDao.save(odsTableMapping);
    }

    @EsbServiceMapping
    public OdsTableMapping getOdsTableMapping(@ServiceParam(name = "id") String id){
        return odsTableMappingDao.get(id);
    }

    @EsbServiceMapping
    public void removeOdsTableMapping(@ServiceParam(name = "id") String id){
        odsTableMappingDao.remove(id);
    }

    @Override
    public  List<OdsTableMapping> getTableMappingsByDataSource(String catalog,String schema) {
        return odsTableMappingDao.findByCatalogAndSchema(catalog,schema);
    }

}
