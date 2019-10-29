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
package org.youi.spuser.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.Record;
import org.youi.framework.security.IUser;
import org.youi.framework.util.StringUtils;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

/**
 * 实体: 开发人员
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_ssp_user")
@JsonIgnoreProperties("password")
public class SpUser implements Domain,IUser {
	
	private static final long serialVersionUID = 8577218623733809772L;
	

	@Column
	private String agencyId;//机构ID

	@Column
	private String userCaption;//用户名称

	@Column
	private String username;//登录名

	@Column
	private String password;//密码
	@Id
	@Column
	private String id;//主键

	private String initPassword;//初始化密码

	@Column
	private String phone;//手机号

	public String getInitPassword() {
		return initPassword;
	}

	public void setInitPassword(String initPassword) {
		this.initPassword = initPassword;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Transient
	private List<String> roleIds;

	@Transient
	private Record principalConfig;

	public String getAgencyId(){
		return this.agencyId;
	}
	
	public void setAgencyId(String agencyId){
		this.agencyId = agencyId;
	}
	public String getUserCaption(){
		return this.userCaption;
	}
	
	public void setUserCaption(String userCaption){
		this.userCaption = userCaption;
	}

	@Override
	public List<String> roleIds() {
		return roleIds;
	}

	public String getUsername(){
		return this.username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	public SpUser addRole(String roleId){
		if(roleIds==null){
			roleIds = new ArrayList<>();
		}
		if(StringUtils.isNotEmpty(roleId) && !roleIds.contains(roleId)){
			roleIds.add(roleId);
		}
		return this;
	}

	@Override
	public Record getPrincipalConfig() {
		return principalConfig;
	}

	public void setPrincipalConfig(Record principalConfig) {
		this.principalConfig = principalConfig;
	}

	public SpUser addConfig(String name,String value){
		if(principalConfig==null){
			principalConfig = new Record();
		}

		if(StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(value)){
			principalConfig.put(name,value);
		}
		return this;
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
		result = prime * result + ((agencyId == null) ? 0 : agencyId.hashCode());
		result = prime * result + ((userCaption == null) ? 0 : userCaption.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		final SpUser other = (SpUser) obj;
		if (agencyId == null) {
			if (other.agencyId != null)
				return false;
		} else if (!agencyId.equals(other.agencyId))
			return false;
		if (userCaption == null) {
			if (other.userCaption != null)
				return false;
		} else if (!userCaption.equals(other.userCaption))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
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