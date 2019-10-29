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
package org.youi.softproject.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.framework.core.dataobj.Domain;

import javax.persistence.Column;
/**
 * 实体: 软件项目
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_software_project")
public class SoftProject implements Domain{
	
	private static final long serialVersionUID = 2996339011725822966L;
	

	@Column
	private String enableDate;//启用日期

	@Column
	private String projectCaption;//项目名称

	@Column
	private String projectCode;//项目编号

	@Column
	private String projectGroup;//项目类别

	@Column
	private String projectOwner;//项目负责人员

	@Column
	private String businessOwner;//业务负责人员

	@Column
	private String supportOperator;//业务支持人员

	@Id
	@Column
	private String id;//主键

	@Column
	private String agencyId;//所属机构

	public String getEnableDate(){
		return this.enableDate;
	}
	
	public void setEnableDate(String enableDate){
		this.enableDate = enableDate;
	}
	public String getProjectCaption(){
		return this.projectCaption;
	}
	
	public void setProjectCaption(String projectCaption){
		this.projectCaption = projectCaption;
	}
	public String getProjectCode(){
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode){
		this.projectCode = projectCode;
	}
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public String getAgencyId(){
		return this.agencyId;
	}
	
	public void setAgencyId(String agencyId){
		this.agencyId = agencyId;
	}

	public String getProjectGroup() {
		return projectGroup;
	}

	public void setProjectGroup(String projectGroup) {
		this.projectGroup = projectGroup;
	}

	public String getProjectOwner() {
		return projectOwner;
	}

	public void setProjectOwner(String projectOwner) {
		this.projectOwner = projectOwner;
	}

	public String getBusinessOwner() {
		return businessOwner;
	}

	public void setBusinessOwner(String businessOwner) {
		this.businessOwner = businessOwner;
	}

	public String getSupportOperator() {
		return supportOperator;
	}

	public void setSupportOperator(String supportOperator) {
		this.supportOperator = supportOperator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enableDate == null) ? 0 : enableDate.hashCode());
		result = prime * result + ((projectCaption == null) ? 0 : projectCaption.hashCode());
		result = prime * result + ((projectCode == null) ? 0 : projectCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((agencyId == null) ? 0 : agencyId.hashCode());
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
		final SoftProject other = (SoftProject) obj;
		if (enableDate == null) {
			if (other.enableDate != null)
				return false;
		} else if (!enableDate.equals(other.enableDate))
			return false;
		if (projectCaption == null) {
			if (other.projectCaption != null)
				return false;
		} else if (!projectCaption.equals(other.projectCaption))
			return false;
		if (projectCode == null) {
			if (other.projectCode != null)
				return false;
		} else if (!projectCode.equals(other.projectCode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (agencyId == null) {
			if (other.agencyId != null)
				return false;
		} else if (!agencyId.equals(other.agencyId))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}