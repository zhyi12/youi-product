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
package org.youi.quartz.service.impl;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.youi.framework.core.exception.BusException;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.util.BeanUtils;
import org.youi.framework.util.PropertyUtils;
import org.youi.framework.util.StringUtils;
import org.youi.quartz.Constants;
import org.youi.quartz.dao.SchedulerJobDao;
import org.youi.quartz.entity.CronTrigger;
import org.youi.quartz.entity.JobDetails;
import org.youi.quartz.entity.SchedulerJob;
import org.youi.quartz.entity.SchedulerJobKey;
import org.youi.scheduler.common.ISchedulerManager;
import org.youi.scheduler.common.model.ISchedulerJob;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Collection;
import java.util.UUID;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class QuartzSchedulerManager implements ISchedulerManager<SchedulerJob> {

    private final static Logger logger = LoggerFactory.getLogger(QuartzSchedulerManager.class);

    @Resource(name = "schedulerFactoryBean")
    private Scheduler scheduler;//

    @Autowired(required = false)
    private SchedulerJobDao schedulerJobDao;

    private volatile Object lock = new Object();

    @Override
    public void saveSchedulerJob(ISchedulerJob schedulerJob) {
        SchedulerJob quartzSchedulerJob = new SchedulerJob();
        JobDetails jobDetails = new JobDetails();
        CronTrigger cronTrigger = new CronTrigger();

        BeanUtils.copyProperties(schedulerJob,quartzSchedulerJob);
        BeanUtils.copyProperties(schedulerJob.getJobDetails(),jobDetails);
        BeanUtils.copyProperties(schedulerJob.getCronTrigger(),cronTrigger);

        quartzSchedulerJob.setCronTrigger(cronTrigger);
        quartzSchedulerJob.setJobDetails(jobDetails);

        Object serverName = cronTrigger.getTriggerGroup();//使用trigger group作为serverName

        if(serverName==null){
            serverName = "";
        }
        this.addSchedulerJob(quartzSchedulerJob, serverName.toString());
    }

    /**
     * 分页查询调度任务
     * @param pager
     * @param conditions
     * @param orders
     * @return
     * @throws BusException
     */
    public PagerRecords getPagerSchedulerJobs(Pager pager,//分页条件
                                              Collection<Condition> conditions,//查询条件
                                              Collection<Order> orders) throws BusException{
        return schedulerJobDao.findByPager(pager, conditions, orders);
    }

    @Override
    public ISchedulerJob get(String schedName, String triggerGroup, String triggerName) {
        SchedulerJobKey schedulerJobKey =
                new SchedulerJobKey().setSchedName(schedName)
                .setTriggerGroup(triggerGroup)
                .setTriggerName(triggerName);
        return getSchedulerJob(schedulerJobKey);
    }

    /**
     * 主键查询调度任务
     * @param schedulerJobKey
     * @return
     */
    public SchedulerJob getSchedulerJob(SchedulerJobKey schedulerJobKey) {
        return schedulerJobDao.get(schedulerJobKey);
    }

    /**
     * 暂停触发任务
     * @param triggerGroup
     * @param triggerName
     */
    public void pause(String triggerGroup, String triggerName){
        try {
            scheduler.pauseTrigger(new TriggerKey(triggerName, triggerGroup));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 恢复触发任务
     * @param triggerGroup
     * @param triggerName
     */
    public void resume( String triggerGroup, String triggerName){
        try {
            scheduler.resumeTrigger(new TriggerKey(triggerName, triggerGroup));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除任务
     * @param schedName
     * @param triggerGroup
     * @param triggerName
     */
    public void remove(String schedName, String triggerGroup, String triggerName){
        schedulerJobDao.remove(new SchedulerJobKey().setSchedName(schedName)
                .setTriggerGroup(triggerGroup)
                .setTriggerName(triggerName));
    }

    /**
     * 增加调度任务
     */
    private void addSchedulerJob(SchedulerJob schedulerJob,String serverName){
        synchronized (lock){
            try {
                JobDetail jobDetail = buildJobDetail(schedulerJob,serverName);
                CronTrigger sCronTrigger = schedulerJob.getCronTrigger();

                if(sCronTrigger.getTriggerName()==null){
                    sCronTrigger.setTriggerName(UUID.randomUUID().toString());
                    sCronTrigger.setTriggerGroup(serverName);//使用trigger group存储微服务名称（serverName）
                }

                TriggerKey triggerKey =
                        new TriggerKey(sCronTrigger.getTriggerName(),sCronTrigger.getTriggerGroup());

                Trigger cronTrigger = scheduler.getTrigger(triggerKey);

                CronTriggerImpl cronTriggerImpl = buildCronTrigger(schedulerJob,jobDetail,cronTrigger,sCronTrigger);

                scheduler.addJob(jobDetail, true);
                if(cronTrigger==null){
                    scheduler.scheduleJob(cronTriggerImpl);
                }

                scheduler.rescheduleJob(triggerKey, cronTriggerImpl);
            } catch (SchedulerException e) {
                logger.error(e.getMessage());
                throw new BusException(e.getMessage(),e);
            }
        }
    }

    /**
     *
     * @param schedulerJob
     * @param serverName
     * @return
     */
    private JobDetail buildJobDetail(SchedulerJob schedulerJob,String serverName){
        JobDetailImpl jobDetail = new JobDetailImpl();
        JobDataMap jobData = new JobDataMap();
        jobData.put("serverName",serverName);//设置服务名
        //设置参数
        jobDetail.setDescription(schedulerJob.getJobDetails().getDescription());
        jobDetail.setGroup(schedulerJob.getJobDetails().getJobGroup());
        jobDetail.setJobClass(QuartzServiceJob.class);
        jobDetail.setJobDataMap(jobData);
        if(StringUtils.isEmpty(schedulerJob.getJobDetails().getJobName())){
            jobDetail.setName(UUID.randomUUID().toString());
        }else{
            jobDetail.setName(schedulerJob.getJobDetails().getJobName());
        }
        jobDetail.setDurability(true);
        return jobDetail;
    }

    /**
     *
     * @param schedulerJob
     * @param trigger
     * @param sCronTrigger
     * @return
     */
    private CronTriggerImpl buildCronTrigger(SchedulerJob schedulerJob,JobDetail jobDetail,Trigger trigger,CronTrigger sCronTrigger){
        CronTriggerImpl cronTrigger;
        if(trigger==null&&trigger instanceof CronTriggerImpl){
            cronTrigger = (CronTriggerImpl)trigger;
        }else{
            cronTrigger = new CronTriggerImpl();
        }

        cronTrigger.setName(sCronTrigger.getTriggerName());
        cronTrigger.setGroup(sCronTrigger.getTriggerGroup());
        cronTrigger.setJobKey(jobDetail.getKey());
        cronTrigger.setDescription(schedulerJob.getDescription());

        try {
            cronTrigger.setCronExpression(sCronTrigger.getCronExpression());
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }

        return cronTrigger;
    }
}
