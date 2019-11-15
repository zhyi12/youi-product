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
package org.youi.measureunit.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.framework.core.dataobj.Domain;

import javax.persistence.Column;
/**
 * 实体: 计量单位
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_measure_unit")
public class MeasureUnit implements Domain{
	
	private static final long serialVersionUID = 5014017979201031497L;

	@Id
	@Column
	private String id;//主键

	@Column
	private String measurement;//度量衡

	@Column
	private String unitCaption;//名称

	@Column
	private Double rate;//换算率

	@Column
	private String baseUnitId;//元单位主键

	@Column
	private String description;//说明

	public String getMeasurement(){
		return this.measurement;
	}
	
	public void setMeasurement(String measurement){
		this.measurement = measurement;
	}
	public String getUnitCaption(){
		return this.unitCaption;
	}
	
	public void setUnitCaption(String unitCaption){
		this.unitCaption = unitCaption;
	}
	public Double getRate(){
		return this.rate;
	}
	
	public void setRate(Double rate){
		this.rate = rate;
	}
	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public String getBaseUnitId(){
		return this.baseUnitId;
	}
	
	public void setBaseUnitId(String baseUnitId){
		this.baseUnitId = baseUnitId;
	}
	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((measurement == null) ? 0 : measurement.hashCode());
		result = prime * result + ((unitCaption == null) ? 0 : unitCaption.hashCode());
		result = prime * result + ((rate == null) ? 0 : rate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((baseUnitId == null) ? 0 : baseUnitId.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
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
		final MeasureUnit other = (MeasureUnit) obj;
		if (measurement == null) {
			if (other.measurement != null)
				return false;
		} else if (!measurement.equals(other.measurement))
			return false;
		if (unitCaption == null) {
			if (other.unitCaption != null)
				return false;
		} else if (!unitCaption.equals(other.unitCaption))
			return false;
		if (rate == null) {
			if (other.rate != null)
				return false;
		} else if (!rate.equals(other.rate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (baseUnitId == null) {
			if (other.baseUnitId != null)
				return false;
		} else if (!baseUnitId.equals(other.baseUnitId))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}