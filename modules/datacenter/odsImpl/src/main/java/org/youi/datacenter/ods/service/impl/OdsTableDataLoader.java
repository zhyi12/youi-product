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
import org.springframework.stereotype.Component;
import org.youi.datacenter.ods.entity.OdsTableMapping;
import org.youi.framework.core.dataobj.Record;
import org.youi.framework.util.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class OdsTableDataLoader {

    protected final Log logger = LogFactory.getLog(getClass());

    private String ODS_CATALOG = "cassandra";
    private String ODS_SCHEMA = "dw";

    /**
     * 加载数据
     * @param odsTableMapping
     * @param prevLoadTime
     */
    public void loadData(OdsTableMapping odsTableMapping, Long prevLoadTime) {
        String insertSql = buildInsertSql(odsTableMapping,prevLoadTime);
        //生成插入的sql语句
        logger.info(insertSql);
    }

    /**
     * TODO 使用不同的加载适配器
     * @param odsTableMapping
     * @return
     */
    private String buildInsertSql(OdsTableMapping odsTableMapping, Long prevLoadTime){
        //时间戳列
        String timestampColumn = odsTableMapping.getTimestampColumn();
        String sqlTemplate = "\n insert into \"{0}\".\"{1}\".\"{2}\" \n   select {3} \n  from \"{4}\".\"{5}\".\"{6}\" \n  where {7}>{8}";
        return MessageFormat.format(sqlTemplate,
                ODS_CATALOG,ODS_SCHEMA,odsTableMapping.getTableName(),
                buildOutputColumns(odsTableMapping.getColumnMapping()),
                odsTableMapping.getCatalog(),odsTableMapping.getSchema(),odsTableMapping.getTableName(),
                timestampColumn,prevLoadTime.toString());
    }

    /**
     * 输出字段
     * @param columnMapping
     * @return
     */
    private String buildOutputColumns(Record columnMapping) {
        List<String> columns = new ArrayList();
        columnMapping.forEach((key,value)->{
            columns.add(key+" as "+ value);
        });
        return StringUtils.collectionToDelimitedString(columns,",");
    }
}
