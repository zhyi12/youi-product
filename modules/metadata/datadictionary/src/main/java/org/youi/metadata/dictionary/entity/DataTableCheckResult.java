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
package org.youi.metadata.dictionary.entity;

import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.cube.Item;

import java.util.List;

/**
 * 数据表检查结果
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class DataTableCheckResult implements Domain{

    private static final long serialVersionUID = 3696402457129782499L;

    private String id;

    private String catalog;

    private String schema;

    private String tableName;

    private List<Item> dbColumnItems;

    private List<Item> tableColumnItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getTableName() {
        return tableName;
    }

    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Item> getDbColumnItems() {
        return dbColumnItems;
    }

    public void setDbColumnItems(List<Item> dbColumnItems) {
        this.dbColumnItems = dbColumnItems;
    }

    public List<Item> getTableColumnItems() {
        return tableColumnItems;
    }

    public void setTableColumnItems(List<Item> tableColumnItems) {
        this.tableColumnItems = tableColumnItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DataTableCheckResult that = (DataTableCheckResult) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (catalog != null ? !catalog.equals(that.catalog) : that.catalog != null) return false;
        if (schema != null ? !schema.equals(that.schema) : that.schema != null) return false;
        return tableName != null ? tableName.equals(that.tableName) : that.tableName == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (catalog != null ? catalog.hashCode() : 0);
        result = 31 * result + (schema != null ? schema.hashCode() : 0);
        result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
        return result;
    }
}
