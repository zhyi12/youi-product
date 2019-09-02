package org.youi.metadata.object.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.metadata.object.entity.MetaObjectConfig;

import static org.junit.Assert.*;

/**
 * Created by zhouyi on 2019/9/1.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {MongoTestConfig.class})
public class MetaObjectConfigDaoTest {

    @Autowired
    private MetaObjectConfigDao metaObjectConfigDao;

    @Test
    public void testSaveMetaObjectConfig(){

        MetaObjectConfig metaObjectConfig = new MetaObjectConfig();
        metaObjectConfig.setMetaObjectName("metaPlan");
        metaObjectConfig.setId("metaPlan");

        metaObjectConfig.addProperty("agencyId",new Item("agencyId","机构ID"));

        metaObjectConfigDao.save(metaObjectConfig);
    }
}