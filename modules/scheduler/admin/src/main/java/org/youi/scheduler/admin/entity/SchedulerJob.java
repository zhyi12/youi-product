/*
 * Copyright 2012-2018 the original author or authors.
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
package org.youi.scheduler.admin.entity;

import org.youi.framework.core.dataobj.Domain;
import org.youi.scheduler.common.model.ISchedulerJob;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class SchedulerJob implements ISchedulerJob,Domain{

    private static final long serialVersionUID = 2554256399943562005L;

    private String serverName;//服务名

    private String schedName;//

    private String triggerName;//触发器

    private String triggerGroup;//触发组

    private JobDetails jobDetails;

    private CronTrigger cronTrigger;

    private String triggerState;//调度状态

    private String startTime;//开始时间

    private String endTime;//结束时间

    private String prevFireTime;//上次运行时间

    private String nextFireTime;//下次运行时间

    private String calendarName;//日历名

    private String priority;//优先级

    private String misfireInsert;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public String getSchedName() {
        return schedName;
    }

    public void setSchedName(String schedName) {
        this.schedName = schedName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public JobDetails getJobDetails() {
        return jobDetails;
    }

    public void setJobDetails(JobDetails jobDetails) {
        this.jobDetails = jobDetails;
    }

    @Override
    public CronTrigger getCronTrigger() {
        return cronTrigger;
    }

    public void setCronTrigger(CronTrigger cronTrigger) {
        this.cronTrigger = cronTrigger;
    }

    public String getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPrevFireTime() {
        return prevFireTime;
    }

    public void setPrevFireTime(String prevFireTime) {
        this.prevFireTime = prevFireTime;
    }

    public String getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(String nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getMisfireInsert() {
        return misfireInsert;
    }

    public void setMisfireInsert(String misfireInsert) {
        this.misfireInsert = misfireInsert;
    }
}
