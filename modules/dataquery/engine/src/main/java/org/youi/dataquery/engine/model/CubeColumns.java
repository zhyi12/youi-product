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
package org.youi.dataquery.engine.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class CubeColumns implements Serializable{

    private static final long serialVersionUID = 8418154357452952600L;

    private boolean catalog;//

    private Set<String> groupColumns;//分组列

    private Set<String> measureColumns;//计量列

    public boolean isCatalog() {
        return catalog;
    }

    public void setCatalog(boolean catalog) {
        this.catalog = catalog;
    }

    public Set<String> getGroupColumns() {
        return groupColumns;
    }

    public void setGroupColumns(Set<String> groupColumns) {
        this.groupColumns = groupColumns;
    }

    public Set<String> getMeasureColumns() {
        return measureColumns;
    }

    public void setMeasureColumns(Set<String> measureColumns) {
        this.measureColumns = measureColumns;
    }

    /**
     *
     * @param columnName
     */
    public CubeColumns addGroupColumn(String columnName){
        if(groupColumns==null){
            groupColumns = new LinkedHashSet<>();
        }
        groupColumns.add(columnName.toUpperCase());
        return this;
    }

    /**
     *
     * @param columnName
     */
    public CubeColumns addMeasureColumn(String columnName){
        if(measureColumns==null){
            measureColumns = new LinkedHashSet<>();
        }
        measureColumns.add(columnName.toUpperCase());
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CubeColumns that = (CubeColumns) o;

        if (catalog != that.catalog) return false;
        if (groupColumns != null ? !groupColumns.equals(that.groupColumns) : that.groupColumns != null) return false;
        return measureColumns != null ? measureColumns.equals(that.measureColumns) : that.measureColumns == null;
    }

    @Override
    public int hashCode() {
        int result = (catalog ? 1 : 0);
        result = 31 * result + (groupColumns != null ? groupColumns.hashCode() : 0);
        result = 31 * result + (measureColumns != null ? measureColumns.hashCode() : 0);
        return result;
    }
}
