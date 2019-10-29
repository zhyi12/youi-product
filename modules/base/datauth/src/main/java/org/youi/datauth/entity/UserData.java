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
 * 实体: 用户数据
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_user_data")
@JsonIgnoreProperties("dataIds")
public class UserData implements Domain{
	
	private static final long serialVersionUID = -8273075717195370620L;
	

	@Column
	private List<String> dataIds;//数据ID集合
	@Id
	@Column
	private String id;//主键

	public List<String> getDataIds(){
		return this.dataIds;
	}
	
	public void setDataIds(List<String> dataIds){
		this.dataIds = dataIds;
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
		result = prime * result + ((dataIds == null) ? 0 : dataIds.hashCode());
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
		final UserData other = (UserData) obj;
		if (dataIds == null) {
			if (other.dataIds != null)
				return false;
		} else if (!dataIds.equals(other.dataIds))
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