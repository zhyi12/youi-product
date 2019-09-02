package org.youi.metadata.object.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.metadata.object.entity.MetaPlan;

import static org.junit.Assert.*;

/**
 * Created by zhouyi on 2019/9/1.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {MongoTestConfig.class})
public class MetaPlanDaoTest {

    @Autowired
    private MetaPlanDao metaPlanDao;

    @Test
    public void testSaveMetaPlan(){
        MetaPlan metaPlan = new MetaPlan();
        metaPlan.setText("通信行业统计调查制度");
        metaPlanDao.save(metaPlan);
    }

}