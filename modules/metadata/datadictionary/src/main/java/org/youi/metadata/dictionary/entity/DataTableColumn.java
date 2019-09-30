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
 * 实体: 数据列
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_metadata_table_column")
public class DataTableColumn implements Domain{
	
	private static final long serialVersionUID = -5319966493923651753L;
	

	@Column
	private String dataResourceId;//资源目录ID

	@Column
	private String columnName;//列名

	@Column
	private String columnCaption;//列中文描述

	@Column
	private String tableName;//表名

	@Column
	private String dataItemId;//数据项ID
	@Id
	@Column
	private String id;//主键

	@Column
	private String primary;//"0","1" 是否主键

	public String getDataResourceId(){
		return this.dataResourceId;
	}
	
	public void setDataResourceId(String dataResourceId){
		this.dataResourceId = dataResourceId;
	}
	public String getColumnName(){
		return this.columnName;
	}
	
	public void setColumnName(String columnName){
		this.columnName = columnName;
	}
	public String getTableName(){
		return this.tableName;
	}
	
	public void setTableName(String tableName){
		this.tableName = tableName;
	}
	public String getDataItemId(){
		return this.dataItemId;
	}
	
	public void setDataItemId(String dataItemId){
		this.dataItemId = dataItemId;
	}
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}

	public String getColumnCaption() {
		return columnCaption;
	}

	public void setColumnCaption(String columnCaption) {
		this.columnCaption = columnCaption;
	}

	public String getPrimary() {
		return primary;
	}

	public void setPrimary(String primary) {
		this.primary = primary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataResourceId == null) ? 0 : dataResourceId.hashCode());
		result = prime * result + ((columnName == null) ? 0 : columnName.hashCode());
		result = prime * result + ((tableName == null) ? 0 : tableName.hashCode());
		result = prime * result + ((dataItemId == null) ? 0 : dataItemId.hashCode());
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
		final DataTableColumn other = (DataTableColumn) obj;
		if (dataResourceId == null) {
			if (other.dataResourceId != null)
				return false;
		} else if (!dataResourceId.equals(other.dataResourceId))
			return false;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		if (dataItemId == null) {
			if (other.dataItemId != null)
				return false;
		} else if (!dataItemId.equals(other.dataItemId))
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