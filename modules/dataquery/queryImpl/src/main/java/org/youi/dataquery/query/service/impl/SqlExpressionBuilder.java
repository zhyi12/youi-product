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
package org.youi.dataquery.query.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.youi.dataquery.engine.DataQueryConstants;
import org.youi.dataquery.query.entity.SqlExpression;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class SqlExpressionBuilder {

    private final static String SQL_EMPTY_VALUE = "_EMPTY";
    /**
     *
     * @param sqlExpression
     * @return
     */
    public String buildSql(SqlExpression sqlExpression) {
        String source = sqlExpression.getSource();
        List<String> params = new ArrayList();
        if(!CollectionUtils.isEmpty(sqlExpression.getItems())){
            sqlExpression.getItems().forEach(dataResourceItem -> {
                if(DataQueryConstants.DATA_RESOURCE_TABLE_COLUMN.equals(dataResourceItem.getGroup())){
                    params.add(dataResourceItem.getColumnName());//数据列
                }else{
                    //数据表
                    params.add("\""+dataResourceItem.getCatalog()+"\".\""+dataResourceItem.getSchema()+"\".\""+dataResourceItem.getTableName()+"\"");//数据列
                }
            });
        }
        //TODO 增加sql语句替换处理规则
        return MessageFormat.format(source.replace("'"+SQL_EMPTY_VALUE+"'","''"+SQL_EMPTY_VALUE+"''"),params.toArray()).replace(" "," ");
    }
}
