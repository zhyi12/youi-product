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
package org.youi.metadata.demand.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.tree.TreeAttribute;

import javax.persistence.Column;
/**
 * 实体: 数据需求域
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_metadata_demand_realm")
public class DataDemandRealm implements Domain{
	
	private static final long serialVersionUID = -2480401975129599207L;

	@Id
	@Column
	private String id;//主键

	@Column
	private String parentId;//父需求域

	@Column
	private String realmCaption;//需求域名称

	@TreeAttribute(TreeAttribute.TREE_ATTR_PID)
	public String getParentId(){
		return this.parentId;
	}
	
	public void setParentId(String parentId){
		this.parentId = parentId;
	}

	@TreeAttribute(TreeAttribute.TREE_ATTR_ID)
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}

	@TreeAttribute(TreeAttribute.TREE_ATTR_TEXT)
	public String getRealmCaption(){
		return this.realmCaption;
	}
	
	public void setRealmCaption(String realmCaption){
		this.realmCaption = realmCaption;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((realmCaption == null) ? 0 : realmCaption.hashCode());
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
		final DataDemandRealm other = (DataDemandRealm) obj;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (realmCaption == null) {
			if (other.realmCaption != null)
				return false;
		} else if (!realmCaption.equals(other.realmCaption))
			return false;
		return true;
	}
	
	public String toString(){
		return realmCaption;
	}
}