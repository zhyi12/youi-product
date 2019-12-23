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
package org.youi.metadata.metadimension.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.youi.framework.core.dataobj.Domain;

import javax.persistence.Column;
/**
 * 实体: 分类属性
 * @author 代码生成器
 * @since 1.0.0
 */
@Document(collection = "youi_metadata_category")
public class MetaCategory implements Domain{
	
	private static final long serialVersionUID = -6351746641877150831L;
	
	@Id
	@Column
	private String id;//主键

	@Column
	private String text;//属性名称

	@Column
	private String expression;//属性表达式

	public String getId(){
		return this.id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	public String getText(){
		return this.text;
	}
	
	public void setText(String text){
		this.text = text;
	}
	public String getExpression(){
		return this.expression;
	}
	
	public void setExpression(String expression){
		this.expression = expression;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((expression == null) ? 0 : expression.hashCode());
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
		final MetaCategory other = (MetaCategory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		return true;
	}
	
	public String toString(){
		return super.toString();
	}
}