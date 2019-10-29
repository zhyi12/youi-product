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
package org.youi.spcompany.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.framework.core.dataobj.Domain;

import javax.persistence.Column;
/**
 * 实体: 软件开发商
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_ssp_company")
public class SpCompany implements Domain{
	
	private static final long serialVersionUID = 2088282456743238513L;
	

	@Column
	private String agencyId;//机构ID

	@Column
	private String companyCaption;//开发商名称

	@Column
	private String shortCaption;//开发商简称
	@Id
	@Column
	private String id;//主键

	public String getAgencyId(){
		return this.agencyId;
	}
	
	public void setAgencyId(String agencyId){
		this.agencyId = agencyId;
	}
	public String getCompanyCaption(){
		return this.companyCaption;
	}
	
	public void setCompanyCaption(String companyCaption){
		this.companyCaption = companyCaption;
	}
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}

	public String getShortCaption() {
		return shortCaption;
	}

	public void setShortCaption(String shortCaption) {
		this.shortCaption = shortCaption;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agencyId == null) ? 0 : agencyId.hashCode());
		result = prime * result + ((companyCaption == null) ? 0 : companyCaption.hashCode());
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
		final SpCompany other = (SpCompany) obj;
		if (agencyId == null) {
			if (other.agencyId != null)
				return false;
		} else if (!agencyId.equals(other.agencyId))
			return false;
		if (companyCaption == null) {
			if (other.companyCaption != null)
				return false;
		} else if (!companyCaption.equals(other.companyCaption))
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