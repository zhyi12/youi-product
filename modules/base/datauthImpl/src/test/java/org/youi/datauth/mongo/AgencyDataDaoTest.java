package org.youi.datauth.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.datauth.entity.AgencyData;
import org.youi.framework.mongo.ModuleConfig;

import static org.junit.Assert.*;

/**
 * Created by zhouyi on 2019/10/17.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {
        MongoTestConfig.class,
        ModuleConfig.class})
public class AgencyDataDaoTest {

    @Autowired
    private AgencyDataDao agencyDataDao;

    @Test
    public void getDataByAuthDataTypeAndAgencyId() throws Exception {
        AgencyData agencyData = agencyDataDao.getDataByAuthDataTypeAndAgencyId("datasource","gwssi");
        System.out.println(agencyData);
    }

}