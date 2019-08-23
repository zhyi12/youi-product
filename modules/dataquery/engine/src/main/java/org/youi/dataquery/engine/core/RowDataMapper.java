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
package org.youi.dataquery.engine.core;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.youi.dataquery.engine.entity.RowData;
import org.youi.dataquery.engine.entity.RowItem;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class RowDataMapper implements RowMapper<RowData> {

    @Nullable
    private ConversionService conversionService = DefaultConversionService.getSharedInstance();

    private Map<String,RowItem> columnItems;//输出列

    public RowDataMapper(Map<String,RowItem> columnItems){
        this.columnItems = columnItems;
    }

    @Override
    public RowData mapRow(ResultSet rs, int rowIndex) throws SQLException {
        RowData rowData = new RowData();
        ResultSetMetaData rsmd = rs.getMetaData();
        int nrOfColumns = rsmd.getColumnCount();

        if(CollectionUtils.isEmpty(columnItems)){
            columnItems  = parseColumnItems(rsmd,nrOfColumns);
        }

        for(int i=1;i<=nrOfColumns;i++){
            String columnName = rsmd.getColumnName(i);
            if(columnItems.containsKey(columnName)){
                RowItem columnItem = columnItems.get(columnName);
                rowData.put(columnItem.getId(),parseValue(columnItem,rs.getObject(i)));
            }
        }
        return rowData;
    }

    /**
     *
     * @param rsmd
     * @param nrOfColumns
     * @return
     * @throws SQLException
     */
    private Map<String,RowItem> parseColumnItems(ResultSetMetaData rsmd,int nrOfColumns) throws SQLException{
        Map<String,RowItem> columnItems = new HashMap<>();
        for(int i=1;i<=nrOfColumns;i++){
            String columnName = rsmd.getColumnName(i);
            RowItem rowItem = buildRowItem(columnName,rsmd,i);
            columnItems.put(columnName,rowItem);
        }
        return columnItems;
    }

    /**
     *
     * @param columnName
     * @param rsmd
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    private RowItem buildRowItem(String columnName, ResultSetMetaData rsmd, int columnIndex) throws SQLException{
        RowItem rowItem = new RowItem();
        rowItem.setColumnName(columnName);
        rowItem.setId(columnName);//设置ID为输出列名
        rowItem.setText(rsmd.getColumnLabel(columnIndex));
        rowItem.setDataType(rsmd.getColumnTypeName(columnIndex));//TODO 数据类型名转换
        return rowItem;
    }

    /**
     *
     * @param columnItem
     * @param object
     * @return
     */
    private Object parseValue(RowItem columnItem, Object object) {
        //TODO 使用 conversionService 执行类型转换
        return object;
    }
}
