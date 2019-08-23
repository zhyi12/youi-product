package org.youi.dataquery.presto.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.dataquery.engine.entity.QueryOrder;
import org.youi.dataquery.presto.ModuleConfig;
import org.youi.framework.core.orm.Pager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouyi on 2019/8/21.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DaoTestConfig.class, ModuleConfig.class})
public class PrestoQueryDaoTest {

    @Autowired
    private PrestoQueryDao prestoQueryDao;

    @Test
    public void queryRowDataByPagerTest(){
        Pager pager = new Pager(10,1,Pager.QUERY_TYPE_ALL);

        List<QueryOrder> queryOrders = new ArrayList<>();
        QueryOrder queryOrder = new QueryOrder();
        queryOrder.setProperty("catalogItemId");
        queryOrders.add(queryOrder);

//        String querySql="select * from stats_agency";
        String querySql = "select sum(S203_2018020170000_D1) S203_2018020170000_D1,DATA_101_COL007,tc.catalogItemId from stats_working_task_row_st001 t left join stats_working_task_row_st001_catalog tc on t._id=tc.rowId group by DATA_101_COL007,tc.catalogItemId";
        System.out.println(prestoQueryDao.queryRowDataByPager(pager,queryOrders,querySql,new String[]{}).getRecords());
    }
}