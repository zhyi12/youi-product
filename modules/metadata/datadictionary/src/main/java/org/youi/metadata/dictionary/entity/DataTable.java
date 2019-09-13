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
package org.youi.metadata.dictionary.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.framework.core.dataobj.Domain;

import javax.persistence.Column;
/**
 * 实体: 数据表
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_metadata_table")
public class DataTable implements Domain{
	
	private static final long serialVersionUID = -6709731082121740579L;

	@Id
	@Column
	private String id;//主键

	@Column
	private String tableName;//表名

	@Column
	private String dataResourceId;//资源ID

	@Column
	private String tableCaption;//中文名称


	public String getTableName(){
		return this.tableName;
	}
	
	public void setTableName(String tableName){
		this.tableName = tableName;
	}
	public String getDataResourceId(){
		return this.dataResourceId;
	}
	
	public void setDataResourceId(String dataResourceId){
		this.dataResourceId = dataResourceId;
	}
	public String getTableCaption(){
		return this.tableCaption;
	}
	
	public void setTableCaption(String tableCaption){
		this.tableCaption = tableCaption;
	}
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
		result = prime * result + ((dataResourceId == null) ? 0 : dataResourceId.hashCode());
		result = prime * result + ((tableCaption == null) ? 0 : tableCaption.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		final DataTable other = (DataTable) obj;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		if (dataResourceId == null) {
			if (other.dataResourceId != null)
				return false;
		} else if (!dataResourceId.equals(other.dataResourceId))
			return false;
		if (tableCaption == null) {
			if (other.tableCaption != null)
				return false;
		} else if (!tableCaption.equals(other.tableCaption))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}