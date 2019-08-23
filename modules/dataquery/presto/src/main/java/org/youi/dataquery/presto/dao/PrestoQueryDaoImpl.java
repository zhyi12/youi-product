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
import org.springframework.util.StringUtils;
import org.youi.dataquery.engine.core.RowDataMapper;
import org.youi.dataquery.engine.entity.QueryOrder;
import org.youi.dataquery.engine.entity.RowData;
import org.youi.dataquery.engine.entity.RowItem;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;

import java.text.MessageFormat;
import java.util.ArrayList;
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

    public List<RowData> queryRowData(List<RowItem> columnItems){
        //测试用sql语句
        String sql = "select sum(S203_2018020170000_D1) S203_2018020170000_D1,DATA_101_COL007,tc.catalogItemId from stats_working_task_row_st001 t left join stats_working_task_row_st001_catalog tc on t._id=tc.rowId group by DATA_101_COL007,tc.catalogItemId";
        String[] params = {};
        return jdbcTemplate.query(sql, params, new RowDataMapper(null));
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
                                            String[] params){
        //使用分页查询包装查询语句
        String countSql = buildCountSql(querySql);
        String pagerSql = buildPagerSql(querySql,pager,queryOrders);
        //查询记录总数
        int totalCount = jdbcTemplate.queryForObject(countSql,params,Integer.TYPE);
        //查询数据
        List<RowData> records = jdbcTemplate.query(pagerSql, params, new RowDataMapper(null));
        PagerRecords pagerRecords = new PagerRecords(records,totalCount);
        pagerRecords.setPager(pager);
        return pagerRecords;
    }

    /**
     *
     * @param querySql
     * @param pager
     * @param queryOrders
     * @return
     */
    private String buildPagerSql(String querySql,Pager pager,List<QueryOrder> queryOrders) {
        int startIndex = pager.getStartIndex();
        return MessageFormat.format("SELECT * FROM (SELECT ROW_NUMBER() over({0}) as Row,PT_.* FROM ({1}) as PT_) PTT_ WHERE PTT_.Row BETWEEN {2} AND {3}",
                buildOrderSql(queryOrders),querySql,startIndex,startIndex+pager.getPageSize());
    }

    /**
     * 构建排序串
     * @param queryOrders
     * @return
     */
    private String buildOrderSql(List<QueryOrder> queryOrders) {
        List<String> orderSqls = new ArrayList<>();
        for(QueryOrder queryOrder:queryOrders){
            orderSqls.add(queryOrder.getProperty()+(queryOrder.isAscending()? " asc":" desc"));
        }
        return "ORDER BY "+StringUtils.arrayToDelimitedString(orderSqls.toArray(),",");
    }

    /**
     * 总记录条数语句
     * @param querySql
     * @return
     */
    private String buildCountSql(String querySql) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT count(1) as count FROM ( ").append(querySql).append(")");
        return sqlBuilder.toString();
    }
}
