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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.framework.util.StringUtils;
import org.youi.metadata.dictionary.entity.DataTableCheckResult;
import org.youi.metadata.dictionary.entity.DataTableColumn;
import org.youi.metadata.dictionary.service.DataTableCheckService;
import org.youi.metadata.dictionary.service.DataTableColumnManager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("dataTableCheckService")
public class DataTableCheckServiceImpl implements DataTableCheckService {

    private  static final Log logger = LogFactory.getLog(DataTableCheckServiceImpl.class);

    @Autowired
    private DataDictionaryFinder dataDictionaryFinder;

    @Autowired
    private DataTableColumnManager dataTableColumnManager;

    @Override
    @EsbServiceMapping(trancode = "8001035301",caption = "通过数据库检查数据表")
    public DataTableCheckResult doCheckFromDb(@ServiceParam(name = "catalog") String catalog, @ServiceParam(name = "schema") String schema, @ServiceParam(name = "tableName") String tableName) {
        DataTableCheckResult dataTableCheckResult = new DataTableCheckResult();
        //数据库实际列
        List<Item> items = new ArrayList<>();
        try {
            if(StringUtils.isNotEmpty(catalog) && StringUtils.isNotEmpty(schema) &&StringUtils.isNotEmpty(tableName)){
                items = dataDictionaryFinder.findTableColumns(catalog, schema, tableName);
            }
        } catch (Exception e) {
            logger.error(MessageFormat.format("获取数据库表{0}.{1}.{2}的列失败：{3}",catalog,schema,tableName,e.getMessage()));
        }

        String dataResourceId = catalog+"_"+schema;
        //数据表列
        List<DataTableColumn> tableColumns = dataTableColumnManager.findByDataResourceIdAndTableName(dataResourceId,tableName);

        dataTableCheckResult.setCatalog(catalog);
        dataTableCheckResult.setSchema(schema);
        dataTableCheckResult.setTableName(tableName);

        dataTableCheckResult.setDbColumnItems(items);
        dataTableCheckResult.setTableColumnItems(buildMatchedColumnItems(tableColumns,items));

        return dataTableCheckResult;
    }

    /**
     *
     * @param tableColumns
     * @param dbColumnItems
     * @return
     */
    private List<Item> buildMatchedColumnItems(List<DataTableColumn> tableColumns,List<Item> dbColumnItems){
        List<Item> tableColumnItems = new ArrayList<>();
        if(!CollectionUtils.isEmpty(tableColumns) ){
            //构建数据库列map用户匹配
            Map<String,Item> dbColumnItemMap = new HashMap<>();
            if(!CollectionUtils.isEmpty(dbColumnItems)) {
                dbColumnItems.forEach(item -> dbColumnItemMap.put(item.getId().toUpperCase(), item));
            }

            tableColumns.forEach(tableColumn -> {
                tableColumnItems.add(buildTableColumnItem(tableColumn,dbColumnItemMap));
            });
        }

        return tableColumnItems;
    }

    /**
     *
     * @param tableColumn
     * @param dbColumnItemMap
     * @return
     */
    private Item buildTableColumnItem(DataTableColumn tableColumn, Map<String, Item> dbColumnItemMap) {
        Item item = new Item(tableColumn.getId(),tableColumn.getColumnCaption());
        if(StringUtils.isNotEmpty(tableColumn.getDataItemId())){
            String columnKey = tableColumn.getDataItemId().toUpperCase();
            if(dbColumnItemMap.containsKey(columnKey)){
                item.setMappedId(dbColumnItemMap.get(columnKey).getId());
            }
        }
        return item;
    }
}
