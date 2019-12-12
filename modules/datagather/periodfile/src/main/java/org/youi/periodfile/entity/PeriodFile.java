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

import javax.persistence.Column;
/**
 * 实体: 固定期文件
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_datagather_period_file")
public class PeriodFile implements Domain{
	
	private static final long serialVersionUID = -4782586798215485327L;

	@Id
	@Column
	private String id;//主键

	@Column
	private Long createTime;//创建时间

	@Column
	private Long startTime;//上传开始时间

	@Column
	private String periodId;//报告期

	@Column
	private String filePath;//文件路径

	private String fileName;//文件名称

	@Column
	private String surveyTaskId;//调查方案

	@Column
	private Long uploadTime;//上传时间

	@Column
	private String agencyId;//机构

	@Column
	private String reportCode;//报表编号

	@Column
	private String memo;//上传说明

	@Column
	private String areaId;//行政区划

	@Column
	private Long endTime;//上传截止时间

	public Long getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(Long createTime){
		this.createTime = createTime;
	}
	public Long getStartTime(){
		return this.startTime;
	}
	
	public void setStartTime(Long startTime){
		this.startTime = startTime;
	}
	public String getPeriodId(){
		return this.periodId;
	}
	
	public void setPeriodId(String periodId){
		this.periodId = periodId;
	}
	public String getFilePath(){
		return this.filePath;
	}
	
	public void setFilePath(String filePath){
		this.filePath = filePath;
	}
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public String getSurveyTaskId(){
		return this.surveyTaskId;
	}
	
	public void setSurveyTaskId(String surveyTaskId){
		this.surveyTaskId = surveyTaskId;
	}
	public Long getUploadTime(){
		return this.uploadTime;
	}
	
	public void setUploadTime(Long uploadTime){
		this.uploadTime = uploadTime;
	}
	public String getAgencyId(){
		return this.agencyId;
	}
	
	public void setAgencyId(String agencyId){
		this.agencyId = agencyId;
	}
	public String getReportCode(){
		return this.reportCode;
	}
	
	public void setReportCode(String reportCode){
		this.reportCode = reportCode;
	}
	public String getMemo(){
		return this.memo;
	}
	
	public void setMemo(String memo){
		this.memo = memo;
	}
	public String getAreaId(){
		return this.areaId;
	}
	
	public void setAreaId(String areaId){
		this.areaId = areaId;
	}
	public Long getEndTime(){
		return this.endTime;
	}
	
	public void setEndTime(Long endTime){
		this.endTime = endTime;
	}

	public String getFileName() {
		return fileName;
	}

	public PeriodFile setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((periodId == null) ? 0 : periodId.hashCode());
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((surveyTaskId == null) ? 0 : surveyTaskId.hashCode());
		result = prime * result + ((uploadTime == null) ? 0 : uploadTime.hashCode());
		result = prime * result + ((agencyId == null) ? 0 : agencyId.hashCode());
		result = prime * result + ((reportCode == null) ? 0 : reportCode.hashCode());
		result = prime * result + ((memo == null) ? 0 : memo.hashCode());
		result = prime * result + ((areaId == null) ? 0 : areaId.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
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
		final PeriodFile other = (PeriodFile) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (periodId == null) {
			if (other.periodId != null)
				return false;
		} else if (!periodId.equals(other.periodId))
			return false;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (surveyTaskId == null) {
			if (other.surveyTaskId != null)
				return false;
		} else if (!surveyTaskId.equals(other.surveyTaskId))
			return false;
		if (uploadTime == null) {
			if (other.uploadTime != null)
				return false;
		} else if (!uploadTime.equals(other.uploadTime))
			return false;
		if (agencyId == null) {
			if (other.agencyId != null)
				return false;
		} else if (!agencyId.equals(other.agencyId))
			return false;
		if (reportCode == null) {
			if (other.reportCode != null)
				return false;
		} else if (!reportCode.equals(other.reportCode))
			return false;
		if (memo == null) {
			if (other.memo != null)
				return false;
		} else if (!memo.equals(other.memo))
			return false;
		if (areaId == null) {
			if (other.areaId != null)
				return false;
		} else if (!areaId.equals(other.areaId))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}