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
package org.youi.datauth.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.framework.core.dataobj.Domain;

import javax.persistence.Column;
import java.util.List;

/**
 * 实体: 数据权限组
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_data_group")
@JsonIgnoreProperties("dataIds")
public class DataGroup implements Domain{
	
	private static final long serialVersionUID = -6276373173661316797L;
	
	@Id
	@Column
	private String id;//主键

	@Column
	private String groupCaption;//权限组名称

	private List<String> dataIds;

	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public String getGroupCaption(){
		return this.groupCaption;
	}
	
	public void setGroupCaption(String groupCaption){
		this.groupCaption = groupCaption;
	}

	public List<String> getDataIds() {
		return dataIds;
	}

	public void setDataIds(List<String> dataIds) {
		this.dataIds = dataIds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((groupCaption == null) ? 0 : groupCaption.hashCode());
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
		final DataGroup other = (DataGroup) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (groupCaption == null) {
			if (other.groupCaption != null)
				return false;
		} else if (!groupCaption.equals(other.groupCaption))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}