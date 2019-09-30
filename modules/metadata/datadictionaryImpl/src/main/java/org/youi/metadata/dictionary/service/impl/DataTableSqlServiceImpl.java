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
package org.youi.metadata.dictionary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.metadata.dictionary.entity.DataTableColumn;
import org.youi.metadata.dictionary.entity.MetaDataItem;
import org.youi.metadata.dictionary.service.DataTableColumnManager;
import org.youi.metadata.dictionary.service.DataTableSqlService;
import org.youi.metadata.dictionary.service.MetaDataItemManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("dataTableSqlService")
public class DataTableSqlServiceImpl implements DataTableSqlService {

    @Autowired
    private DataTableSqlBuilder dataTableSqlBuilder;

    @Autowired
    private MetaDataItemManager metaDataItemManager;

    @Autowired
    private DataTableColumnManager dataTableColumnManager;


    @Override
    @EsbServiceMapping(trancode="8001030311",caption="生成createSql")
    public String buildCreateSql(@ServiceParam(name = "dataResourceId") String dataResourceId, @ServiceParam(name = "tableName") String tableName) {
        List<String> dataItemIds = new ArrayList<>();
        List<String> keyItemIds = new ArrayList<>();
        List<DataTableColumn> columnList = dataTableColumnManager.findByDataResourceIdAndTableName(dataResourceId,tableName);

        columnList.forEach(dataTableColumn -> dataItemIds.add(dataTableColumn.getDataItemId()));

        Map<String,List<MetaDataItem>> tableColumnMap = new LinkedHashMap<>();
        tableColumnMap.put(tableName,metaDataItemManager.getMetaDataItemByNames(dataItemIds));

        return dataTableSqlBuilder.buildCreateSql(tableColumnMap);
    }
}
