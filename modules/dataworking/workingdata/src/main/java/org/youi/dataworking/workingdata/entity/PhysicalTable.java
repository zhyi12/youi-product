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
import org.youi.framework.core.dataobj.Domain;
import org.youi.metadata.common.model.FieldItem;

import javax.persistence.Column;
import java.util.List;

/**
 * 实体: 物理表
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_dataworking_physical_table")
public class PhysicalTable implements Domain{
	
	private static final long serialVersionUID = -6255680930040124502L;

	@Column
	private String caption;//物理表描述
	@Id
	@Column
	private String id;//主键

	@Column
	private String reportCode;//报表编号

	@Column
	private String createTime;//创建时间

	@Column
	private String physicalTableNum;//物理表序号

	@Column
	private String updateTime;//更新时间

	private List<FieldItem> fieldItems;//字段集合

	public String getCaption(){
		return this.caption;
	}
	
	public void setCaption(String caption){
		this.caption = caption;
	}
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public String getReportCode(){
		return this.reportCode;
	}
	
	public void setReportCode(String reportCode){
		this.reportCode = reportCode;
	}
	public String getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public String getPhysicalTableNum(){
		return this.physicalTableNum;
	}
	
	public void setPhysicalTableNum(String physicalTableNum){
		this.physicalTableNum = physicalTableNum;
	}
	public String getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}

	public List<FieldItem> getFieldItems() {
		return fieldItems;
	}

	public PhysicalTable setFieldItems(List<FieldItem> fieldItems) {
		this.fieldItems = fieldItems;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caption == null) ? 0 : caption.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((reportCode == null) ? 0 : reportCode.hashCode());
		result = prime * result + ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((physicalTableNum == null) ? 0 : physicalTableNum.hashCode());
		result = prime * result + ((updateTime == null) ? 0 : updateTime.hashCode());
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
		final PhysicalTable other = (PhysicalTable) obj;
		if (caption == null) {
			if (other.caption != null)
				return false;
		} else if (!caption.equals(other.caption))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (reportCode == null) {
			if (other.reportCode != null)
				return false;
		} else if (!reportCode.equals(other.reportCode))
			return false;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (physicalTableNum == null) {
			if (other.physicalTableNum != null)
				return false;
		} else if (!physicalTableNum.equals(other.physicalTableNum))
			return false;
		if (updateTime == null) {
			if (other.updateTime != null)
				return false;
		} else if (!updateTime.equals(other.updateTime))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}