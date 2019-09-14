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
package org.youi.dataquery.query.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.framework.core.dataobj.Domain;

import javax.persistence.Column;
import java.util.List;

/**
 * 实体: 数据查询
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_data_query")
public class DataQuery implements Domain{
	
	private static final long serialVersionUID = -7419617019658878564L;

	@Id
	@Column
	private String id;//模版ID

	@Column
	private String name;//查询名称

	@Column
	private String caption;//中文描述

	private SqlExpression sqlExpression;//SQL表达式

	private List<QueryParam> queryParams;//查询参数

	private List<QueryColumn> queryColumns;//查询结果列集合

	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
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
	public SqlExpression getSqlExpression(){
		return this.sqlExpression;
	}
	
	public void setSqlExpression(SqlExpression sqlExpression){
		this.sqlExpression = sqlExpression;
	}

	public List<QueryParam> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(List<QueryParam> queryParams) {
		this.queryParams = queryParams;
	}

	public List<QueryColumn> getQueryColumns() {
		return queryColumns;
	}

	public void setQueryColumns(List<QueryColumn> queryColumns) {
		this.queryColumns = queryColumns;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((caption == null) ? 0 : caption.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((sqlExpression == null) ? 0 : sqlExpression.hashCode());
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
		final DataQuery other = (DataQuery) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
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
		if (sqlExpression == null) {
			if (other.sqlExpression != null)
				return false;
		} else if (!sqlExpression.equals(other.sqlExpression))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}