package org.youi.dataquery.presto.dao;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.dataquery.engine.model.CubeColumns;
import org.youi.dataquery.engine.model.CubeRowData;
import org.youi.dataquery.engine.model.QueryOrder;
import org.youi.dataquery.presto.ModuleConfig;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;

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
    public void testQueryCatalogs(){
        List<String> catalogs =  prestoQueryDao.queryCatalogs("");
        System.out.println(catalogs);
    }

    @Test
    public void testQuerySchemas(){
        List<String> schemas =  prestoQueryDao.querySchemas("mongodb");
        System.out.println(schemas);
    }

    @Test
    public void testQueryTables(){
        List<String> tables =  prestoQueryDao.queryTables("mongodb","stats2-filing");
        System.out.println(tables);
    }
    @Test
    public void testQueryTableColumns(){
        List<Item> columns =  prestoQueryDao.queryTableColumns("mongodb","stats2-filing","standard_classification");
        System.out.println(columns);
    }


    @Test
    public void testGetQueryColumnItems(){
        List<Item> columns =  prestoQueryDao.getQueryColumnItems("select _id,agencyAreaId,agencyCaption from \"mongodb\".\"stats2-filing\".\"stats_agency\"",new String[]{});
        System.out.println(columns);
    }

    @Test
    public void testQueryRowDataByPager(){
        Pager pager = new Pager(10,1,Pager.QUERY_TYPE_ALL);
        //排序条件，分页查询时需要排序条件，保证分页后数据的正确性
        List<QueryOrder> queryOrders = new ArrayList<>();
        QueryOrder queryOrder = new QueryOrder();
        queryOrder.setProperty("catalogItemId");
        queryOrders.add(queryOrder);

        String querySql = "select sum(S203_2018020170000_D1) S203_2018020170000_D1,DATA_101_COL007,tc.catalogItemId from stats_working_task_row_st001 t left join stats_working_task_row_st001_catalog tc on t._id=tc.rowId group by DATA_101_COL007,tc.catalogItemId";
        PagerRecords pagerRecords = prestoQueryDao.queryRowDataByPager(pager,queryOrders,querySql,new String[]{});

        Assert.assertTrue(pagerRecords.getTotalCount()>0);
    }

    @Test
    public void testQueryCubeRowDatas(){
        String cubeQuerySql = "SELECT t_.DATA_101_COL044,t_.DATA_101_COL007,sum(t_.S203_20180_A0) as S203_20180_A0 FROM stats_working_task_row_st001 as t_ WHERE t_.DATA_101_COL044  IN  (?,?) AND t_.DATA_101_COL007  IN  (?,?,?,?) GROUP by cube(t_.DATA_101_COL044,t_.DATA_101_COL007)";
        String[] params = {"1","2","01","02","03","07"};//预期的生成的参数
        CubeColumns cubeColumns = new CubeColumns();
        cubeColumns.addGroupColumn("DATA_101_COL044")
                .addGroupColumn("DATA_101_COL007").addMeasureColumn("S203_20180_A0");
        //
        List<CubeRowData> cubeRowDatas = prestoQueryDao.queryCubeRowDatas(cubeColumns,cubeQuerySql,params);

        Assert.assertTrue(cubeRowDatas.size()>0);
    }
}