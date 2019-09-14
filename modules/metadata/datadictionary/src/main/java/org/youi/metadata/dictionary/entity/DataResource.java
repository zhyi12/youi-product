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
 * 实体: 数据资源
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_metadata_resource")
public class DataResource implements Domain{
	
	private static final long serialVersionUID = -2071178684832540223L;

	@Id
	@Column
	private String id;//主键

	@Column
	private String caption;//资源名称

	@Column
	private String schema;//数据结构

	@Column
	private String catalog;//资源目录

	@Column
	private String status;//资源状态


	public String getCaption(){
		return this.caption;
	}
	
	public void setCaption(String caption){
		this.caption = caption;
	}
	public String getSchema(){
		return this.schema;
	}
	
	public void setSchema(String schema){
		this.schema = schema;
	}
	public String getCatalog(){
		return this.catalog;
	}
	
	public void setCatalog(String catalog){
		this.catalog = catalog;
	}
	public String getStatus(){
		return this.status;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}

	public DataResource buildKey(){
		this.id = this.catalog+"_"+this.schema;
		return this;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caption == null) ? 0 : caption.hashCode());
		result = prime * result + ((schema == null) ? 0 : schema.hashCode());
		result = prime * result + ((catalog == null) ? 0 : catalog.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		final DataResource other = (DataResource) obj;
		if (caption == null) {
			if (other.caption != null)
				return false;
		} else if (!caption.equals(other.caption))
			return false;
		if (schema == null) {
			if (other.schema != null)
				return false;
		} else if (!schema.equals(other.schema))
			return false;
		if (catalog == null) {
			if (other.catalog != null)
				return false;
		} else if (!catalog.equals(other.catalog))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
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