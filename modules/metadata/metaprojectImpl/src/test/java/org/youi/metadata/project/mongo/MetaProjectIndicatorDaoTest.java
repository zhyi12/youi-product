package org.youi.metadata.project.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.framework.mongo.MaxValue;
import org.youi.framework.mongo.ModuleConfig;
import org.youi.metadata.project.entity.MetaProjectIndicator;

import static org.junit.Assert.*;

/**
 * Created by zhouyi on 2019/11/10.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {
        MongoTestConfig.class,
        ModuleConfig.class})
public class MetaProjectIndicatorDaoTest {


    @Autowired
    private MetaProjectIndicatorDao metaProjectIndicatorDao;

    @Test
    public void testProjectData(){

        metaProjectIndicatorDao.update("metaProjectId","proj001");


    }
}