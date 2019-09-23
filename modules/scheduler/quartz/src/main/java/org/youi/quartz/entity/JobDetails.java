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
import org.youi.scheduler.common.model.IJobDetails;


/**
 * <p>@系统描述:YOUI</p>
 * <p>@功能描述:</p>
 * <p>@作者：  Administrator</p>
 * <p>@版本 ：1.0.0</p>
 * <p>@创建时间： 下午08:39:13</p>
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "youi_qrtz_job_details")
@IdClass(value = JobDetailsKey.class)
public class JobDetails implements IJobDetails,Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1400283956491736563L;
	
	@Id
	private String schedName;//调度
	
	@Id
	private String jobName;//任务名称
	
	@Id
	private String jobGroup;//任务组
	
	@javax.persistence.Column(name = "DESCRIPTION")
	@org.hibernate.validator.constraints.Length(max=250)
	private String description;
	
	@javax.persistence.Column(name = "JOB_CLASS_NAME")
	@org.hibernate.validator.constraints.Length(max=250)
	private String jobClassName;

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
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @return the jobGroup
	 */
	public String getJobGroup() {
		return jobGroup;
	}

	/**
	 * @param jobGroup the jobGroup to set
	 */
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
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
	 * @return the jobClassName
	 */
	public String getJobClassName() {
		return jobClassName;
	}

	/**
	 * @param jobClassName the jobClassName to set
	 */
	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}

}
