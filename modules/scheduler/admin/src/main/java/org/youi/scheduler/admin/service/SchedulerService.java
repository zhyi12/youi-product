package org.youi.scheduler.admin.service;

import org.youi.framework.core.exception.BusException;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.ConditionCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.OrderCollection;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.scheduler.admin.entity.SchedulerJob;
import org.youi.scheduler.common.model.ISchedulerJob;

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

    /**
     *
     * 根据schedName、triggerGroup、triggerName获取定时任务
     * PS：任务限制一个任务（job）一个触发（trigger）
     * @param schedName
     * @param triggerGroup
     * @param triggerName
     * @return
     */
    @EsbServiceMapping
    public ISchedulerJob getSchedulerJob(
            @ServiceParam(name = "schedName") String schedName,
            @ServiceParam(name = "triggerGroup") String triggerGroup,
            @ServiceParam(name = "triggerName") String triggerName);


    /**
     * 暂停任务
     * @param triggerGroup
     * @param triggerName
     */
    @EsbServiceMapping
    void pause(@ServiceParam(name = "triggerGroup") String triggerGroup,
                      @ServiceParam(name = "triggerName") String triggerName);

    /**
     * 恢复任务
     * @param triggerGroup
     * @param triggerName
     */
    @EsbServiceMapping
    void resume(@ServiceParam(name = "triggerGroup") String triggerGroup,
                       @ServiceParam(name = "triggerName") String triggerName);

    /**
     * 删除任务
     * @param schedName
     * @param triggerGroup
     * @param triggerName
     */
    @EsbServiceMapping
    void removeSchedulerJob(
            @ServiceParam(name = "schedName") String schedName,
            @ServiceParam(name = "triggerGroup") String triggerGroup,
            @ServiceParam(name = "triggerName") String triggerName);
}
