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

import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 立方体行数据对象
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class CubeRowData implements Domain{

    private static final long serialVersionUID = -5305979088111909388L;

    private Map<String,String> groups;//分组项健值

    private String catalogItemId;//

    private Map<String,Double> measures;//计量健值

    public Map<String, String> getGroups() {
        return groups;
    }

    public void setGroups(Map<String, String> groups) {
        this.groups = groups;
    }

    public Map<String, Double> getMeasures() {
        return measures;
    }

    public void setMeasures(Map<String, Double> measures) {
        this.measures = measures;
    }

    public String getCatalogItemId() {
        return catalogItemId;
    }

    public void setCatalogItemId(String catalogItemId) {
        this.catalogItemId = catalogItemId;
    }

    /**
     *
     * @param indicatorId
     * @param value
     */
    public void addMeasure(String indicatorId,Double value){
        if(StringUtils.isEmpty(indicatorId) || value==null){
            return;
        }
        if(measures==null){
            measures = new HashMap<>();
        }
        measures.put(indicatorId,value);
    }

    /**
     *
     * @param groupId
     * @param itemId
     */
    public void addGroup(String groupId,String itemId){
        if(StringUtils.isEmpty(groupId) || StringUtils.isEmpty(itemId)){
            return;
        }
        if(groups==null){
            groups = new HashMap<>();
        }
        groups.put(groupId,itemId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CubeRowData that = (CubeRowData) o;

        if (groups != null ? !groups.equals(that.groups) : that.groups != null) return false;
        return measures != null ? measures.equals(that.measures) : that.measures == null;
    }

    @Override
    public int hashCode() {
        int result = groups != null ? groups.hashCode() : 0;
        result = 31 * result + (measures != null ? measures.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CubeRowData{" +
                "groups=" + groups +
                ", catalogItemId='" + catalogItemId + '\'' +
                ", measures=" + measures +
                '}';
    }
}
