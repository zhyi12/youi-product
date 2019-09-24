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
package org.youi.server.config.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.framework.core.dataobj.Domain;

import javax.persistence.Column;
/**
 * 实体: 服务参数
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_server_property")
public class ServerProperty implements Domain{


	private static final long serialVersionUID = 6823157042864399427L;

	@Column
	private String propName;//参数名

	@Column
	private String propValue;//参数值
	@Id
	@Column
	private String id;//主键

	@Column
	private String profile;//配置类别

	@Column
	private String serverId;//服务ID

	public String getPropName(){
		return this.propName;
	}
	
	public void setPropName(String propName){
		this.propName = propName;
	}
	public String getPropValue(){
		return this.propValue;
	}
	
	public void setPropValue(String propValue){
		this.propValue = propValue;
	}
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public String getProfile(){
		return this.profile;
	}
	
	public void setProfile(String profile){
		this.profile = profile;
	}
	public String getServerId(){
		return this.serverId;
	}
	
	public void setServerId(String serverId){
		this.serverId = serverId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((propName == null) ? 0 : propName.hashCode());
		result = prime * result + ((propValue == null) ? 0 : propValue.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		result = prime * result + ((serverId == null) ? 0 : serverId.hashCode());
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
		final ServerProperty other = (ServerProperty) obj;
		if (propName == null) {
			if (other.propName != null)
				return false;
		} else if (!propName.equals(other.propName))
			return false;
		if (propValue == null) {
			if (other.propValue != null)
				return false;
		} else if (!propValue.equals(other.propValue))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (profile == null) {
			if (other.profile != null)
				return false;
		} else if (!profile.equals(other.profile))
			return false;
		if (serverId == null) {
			if (other.serverId != null)
				return false;
		} else if (!serverId.equals(other.serverId))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}