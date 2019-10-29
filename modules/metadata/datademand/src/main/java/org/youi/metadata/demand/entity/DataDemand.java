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
 * 实体: 数据需求
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_metadata_demand")
public class DataDemand implements Domain{
	
	private static final long serialVersionUID = 6283898169414910483L;
	
	@Id
	@Column
	private String id;//主键

	@Column
	private String realmId;//需求域

	@Column
	private String demandCaption;//需求名称

	@TreeAttribute(TreeAttribute.TREE_ATTR_ID)
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}

	@TreeAttribute(TreeAttribute.TREE_ATTR_TEXT)
	public String getDemandCaption(){
		return this.demandCaption;
	}
	
	public void setDemandCaption(String demandCaption){
		this.demandCaption = demandCaption;
	}

	@TreeAttribute(TreeAttribute.TREE_ATTR_PID)
	public String getRealmId() {
		return realmId;
	}

	public void setRealmId(String realmId) {
		this.realmId = realmId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((demandCaption == null) ? 0 : demandCaption.hashCode());
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
		final DataDemand other = (DataDemand) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (demandCaption == null) {
			if (other.demandCaption != null)
				return false;
		} else if (!demandCaption.equals(other.demandCaption))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}