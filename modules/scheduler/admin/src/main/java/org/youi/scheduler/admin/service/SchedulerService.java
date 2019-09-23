package org.youi.scheduler.admin.service;

import org.youi.framework.core.exception.BusException;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.ConditionCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.OrderCollection;
import org.youi.scheduler.admin.entity.SchedulerJob;

import java.util.Collection;

/**
 * Created by zhouyi on 2019/9/22.
 */
public interface SchedulerService {

    /**
     * 保存调度任务
     * @param schedulerJob
     */
    @EsbServiceMapping
    void saveSchedulerJob(SchedulerJob schedulerJob);

    /**
     * 分页查询任务
     * @param pager
     * @param conditions
     * @param orders
     * @return
     * @throws BusException
     */
    @EsbServiceMapping
    PagerRecords getPagerSchedulerJobs(
            Pager pager,//分页条件
            @ConditionCollection(domainClazz=SchedulerJob.class) Collection<Condition> conditions,//查询条件
            @OrderCollection Collection<Order> orders) throws BusException;

}
