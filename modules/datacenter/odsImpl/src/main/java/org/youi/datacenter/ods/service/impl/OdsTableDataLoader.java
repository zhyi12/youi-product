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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.youi.datacenter.ods.config.OdsProperties;
import org.youi.datacenter.ods.entity.OdsTableMapping;
import org.youi.datacenter.ods.service.IOdsTableBuilder;
import org.youi.dataquery.engine.service.IInsertService;
import org.youi.dataquery.presto.dao.PrestoSqlDao;
import org.youi.dataquery.presto.service.impl.PrestoQueryService;
import org.youi.framework.core.dataobj.Record;
import org.youi.framework.util.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class OdsTableDataLoader {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private OdsProperties odsProperties;

    @Autowired(required = false)
    private IOdsTableBuilder odsTableBuilder;

    @Autowired(required = false)
    private IInsertService insertService;
    /**
     * 加载数据
     * @param odsTableMapping
     * @param prevLoadTime
     */
    public void loadData(OdsTableMapping odsTableMapping, Long prevLoadTime) {
        if(odsTableBuilder!=null){
            //不存在则创建表
            odsTableBuilder.createTableIfNotExist(odsTableMapping);
            String insertSql = buildInsertSql(odsTableMapping,prevLoadTime);//生成插入的sql语句
            if(insertService!=null){
                logger.info(insertSql);//待执行的插入语句
                insertService.doInsertInto(insertSql);
            }
        }
    }

    /**
     * TODO 使用不同的加载适配器
     * @param odsTableMapping
     * @return
     */
    private String buildInsertSql(OdsTableMapping odsTableMapping, Long prevLoadTime){
        //时间戳列
        String timestampColumn = odsTableMapping.getTimestampColumn();
        String sqlTemplate = "\n insert into \"{0}\".\"{1}\".\"{2}\" ({9}) \n  select {3} \n  from \"{4}\".\"{5}\".\"{6}\" \n  where {7}>{8}";

        Map<String,String> mapping = new LinkedHashMap(odsTableMapping.getColumnMapping());//有序的map

        return MessageFormat.format(sqlTemplate,
                odsProperties.getCatalog(),odsProperties.getSchema(),odsTableMapping.getTableName(),
                buildOutputColumns(mapping),
                odsTableMapping.getCatalog(),odsTableMapping.getSchema(),odsTableMapping.getTableName(),
                timestampColumn,prevLoadTime.toString(),
                StringUtils.arrayToCommaDelimitedString(mapping.keySet().toArray(new String[0])));
    }

    /**
     * 输出字段
     * @param columnMapping
     * @return
     */
    private String buildOutputColumns(Map<String,String> columnMapping) {
        List<String> columns = new ArrayList();
        columnMapping.forEach((key,value)->{
            columns.add(key+" as "+ value);
        });
        return StringUtils.collectionToDelimitedString(columns,",");
    }
}
