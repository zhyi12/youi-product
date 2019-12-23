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
package org.youi.dataworking.workingdata.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.CollectionUtils;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.util.StringUtils;

import javax.persistence.Column;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 交叉表数据
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Document(collection = "youi_dataworking_cross_data")
public class WorkingCrossData implements Domain {

    private static final long serialVersionUID = 1343871250406755322L;

    @Id
    @Column
    private String id;//主键

    private String periodId;//报告期

    private String attrId;//属性

    private String areaId;//行政区划

    private String indicatorId;//指标

    private Boolean acc;//累计值

    private Map<String,String> groupItems;//多个分组项

    private Double value;//值

    private Double unitRate = 1d;//单位换算率

    private int rowIndex;

    private int colIndex;

    public String getId() {
        return id;
    }

    public WorkingCrossData setId(String id) {
        this.id = id;
        return this;
    }

    public String getPeriodId() {
        return periodId;
    }

    public WorkingCrossData setPeriodId(String periodId) {
        this.periodId = periodId;
        return this;
    }

    public String getAttrId() {
        return attrId;
    }

    public WorkingCrossData setAttrId(String attrId) {
        this.attrId = attrId;
        return this;
    }

    public String getAreaId() {
        return areaId;
    }

    public WorkingCrossData setAreaId(String areaId) {
        this.areaId = areaId;
        return this;
    }

    public String getIndicatorId() {
        return indicatorId;
    }

    public WorkingCrossData setIndicatorId(String indicatorId) {
        this.indicatorId = indicatorId;
        return this;
    }

    public Boolean getAcc() {
        return acc;
    }

    public WorkingCrossData setAcc(Boolean acc) {
        this.acc = acc;
        return this;
    }

    public Map<String, String> getGroupItems() {
        return groupItems;
    }

    public WorkingCrossData setGroupItems(Map<String, String> groupItems) {
        this.groupItems = groupItems;
        return this;
    }

    public Double getValue() {
        return value;
    }

    public WorkingCrossData setValue(Double value) {
        this.value = value;
        return this;
    }

    public Double getUnitRate() {
        return unitRate;
    }

    public WorkingCrossData setUnitRate(Double unitRate) {
        this.unitRate = unitRate;
        return this;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public WorkingCrossData setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
        return this;
    }

    public int getColIndex() {
        return colIndex;
    }

    public WorkingCrossData setColIndex(int colIndex) {
        this.colIndex = colIndex;
        return this;
    }

    /**
     *
     * @param dimId
     * @param id
     * @return
     */
    public WorkingCrossData addGroupItem(String dimId, String id) {
        if(groupItems==null){
            groupItems = new HashMap<>();
        }

        if(StringUtils.isNotEmpty(dimId)){
            groupItems.put(dimId,id);
        }
        return this;
    }

    /**
     * 生成数据主键
     * 行政区划+报告期+指标ID+是否累计+hash code
     * hash code 由全keys字符生成
     * @return
     */
    public WorkingCrossData buildKey(){
        StringBuilder keys = new StringBuilder();
        keys.append(this.areaId)
                .append(this.periodId)
                .append(this.indicatorId)
                .append(Boolean.TRUE.equals(this.acc)?"1":"0");
        String prefix = keys.toString();
        if(!CollectionUtils.isEmpty(this.groupItems)){
            this.groupItems.forEach((dimId,itemId)->{
                keys.append(dimId).append(itemId);
            });
        }
        keys.append(this.attrId);

        this.id = prefix+Math.abs(keys.toString().hashCode());
        return this;
    }
}
