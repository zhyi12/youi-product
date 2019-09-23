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
import org.youi.scheduler.common.model.ISchedulerJob;


/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述: 调度任务</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午02:34:07</p>
 */

@javax.persistence.Entity
@javax.persistence.Table(name = "youi_qrtz_triggers")
@IdClass(value = SchedulerJobKey.class)
public class SchedulerJob implements Domain,ISchedulerJob {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9106047587928136468L;

	@Id
	private String schedName;//
	
	@Id
	private String triggerName;//触发器
	
	@Id
	private String triggerGroup;//触发组
	
	@javax.persistence.ManyToOne
    @javax.persistence.JoinColumns(value = {
    		@javax.persistence.JoinColumn(name="SCHED_NAME"),
    		@javax.persistence.JoinColumn(name="JOB_NAME"),
    		@javax.persistence.JoinColumn(name="JOB_GROUP")})
	private JobDetails jobDetails;
	
	@javax.persistence.ManyToOne
	@javax.persistence.JoinColumns(value = {
    		@javax.persistence.JoinColumn(name="SCHED_NAME",referencedColumnName="SCHED_NAME",insertable=false,updatable=false),
    		@javax.persistence.JoinColumn(name="TRIGGER_NAME",referencedColumnName="TRIGGER_NAME",insertable=false,updatable=false),
    		@javax.persistence.JoinColumn(name="TRIGGER_GROUP",referencedColumnName="TRIGGER_GROUP",insertable=false,updatable=false)})
	private CronTrigger cronTrigger;
	
	
	@javax.persistence.Column(name = "DESCRIPTION")
	@org.hibernate.validator.constraints.Length(max=200)
	private String description;//调度描述
	
	@javax.persistence.Column(name = "trigger_State")
	@org.hibernate.validator.constraints.Length(max=200)
	private String triggerState;//调度状态
	
	@javax.persistence.Column(name = "START_TIME")
	@org.hibernate.validator.constraints.Length(max=13)
	private String startTime;//开始时间
	
	@javax.persistence.Column(name = "END_TIME")
	@org.hibernate.validator.constraints.Length(max=13)
	private String endTime;//结束时间
	
	//
	@javax.persistence.Column(name = "PREV_FIRE_TIME")
	@org.hibernate.validator.constraints.Length(max=13)
	private String prevFireTime;//上次运行时间
	
	@javax.persistence.Column(name = "NEXT_FIRE_TIME")
	@org.hibernate.validator.constraints.Length(max=13)
	private String nextFireTime;//下次运行时间
	
	@javax.persistence.Column(name = "CALENDAR_NAME")
	@org.hibernate.validator.constraints.Length(max=200)
	private String calendarName;//日历名
	
	//PRIORITY
	@javax.persistence.Column(name = "PRIORITY")
	private Integer priority;//优先级
	
	@javax.persistence.Column(name = "MISFIRE_INSTR")
	private Integer misfireInsert;

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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the triggerState
	 */
	public String getTriggerState() {
		return triggerState;
	}

	/**
	 * @param triggerState the triggerState to set
	 */
	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the prevFireTime
	 */
	public String getPrevFireTime() {
		return prevFireTime;
	}

	/**
	 * @param prevFireTime the prevFireTime to set
	 */
	public void setPrevFireTime(String prevFireTime) {
		this.prevFireTime = prevFireTime;
	}

	/**
	 * @return the nextFireTime
	 */
	public String getNextFireTime() {
		return nextFireTime;
	}

	/**
	 * @param nextFireTime the nextFireTime to set
	 */
	public void setNextFireTime(String nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	/**
	 * @return the calendarName
	 */
	public String getCalendarName() {
		return calendarName;
	}

	/**
	 * @param calendarName the calendarName to set
	 */
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	/**
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * @return the misfireInsert
	 */
	public Integer getMisfireInsert() {
		return misfireInsert;
	}

	/**
	 * @param misfireInsert the misfireInsert to set
	 */
	public void setMisfireInsert(Integer misfireInsert) {
		this.misfireInsert = misfireInsert;
	}

	/**
	 * @return the jobDetails
	 */
	public JobDetails getJobDetails() {
		return jobDetails;
	}

	/**
	 * @param jobDetails the jobDetails to set
	 */
	public void setJobDetails(JobDetails jobDetails) {
		this.jobDetails = jobDetails;
	}
	
	/**
	 * @return the cornTrigger
	 */
	public CronTrigger getCronTrigger() {
		return cronTrigger;
	}

	/**
	 * @param cronTrigger
	 */
	public void setCronTrigger(CronTrigger cronTrigger) {
		this.cronTrigger = cronTrigger;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((calendarName == null) ? 0 : calendarName.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + misfireInsert;
		result = prime * result
				+ ((nextFireTime == null) ? 0 : nextFireTime.hashCode());
		result = prime * result
				+ ((prevFireTime == null) ? 0 : prevFireTime.hashCode());
		result = prime * result + priority;
		result = prime * result
				+ ((schedName == null) ? 0 : schedName.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result
				+ ((triggerState == null) ? 0 : triggerState.hashCode());
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
		SchedulerJob other = (SchedulerJob) obj;
		if (calendarName == null) {
			if (other.calendarName != null)
				return false;
		} else if (!calendarName.equals(other.calendarName))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (misfireInsert != other.misfireInsert)
			return false;
		if (nextFireTime == null) {
			if (other.nextFireTime != null)
				return false;
		} else if (!nextFireTime.equals(other.nextFireTime))
			return false;
		if (prevFireTime == null) {
			if (other.prevFireTime != null)
				return false;
		} else if (!prevFireTime.equals(other.prevFireTime))
			return false;
		if (priority != other.priority)
			return false;
		if (schedName == null) {
			if (other.schedName != null)
				return false;
		} else if (!schedName.equals(other.schedName))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		if (triggerState == null) {
			if (other.triggerState != null)
				return false;
		} else if (!triggerState.equals(other.triggerState))
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
