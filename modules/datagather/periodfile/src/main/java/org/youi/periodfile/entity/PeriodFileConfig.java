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
package org.youi.periodfile.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.framework.core.dataobj.Domain;
import org.youi.metadata.common.model.CrossReport;

import javax.persistence.Column;
/**
 * 实体: 固定期文件报表配置
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_datagather_period_file_config")
public class PeriodFileConfig implements Domain{
	
	private static final long serialVersionUID = -5444636962462418682L;

	@Column
	private String periodId;//报告期

	@Column
	private String areaId;//行政区划

	@Column
	private String createTime;//创建时间
	@Id
	@Column
	private String periodFileId;//固定期文件ID

	@Column
	private String reportCode;//报表编号

	private CrossReport crossReport;//交叉表

	public String getPeriodId(){
		return this.periodId;
	}
	
	public void setPeriodId(String periodId){
		this.periodId = periodId;
	}
	public String getAreaId(){
		return this.areaId;
	}
	
	public void setAreaId(String areaId){
		this.areaId = areaId;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public String getPeriodFileId(){
		return this.periodFileId;
	}
	
	public void setPeriodFileId(String periodFileId){
		this.periodFileId = periodFileId;
	}
	public String getReportCode(){
		return this.reportCode;
	}
	
	public void setReportCode(String reportCode){
		this.reportCode = reportCode;
	}

	public CrossReport getCrossReport() {
		return crossReport;
	}

	public PeriodFileConfig setCrossReport(CrossReport crossReport) {
		this.crossReport = crossReport;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((periodId == null) ? 0 : periodId.hashCode());
		result = prime * result + ((areaId == null) ? 0 : areaId.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((periodFileId == null) ? 0 : periodFileId.hashCode());
		result = prime * result + ((reportCode == null) ? 0 : reportCode.hashCode());
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
		final PeriodFileConfig other = (PeriodFileConfig) obj;
		if (periodId == null) {
			if (other.periodId != null)
				return false;
		} else if (!periodId.equals(other.periodId))
			return false;
		if (areaId == null) {
			if (other.areaId != null)
				return false;
		} else if (!areaId.equals(other.areaId))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (periodFileId == null) {
			if (other.periodFileId != null)
				return false;
		} else if (!periodFileId.equals(other.periodFileId))
			return false;
		if (reportCode == null) {
			if (other.reportCode != null)
				return false;
		} else if (!reportCode.equals(other.reportCode))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}