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
package org.youi.datacenter.ods.cassandra;

import com.datastax.driver.core.DataType;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.cql.CqlTemplate;
import org.springframework.data.cassandra.core.cql.generator.CreateTableCqlGenerator;
import org.springframework.data.cassandra.core.cql.keyspace.CreateTableSpecification;
import org.springframework.stereotype.Component;
import org.youi.datacenter.ods.entity.OdsTableMapping;
import org.youi.datacenter.ods.service.IOdsTableBuilder;
import org.youi.framework.core.dataobj.Record;
import org.youi.metadata.common.MetaItemDataType;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class OdsCassandraTableBuilder implements IOdsTableBuilder {

    @Autowired
    private CqlTemplate cqlTemplate;

    /**
     * 创建数据中心的贴源数据表
     * @param odsTableMapping
     */
    @Override
    public void createTableIfNotExist(OdsTableMapping odsTableMapping) {
        CreateTableSpecification createTableSpecification =
                CreateTableSpecification.createTable(odsTableMapping.getTableName())
                        .ifNotExists(true);

        String[] keyColumns = odsTableMapping.getKeyColumns();
        //主键列
        for(String keyColumn:keyColumns){
            createTableSpecification = createTableSpecification.partitionKeyColumn(keyColumn, DataType.text());
        }
        //普通列
        for(String column:odsTableMapping.getColumnMapping().keySet()){
            if(!ArrayUtils.contains(keyColumns,column)){
                createTableSpecification = createTableSpecification.column(column, parseColumnType(column,odsTableMapping.getColumnTypes()));
            }
        }
        //生成
        cqlTemplate.execute(CreateTableCqlGenerator.toCql(createTableSpecification));
    }

    /**
     * 解析数据类型
     * @param column
     * @param columnTypes
     * @return
     */
    private DataType parseColumnType(String column, Record columnTypes) {
        String type = (columnTypes!=null && columnTypes.containsKey(column)) ?columnTypes.get(column).toString():MetaItemDataType.string.getKey();
        //TODO 使用type converter来处理类型转换
        if(MetaItemDataType.integer.getKey().equals(type)){
            return DataType.cint();//整型
        }else if(MetaItemDataType.bigint.getKey().equals(type)){
            return DataType.bigint();//长整型
        }else if(MetaItemDataType.number.getKey().equals(type)){
            return DataType.cdouble();//数值
        }else if(MetaItemDataType.date.getKey().equals(type)){
            return DataType.date();//日期型
        }else {
            return DataType.text();//字符
        }
    }
}
