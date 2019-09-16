package org.youi.agency.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.agency.entity.Agency;
import org.youi.framework.mongo.ModuleConfig;

import static org.junit.Assert.*;

/**
 * Created by zhouyi on 2019/9/16.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {
        MongoTestConfig.class,
        ModuleConfig.class})
public class AgencyDaoTest {

    @Autowired
    private AgencyDao agencyDao;

    @Test
    public void testSaveAgency(){
        Agency agency = new Agency();

        agency.setId("000000");
        agency.setAreaId("000000");
        agency.setCaption("全国");
        agency.setCode("000000");

        agencyDao.save(agency);
    }
}