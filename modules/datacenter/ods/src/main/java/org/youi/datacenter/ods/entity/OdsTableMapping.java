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
package org.youi.datacenter.ods.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.Record;
import org.youi.framework.util.StringUtils;

import javax.persistence.Column;
import java.util.Objects;

/**
 * 数据中心贴源库-贴源库表字段抽取的规则
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Document("youi_dc_ods_table_mapping")
public class OdsTableMapping implements Domain {

    private static final long serialVersionUID = 2058218928141915965L;

    @Id
    private String id;//使用业务主键

    @Column
    private String catalog;//

    @Column
    private String schema;//

    @Column
    private String tableName;//表名称

    private String[] keyColumns;//主键字段

    private Record columnMapping;//列映射

    private Record columnTypes;//列字段类型

    private String timestampColumn = "update_time";//时间戳列

    public String getId() {
        return id;
    }

    public OdsTableMapping setId(String id) {
        this.id = id;
        return this;
    }

    public String getCatalog() {
        return catalog;
    }

    public OdsTableMapping setCatalog(String catalog) {
        this.catalog = catalog;
        return this;
    }

    public String getSchema() {
        return schema;
    }

    public OdsTableMapping setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public OdsTableMapping setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public Record getColumnMapping() {
        return columnMapping;
    }

    public OdsTableMapping setColumnMapping(Record columnMapping) {
        this.columnMapping = columnMapping;
        return this;
    }

    public String getTimestampColumn() {
        return timestampColumn;
    }

    public OdsTableMapping setTimestampColumn(String timestampColumn) {
        this.timestampColumn = timestampColumn;
        return this;
    }

    public String[] getKeyColumns() {
        return keyColumns;
    }

    public void setKeyColumns(String[] keyColumns) {
        this.keyColumns = keyColumns;
    }

    /**
     * 构建主键
     * @return
     */
    public OdsTableMapping buildKey(){
        this.id = StringUtils.arrayToDelimitedString(new String[]{
                this.catalog,
                this.schema,
                this.tableName
        },"_");
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OdsTableMapping that = (OdsTableMapping) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(catalog, that.catalog) &&
                Objects.equals(schema, that.schema) &&
                Objects.equals(tableName, that.tableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, catalog,schema, tableName);
    }

    public Record getColumnTypes() {
        return columnTypes;
    }

    public OdsTableMapping setColumnTypes(Record columnTypes) {
        this.columnTypes = columnTypes;
        return this;
    }

    public OdsTableMapping addColumnMapping(String columnName, String mapName) {
        if(columnMapping==null){
            columnMapping = new Record();
        }
        if(StringUtils.isNotEmpty(columnName)&&StringUtils.isNotEmpty(mapName)){
            columnMapping.put(columnName,mapName);
        }
        return this;
    }

    /**
     *
     * @param columnName
     * @param dataType
     * @return
     */
    public OdsTableMapping addColumnType(String columnName, String dataType){
        if(columnTypes==null){
            columnTypes = new Record();
        }
        if(StringUtils.isNotEmpty(columnName)&&StringUtils.isNotEmpty(dataType)){
            columnTypes.put(columnName,dataType);
        }
        return this;
    }
}
