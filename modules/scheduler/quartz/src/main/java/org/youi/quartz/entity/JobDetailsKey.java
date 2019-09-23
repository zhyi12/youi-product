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
 * <p>@创建时间： 下午04:01:23</p>
 */
public class JobDetailsKey implements Domain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4290465625087361218L;
	
	@Id
	@javax.persistence.Column(name = "SCHED_NAME")
	@org.hibernate.validator.constraints.Length(max=120)
	private String schedName;
	
	@Id
	@javax.persistence.Column(name = "JOB_NAME")
	@org.hibernate.validator.constraints.Length(max=200)
	private String jobName;
	
	@Id
	@javax.persistence.Column(name = "JOB_GROUP")
	@org.hibernate.validator.constraints.Length(max=200)
	private String jobGroup;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((jobGroup == null) ? 0 : jobGroup.hashCode());
		result = prime * result + ((jobName == null) ? 0 : jobName.hashCode());
		result = prime * result
				+ ((schedName == null) ? 0 : schedName.hashCode());
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
		JobDetailsKey other = (JobDetailsKey) obj;
		if (jobGroup == null) {
			if (other.jobGroup != null)
				return false;
		} else if (!jobGroup.equals(other.jobGroup))
			return false;
		if (jobName == null) {
			if (other.jobName != null)
				return false;
		} else if (!jobName.equals(other.jobName))
			return false;
		if (schedName == null) {
			if (other.schedName != null)
				return false;
		} else if (!schedName.equals(other.schedName))
			return false;
		return true;
	}
	
	

}
