/*
 * YOUI框架
 * Copyright 2012 the original author or authors.
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
package org.youi.quartz.entity;

import javax.persistence.Id;

import org.youi.framework.core.dataobj.Domain;


/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午03:54:52</p>
 */
public class SchedulerJobKey implements Domain{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3062507591231607641L;

	@Id
	@javax.persistence.Column(name = "SCHED_NAME",insertable=false,updatable=false)
	@org.hibernate.validator.constraints.Length(max=120)
	private String schedName;//
	
	@Id
	@javax.persistence.Column(name = "TRIGGER_NAME",insertable=false,updatable=false)
	@org.hibernate.validator.constraints.Length(max=200)
	private String triggerName;//触发器
	
	@Id
	@javax.persistence.Column(name = "TRIGGER_GROUP",insertable=false,updatable=false)
	@org.hibernate.validator.constraints.Length(max=200)
	private String triggerGroup;//触发组

	public String getSchedName() {
		return schedName;
	}

	public SchedulerJobKey setSchedName(String schedName) {
		this.schedName = schedName;
		return this;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public SchedulerJobKey setTriggerName(String triggerName) {
		this.triggerName = triggerName;
		return this;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public SchedulerJobKey setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
		return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((schedName == null) ? 0 : schedName.hashCode());
		result = prime * result
				+ ((triggerGroup == null) ? 0 : triggerGroup.hashCode());
		result = prime * result
				+ ((triggerName == null) ? 0 : triggerName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SchedulerJobKey other = (SchedulerJobKey) obj;
		if (schedName == null) {
			if (other.schedName != null)
				return false;
		} else if (!schedName.equals(other.schedName))
			return false;
		if (triggerGroup == null) {
			if (other.triggerGroup != null)
				return false;
		} else if (!triggerGroup.equals(other.triggerGroup))
			return false;
		if (triggerName == null) {
			if (other.triggerName != null)
				return false;
		} else if (!triggerName.equals(other.triggerName))
			return false;
		return true;
	}
	
	
}
