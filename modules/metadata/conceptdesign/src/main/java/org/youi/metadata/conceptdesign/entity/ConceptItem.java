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
package org.youi.metadata.conceptdesign.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.tree.TreeAttribute;

import javax.persistence.Column;

/**
 * 实体: 模型项
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_metadata_concept_item")
public class ConceptItem implements Domain{
	
	private static final long serialVersionUID = 3273056084174219232L;
	

	@Column
	private String name;//英文名称

	@Column
	private String group;//模型项类型

	@Column
	private String caption;//模型项名称

	@Column
	private String parentId;//父模型项ID
	@Id
	@Column
	private String id;//模型项ID

	@Column
	private String dataResourceId;//数据资源ID

	@TreeAttribute(TreeAttribute.TREE_ATTR_CODE)
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	@TreeAttribute(TreeAttribute.TREE_ATTR_GOURP)
	public String getGroup(){
		return this.group;
	}
	
	public void setGroup(String group){
		this.group = group;
	}

	@TreeAttribute(TreeAttribute.TREE_ATTR_TEXT)
	public String getCaption(){
		return this.caption;
	}
	
	public void setCaption(String caption){
		this.caption = caption;
	}

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

	public String getDataResourceId() {
		return dataResourceId;
	}

	public void setDataResourceId(String dataResourceId) {
		this.dataResourceId = dataResourceId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((caption == null) ? 0 : caption.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
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
		final ConceptItem other = (ConceptItem) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (caption == null) {
			if (other.caption != null)
				return false;
		} else if (!caption.equals(other.caption))
			return false;
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
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}