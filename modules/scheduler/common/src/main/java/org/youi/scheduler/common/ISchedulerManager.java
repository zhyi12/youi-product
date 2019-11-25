package org.youi.scheduler.common;

import org.youi.framework.core.exception.BusException;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.scheduler.common.model.ISchedulerJob;

import java.util.Collection;

/**
 *
 * 任务调度管理接口类
 * Created by zhouyi on 2019/9/22.
 */
public interface ISchedulerManager<T extends ISchedulerJob> {

    /**
     *
     * @param schedulerJob
     */
    void saveSchedulerJob(ISchedulerJob schedulerJob);

    /**
     *
     * @param pager
     * @param conditions
     * @param orders
     * @return
     * @throws BusException
     */
    PagerRecords getPagerSchedulerJobs(Pager pager,//分页条件
                                              Collection<Condition> conditions,//查询条件
                                              Collection<Order> orders) throws BusException;

    /**
     *
     * 根据schedName、triggerGroup、triggerName获取定时触发的任务
     * @param schedName
     * @param triggerGroup
     * @param triggerName
     * @return
     */
    ISchedulerJob get(String schedName, String triggerGroup, String triggerName);


    /**
     * 暂停触发任务
     * @param triggerGroup
     * @param triggerName
     */
    void pause(String triggerGroup, String triggerName);

    /**
     * 恢复触发任务
     * @param triggerGroup
     * @param triggerName
     */
    void resume(String triggerGroup, String triggerName);

    /**
     * 删除任务
     * @param schedName
     * @param triggerGroup
     * @param triggerName
     */
    void remove(String schedName, String triggerGroup, String triggerName);
}
