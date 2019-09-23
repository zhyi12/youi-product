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

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.youi.quartz.Constants;
import org.youi.scheduler.common.IRestServiceCaller;

/**
 *
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class QuartzServiceJob extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ApplicationContext applicationContext;

    private IRestServiceCaller restServiceCaller;//rest调用服务类

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //执行服务的远程调用
        this.initApplicationContext(jobExecutionContext);

        if(restServiceCaller==null){
            restServiceCaller = applicationContext.getBean(IRestServiceCaller.class);
        }

        if(restServiceCaller!=null && jobExecutionContext.getJobDetail()!=null){
            MultiValueMap params = buildJobServiceParam(jobExecutionContext);
            JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
            String serverName = jobDataMap.getString(Constants.JOB_PARAM_SERVER_NAME);//获取serverName数据
            String servicesName = jobExecutionContext.getJobDetail().getKey().getGroup();
            String serviceName = jobExecutionContext.getJobDetail().getKey().getName();
            restServiceCaller.callService(serverName,servicesName,serviceName,params);
            //保存任务调用日志
        }
    }

    /**
     *
     * @param jobExecutionContext
     * @return
     */
    private MultiValueMap buildJobServiceParam(JobExecutionContext jobExecutionContext) {
        MultiValueMap params = new LinkedMultiValueMap();
        params.add(Constants.JOB_PARAM_START_TIME,jobExecutionContext.getFireTime());
        params.add(Constants.JOB_PARAM_END_TIME,jobExecutionContext.getNextFireTime());
        params.add("jobInstanceId",jobExecutionContext.getFireInstanceId());
        return params;
    }

    /**
     *
     * @param jobExecutionContext
     */
    private void initApplicationContext(JobExecutionContext jobExecutionContext) {
        if(applicationContext==null){
            try {
                applicationContext = (ApplicationContext) jobExecutionContext.getScheduler().getContext().get("applicationContextKey");
            } catch (SchedulerException e) {
                logger.error(e.getMessage());
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
    }
}
