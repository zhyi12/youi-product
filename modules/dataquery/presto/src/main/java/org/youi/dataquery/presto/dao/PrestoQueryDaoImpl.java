/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.dataquery.presto.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.youi.dataquery.engine.core.CubeRowDataMapper;
import org.youi.dataquery.engine.core.RowDataMapper;
import org.youi.dataquery.engine.entity.CubeColumns;
import org.youi.dataquery.engine.entity.CubeRowData;
import org.youi.dataquery.engine.entity.QueryOrder;
import org.youi.dataquery.engine.entity.RowData;
import org.youi.dataquery.presto.util.PrestoSqlUtils;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;

import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Repository
public class PrestoQueryDaoImpl implements PrestoQueryDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     *
     * @param querySql
     * @param params
     * @param queryOrders
     * @return
     */
    public List<RowData> queryRowDatas(String querySql,
                                       Object[] params,
                                      List<QueryOrder> queryOrders){
        return jdbcTemplate.query(querySql, params, new RowDataMapper(null));
    }

    /**
     *
     * @param cubeQuerySql 立方体查询语句
     * @param params sql参数
     * @return
     */
    @Override
    public List<CubeRowData> queryCubeRowDatas(CubeColumns cubeColumns,String cubeQuerySql, Object[] params) {
        return jdbcTemplate.query(cubeQuerySql, params, new CubeRowDataMapper(cubeColumns));
    }

    /**
     *
     * 分页查询presto数据
     * @param pager
     * @param querySql
     * @return
     */
    public PagerRecords queryRowDataByPager(@NonNull Pager pager,
                                            @NonNull List<QueryOrder> queryOrders,
                                            @NonNull String querySql,
                                            Object[] params){
        //使用分页查询包装查询语句
        String countSql = PrestoSqlUtils.buildCountSql(querySql);
        String pagerSql = PrestoSqlUtils.buildPagerSql(querySql,pager,queryOrders);
        //查询记录总数
        int totalCount = jdbcTemplate.queryForObject(countSql,params,Integer.TYPE);
        //查询数据
        List<RowData> records = jdbcTemplate.query(pagerSql, params, new RowDataMapper(null));
        PagerRecords pagerRecords = new PagerRecords(records,totalCount);
        pagerRecords.setPager(pager);
        return pagerRecords;
    }

}
