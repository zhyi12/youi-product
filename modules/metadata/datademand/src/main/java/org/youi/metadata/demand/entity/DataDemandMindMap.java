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

import javax.persistence.Column;
/**
 * 实体: 思维导图
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_metadata_demand_mind_map")
public class DataDemandMindMap implements Domain{
	
	private static final long serialVersionUID = 2663863369805240421L;
	
	@Id
	@Column
	private String dataDemandId;//主键

	private String content;//思维导图xml内容

	public String getDataDemandId(){
		return this.dataDemandId;
	}
	
	public void setDataDemandId(String dataDemandId){
		this.dataDemandId = dataDemandId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataDemandId == null) ? 0 : dataDemandId.hashCode());
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
		final DataDemandMindMap other = (DataDemandMindMap) obj;
		if (dataDemandId == null) {
			if (other.dataDemandId != null)
				return false;
		} else if (!dataDemandId.equals(other.dataDemandId))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}