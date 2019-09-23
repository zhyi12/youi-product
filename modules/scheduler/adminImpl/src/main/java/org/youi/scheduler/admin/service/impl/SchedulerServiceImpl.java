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
package org.youi.scheduler.admin.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youi.framework.core.exception.BusException;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.ConditionCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.OrderCollection;
import org.youi.scheduler.admin.entity.SchedulerJob;
import org.youi.scheduler.admin.service.SchedulerService;
import org.youi.scheduler.common.ISchedulerManager;
import org.youi.scheduler.common.model.ISchedulerJob;

import java.util.Collection;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService,InitializingBean {

    @Autowired(required = false)
    private ISchedulerManager<? extends ISchedulerJob> schedulerManager;

    /**
     * 保存任务
     */
    @EsbServiceMapping
    public void saveSchedulerJob(SchedulerJob schedulerJob){
        schedulerManager.saveSchedulerJob(schedulerJob);
    }

    @EsbServiceMapping
    public PagerRecords getPagerSchedulerJobs(
            Pager pager,//分页条件
            @ConditionCollection(domainClazz=SchedulerJob.class) Collection<Condition> conditions,//查询条件
            @OrderCollection Collection<Order> orders) throws BusException {
        return schedulerManager.getPagerSchedulerJobs(pager, conditions, orders);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(schedulerManager==null){
            schedulerManager = new ISchedulerManager(){
                @Override
                public void saveSchedulerJob(ISchedulerJob schedulerJob) {

                }

                @Override
                public PagerRecords getPagerSchedulerJobs(Pager pager, Collection collection, Collection collection2) throws BusException {
                    return null;
                }
            };
        }
    }
}
