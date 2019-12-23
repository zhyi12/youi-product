/*
 * YOUI框架
 * Copyright 2018 the original author or authors.
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
import org.springframework.util.StringUtils;
import org.youi.framework.core.dataobj.Domain;

import javax.persistence.Column;
import java.util.HashMap;
import java.util.Map;

/**
 * 实体: 工作数据
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_dataworking_data")
public class WorkingData implements Domain{
	
	private static final long serialVersionUID = 5550593448054429614L;
	
	@Id
	@Column
	private String id;//主键

	@Column
	private String reportCode;//表号

	@Column
	private Integer physicalTableNum;//物理表序号

	@Column
	private String periodId;//报告期

	@Column
	private String createTime;//创建时间

	@Column
	private String updateTime;//更新时间

	@Column
	private String respondentId;//调查对象ID

	@Column
	private String areaId;//统计管理行政区划

	@Column
	private String categoryItemId;//分类目录项ID

	@Column
	private String gatheredWay;//数据采集方式

	@Column
	private Integer dataStatus;//数据状态

	private Map<String,String> rowText;//非数值类型数据

	private Map<String,Double> rowData;//数值类型数据

	private Map<String,Double> prevRowData;//上期数据

	private Map<String,Double> otherPrevRowData;//上年同期（月、季、旬）或 上上期（年）

	public String getId() {
		return id;
	}

	public WorkingData setId(String id) {
		this.id = id;
		return this;
	}

	public String getReportCode() {
		return reportCode;
	}

	public WorkingData setReportCode(String reportCode) {
		this.reportCode = reportCode;
		return this;
	}

	public Integer getPhysicalTableNum() {
		return physicalTableNum;
	}

	public WorkingData setPhysicalTableNum(Integer physicalTableNum) {
		this.physicalTableNum = physicalTableNum;
		return this;
	}

	public String getPeriodId() {
		return periodId;
	}

	public WorkingData setPeriodId(String periodId) {
		this.periodId = periodId;
		return this;
	}

	public String getCreateTime() {
		return createTime;
	}

	public WorkingData setCreateTime(String createTime) {
		this.createTime = createTime;
		return this;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public WorkingData setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
		return this;
	}

	public String getRespondentId() {
		return respondentId;
	}

	public WorkingData setRespondentId(String respondentId) {
		this.respondentId = respondentId;
		return this;
	}

	public String getAreaId() {
		return areaId;
	}

	public WorkingData setAreaId(String areaId) {
		this.areaId = areaId;
		return this;
	}

	public String getCategoryItemId() {
		return categoryItemId;
	}

	public WorkingData setCategoryItemId(String categoryItemId) {
		this.categoryItemId = categoryItemId;
		return this;
	}

	public String getGatheredWay() {
		return gatheredWay;
	}

	public WorkingData setGatheredWay(String gatheredWay) {
		this.gatheredWay = gatheredWay;
		return this;
	}

	public Integer getDataStatus() {
		return dataStatus;
	}

	public WorkingData setDataStatus(Integer dataStatus) {
		this.dataStatus = dataStatus;
		return this;
	}

	public Map<String, String> getRowText() {
		return rowText;
	}

	public WorkingData setRowText(Map<String, String> rowText) {
		this.rowText = rowText;
		return this;
	}

	public Map<String, Double> getRowData() {
		return rowData;
	}

	public WorkingData setRowData(Map<String, Double> rowData) {
		this.rowData = rowData;
		return this;
	}

	/**
	 * 增加数据类型列值
	 * @param columnName
	 * @param value
	 * @return
	 */
	public WorkingData addData(String columnName, Double value) {
		if(this.rowData==null){
			this.rowData = new HashMap<>();
		}
		if(!StringUtils.isEmpty(columnName)){
			this.rowData.put(columnName, value);
		}
		return this;
	}

	/**
	 * 增加文本类型列值
	 * @param columnName
	 * @param value
	 * @return
	 */
	public WorkingData addText(String columnName,String value) {
		if(this.rowText==null){
			this.rowText = new HashMap<>();
		}
		if(!StringUtils.isEmpty(columnName)){
			this.rowText.put(columnName,value);
		}
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((periodId == null) ? 0 : periodId.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
		result = prime * result + ((respondentId == null) ? 0 : respondentId.hashCode());
		result = prime * result + ((areaId == null) ? 0 : areaId.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final WorkingData other = (WorkingData) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (periodId == null) {
			if (other.periodId != null)
				return false;
		} else if (!periodId.equals(other.periodId))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		if (respondentId == null) {
			if (other.respondentId != null)
				return false;
		} else if (!respondentId.equals(other.respondentId))
			return false;
		if (areaId == null) {
			if (other.areaId != null)
				return false;
		} else if (!areaId.equals(other.areaId))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}