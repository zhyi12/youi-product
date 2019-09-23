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

    PagerRecords getPagerSchedulerJobs(Pager pager,//分页条件
                                              Collection<Condition> conditions,//查询条件
                                              Collection<Order> orders) throws BusException;

}
