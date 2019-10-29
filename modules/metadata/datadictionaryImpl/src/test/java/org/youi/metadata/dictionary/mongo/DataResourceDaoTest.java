package org.youi.metadata.dictionary.mongo;

import org.apache.commons.collections.ArrayStack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.mongo.ModuleConfig;
import org.youi.framework.services.utils.ConditionUtils;
import org.youi.metadata.dictionary.entity.DataResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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


    @Test
    public void complexFindByPager(){
        Pager pager = new Pager(1,1,Pager.QUERY_TYPE_ALL);
        Collection<Condition> conditions = new ArrayList<>();

        String[] catalogs = {"mysql","mongodb"};

        conditions.add(ConditionUtils.getCondition("catalog",Condition.IN,catalogs));

        PagerRecords pagerRecords = dataResourceDao.complexFindByPager(pager, conditions, null);

        System.out.println(pagerRecords.getRecords());
    }
}