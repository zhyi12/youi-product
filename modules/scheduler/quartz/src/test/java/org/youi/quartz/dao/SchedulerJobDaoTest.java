package org.youi.quartz.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.framework.context.ModulesRunnerBuilder;
import org.youi.quartz.ModuleConfig;
import org.youi.quartz.entity.SchedulerJob;
import org.youi.quartz.entity.SchedulerJobKey;

import static org.junit.Assert.*;

/**
 * Created by zhouyi on 2019/9/22.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {
        DaoTestConfig.class,
        org.youi.framework.jpa.ModuleConfig.class})
public class SchedulerJobDaoTest {

    @Autowired
    private SchedulerJobDao schedulerJobDao;

    @BeforeClass
    public static void setup(){
        new ModulesRunnerBuilder(ModuleConfig.class);
    }

    @Test
    public void testGetSchedulerJob(){
        SchedulerJobKey schedulerJobKey = new SchedulerJobKey();
        schedulerJobKey.setSchedName("schedulerFactoryBean");
        schedulerJobKey.setTriggerGroup("DEFAULT");
        schedulerJobKey.setTriggerName("2deaa809-a680-4190-ab05-10a9a53d616f");

        SchedulerJob schedulerJob = schedulerJobDao.get(schedulerJobKey);

        System.out.println(schedulerJob);
    }
}