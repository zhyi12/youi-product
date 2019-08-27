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
import org.youi.dataquery.engine.DataQueryConstants;
import org.youi.dataquery.engine.model.CubeColumns;
import org.youi.dataquery.engine.model.CubeRowData;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class CubeRowDataMapper implements RowMapper<CubeRowData> {

    @Nullable
    private ConversionService conversionService = DefaultConversionService.getSharedInstance();

    private CubeColumns cubeColumns;//

    public CubeRowDataMapper(CubeColumns cubeColumns) {
        this.cubeColumns = cubeColumns;
    }

    @Override
    public CubeRowData mapRow(ResultSet rs, int rowIndex) throws SQLException {
        CubeRowData cubeRowData = new CubeRowData();
        ResultSetMetaData rsmd = rs.getMetaData();
        int nrOfColumns = rsmd.getColumnCount();
        for(int i=1;i<=nrOfColumns;i++){
            Object value = rs.getObject(i);
            String columnName = rsmd.getColumnName(i).toUpperCase();//大写的列名称
            //目录项
            if(cubeColumns.isCatalog()&&columnName.equalsIgnoreCase(DataQueryConstants.DIM_CATALOG_ITEM_NAME)){
                cubeRowData.setCatalogItemId(parseStrValue(value));
            }else if(cubeColumns.getMeasureColumns().contains(columnName)){
                //计量列
                cubeRowData.addMeasure(columnName,rs.getDouble(i));
            }else if(!CollectionUtils.isEmpty(cubeColumns.getGroupColumns()) && cubeColumns.getGroupColumns().contains(columnName)){
                //分组列
                cubeRowData.addGroup(columnName,rs.getString(i));
            }
        }
        return cubeRowData;
    }

    /**
     *
     * @param object
     * @return
     */
    private String parseStrValue(Object object) {
        //TODO 使用 conversionService 执行类型转换
        return object.toString();
    }
}
