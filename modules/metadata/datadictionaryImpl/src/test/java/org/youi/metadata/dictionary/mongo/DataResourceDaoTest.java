package org.youi.metadata.dictionary.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.framework.mongo.ModuleConfig;
import org.youi.metadata.dictionary.entity.DataResource;

import static org.junit.Assert.*;

/**
 * Created by zhouyi on 2019/9/14.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {
        MongoTestConfig.class,
        ModuleConfig.class})
public class DataResourceDaoTest {

    @Autowired
    private DataResourceDao dataResourceDao;


    @Test
    public void findByCatalogAndSchema() throws Exception {

        DataResource dataResource = dataResourceDao.findByCatalogAndSchema("mongodb","stats2-filing");

        System.out.println(dataResource);
    }

}