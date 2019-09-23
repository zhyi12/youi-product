package org.youi.quartz.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.youi.framework.core.orm.Dao;
import org.youi.quartz.entity.SchedulerJob;
import org.youi.quartz.entity.SchedulerJobKey;

/**
 * Created by zhouyi on 2019/9/22.
 */
public interface SchedulerJobDao extends PagingAndSortingRepository<SchedulerJob,SchedulerJobKey>,Dao<SchedulerJob,SchedulerJobKey> {

}
