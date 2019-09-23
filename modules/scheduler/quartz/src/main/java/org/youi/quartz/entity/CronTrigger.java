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
import javax.persistence.IdClass;

import org.youi.framework.core.dataobj.Domain;
import org.youi.scheduler.common.model.ICronTrigger;


/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午02:35:55</p>
 */

@javax.persistence.Entity
@javax.persistence.Table(name = "youi_qrtz_cron_triggers")
@IdClass(value = SchedulerJobKey.class)
public class CronTrigger implements Domain,ICronTrigger{

	/**
	 * 
	 */
	private static final long serialVersionUID = -645796109235676454L;

	@Id
	private String schedName;//
	
	@Id
	private String triggerName;//触发器
	
	@Id
	private String triggerGroup;//触发组
	
	@javax.persistence.Column(name = "CRON_EXPRESSION")
	@org.hibernate.validator.constraints.Length(max=200)
	private String cronExpression;//定时时间表达式
	
	@javax.persistence.Column(name = "TIME_ZONE_ID")
	@org.hibernate.validator.constraints.Length(max=200)
	private String timeZoneId;//时区

	/**
	 * @return the schedName
	 */
	public String getSchedName() {
		return schedName;
	}

	/**
	 * @param schedName the schedName to set
	 */
	public void setSchedName(String schedName) {
		this.schedName = schedName;
	}

	/**
	 * @return the triggerName
	 */
	public String getTriggerName() {
		return triggerName;
	}

	/**
	 * @param triggerName the triggerName to set
	 */
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	/**
	 * @return the triggerGroup
	 */
	public String getTriggerGroup() {
		return triggerGroup;
	}

	/**
	 * @param triggerGroup the triggerGroup to set
	 */
	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	/**
	 * @return the cronExpression
	 */
	public String getCronExpression() {
		return cronExpression;
	}

	/**
	 * @param cronExpression the cronExpression to set
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	/**
	 * @return the timeZoneId
	 */
	public String getTimeZoneId() {
		return timeZoneId;
	}

	/**
	 * @param timeZoneId the timeZoneId to set
	 */
	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}
	
	
	
}
