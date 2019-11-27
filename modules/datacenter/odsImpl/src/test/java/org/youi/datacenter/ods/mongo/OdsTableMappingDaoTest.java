package org.youi.datacenter.ods.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.datacenter.ods.entity.OdsTableMapping;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {MongoTestConfig.class})
public class OdsTableMappingDaoTest {

    @Autowired
    private OdsTableMappingDao odsTableMappingDao;

    @Test
    public void testAddOdsTableMapping(){
        OdsTableMapping odsTableMapping= new OdsTableMapping();

        odsTableMapping
                .setCatalog("mysql")
                .setSchema("filing").setTableName("youi_user").
                addColumnMapping("USER_ID","USER_ID").
                addColumnMapping("AGENCY_ID","AGENCY_ID").
                addColumnMapping("LOGIN_NAME","LOGIN_NAME").
                addColumnMapping("USER_CAPTION","USER_CAPTION").
                addColumnMapping("PASSWORD","PASSWORD").
                addColumnMapping("USER_ACTIVE","USER_ACTIVE").
                addColumnMapping("INIT_PASSWORD","INIT_PASSWORD").
                addColumnMapping("USER_GROUP","USER_GROUP").
                addColumnMapping("UPDATE_TIME","UPDATE_TIME");

        odsTableMappingDao.save(odsTableMapping);
    }
}