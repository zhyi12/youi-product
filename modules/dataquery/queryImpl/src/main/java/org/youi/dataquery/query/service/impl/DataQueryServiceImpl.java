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
package org.youi.dataquery.query.service.impl;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.youi.dataquery.engine.model.QueryOrder;
import org.youi.dataquery.engine.service.IQueryService;
import org.youi.dataquery.query.entity.DataQuery;
import org.youi.dataquery.query.entity.QueryColumn;
import org.youi.dataquery.query.entity.QueryParam;
import org.youi.dataquery.query.service.DataQueryManager;
import org.youi.dataquery.query.service.DataQueryService;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.DomainCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.framework.util.BeanUtils;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("dataQueryService")
public class DataQueryServiceImpl implements DataQueryService{

    @Autowired(required = false)
    private IQueryService queryService;

    @Autowired
    private DataQueryManager dataQueryManager;

    @Autowired
    private SqlExpressionBuilder sqlExpressionBuilder;

    @Autowired
    private SqlParamBuilder sqlParamBuilder;

    /**
     *
     * @param pager
     * @param queryOrders
     * @param queryId
     * @param params  param=userId:zhangsan
     * @return
     */
    @Override
    @EsbServiceMapping
    public PagerRecords queryRowDataByPager(
            Pager pager,
            @DomainCollection(name = "queryOrders", domainClazz = QueryOrder.class) List<QueryOrder> queryOrders,
            @ServiceParam(name = "id") String queryId,
            @DomainCollection(name = "params",domainClazz = QueryParam.class) List<QueryParam> params) {

        DataQuery dataQuery = dataQueryManager.getDataQuery(queryId);

        List<QueryColumn> queryColumns = dataQuery.getQueryColumns();

        if(CollectionUtils.isEmpty(queryColumns)){
            dataQuery = parseDataQuery(dataQuery);
        }

        if(CollectionUtils.isEmpty(queryOrders)){
            QueryOrder queryOrder = new QueryOrder();
            queryOrder.setProperty(dataQuery.getQueryColumns().get(0).getColumnName());
            queryOrders.add(queryOrder);
        }

        String querySql = parseQuerySql(dataQuery);

        //parseParams
        return queryService.queryRowDataByPager(pager,queryOrders,querySql,sqlParamBuilder.parseParams(dataQuery,params));
    }

    @EsbServiceMapping
    public List<Item> getQueryColumns(@ServiceParam(name = "id") String queryId
            ,@ServiceParam(name = "param") String[] params){
        DataQuery dataQuery = dataQueryManager.getDataQuery(queryId);
        return queryService.getQueryColumns(parseQuerySql(dataQuery),params);
    }

    /**
     *
     * @param dataQuery
     * @return
     */
    @EsbServiceMapping
    public DataQuery parseDataQuery(DataQuery dataQuery){
        String querySql = parseQuerySql(dataQuery);
        //查询输出列集合
        List<Item> items = queryService.getQueryColumns(querySql,sqlParamBuilder.parseParams(dataQuery,null));

        List<QueryParam> queryParams = sqlParamBuilder.parseParams(dataQuery);
        List<QueryColumn> queryColumns =  mergeQueryColumns(items,dataQuery.getQueryColumns());

        //合并参数
        mergeQueryParams(queryParams,dataQuery.getQueryParams());

        dataQuery.setQueryColumns(queryColumns);
        dataQuery.setQueryParams(queryParams);

        return dataQuery;
    }

    /**
     *
     * @param items
     * @param orgQueryColumns
     * @return
     */
    private List<QueryColumn> mergeQueryColumns(List<Item> items, List<QueryColumn> orgQueryColumns) {
        Map<String,QueryColumn> queryColumnMap = new HashMap<>();

        if(!CollectionUtils.isEmpty(orgQueryColumns)){
            orgQueryColumns.forEach(queryColumn -> {
                queryColumnMap.put(queryColumn.getColumnName(),queryColumn);
            });
        }

        List<QueryColumn> queryColumns = new ArrayList<>();
        //处理查询列
        items.forEach(item -> {
            QueryColumn queryColumn = new QueryColumn();
            queryColumn.setId(item.getId());
            queryColumn.setColumnName(item.getId());
            queryColumn.setText(item.getText());
            if(queryColumnMap.containsKey(item.getId())){
                BeanUtils.copyProperties(queryColumnMap.get(item.getId()),queryColumn);
            }
            queryColumns.add(queryColumn);
        });
        return queryColumns;
    }

    /**
     *
     * @param queryParams
     * @param orgQueryParams
     */
    private void mergeQueryParams(List<QueryParam> queryParams, List<QueryParam> orgQueryParams) {
        Map<String,QueryParam> queryParamMap = new HashMap<>();
        if(!CollectionUtils.isEmpty(orgQueryParams)){
            orgQueryParams.forEach(queryParam -> {
                if(org.youi.framework.util.StringUtils.isNotEmpty(queryParam.getId())){
                    queryParamMap.put(queryParam.getId(),queryParam);
                }
            });
        }
        //合并数据
        queryParams.forEach(queryParam -> {
            if(queryParamMap.containsKey(queryParam.getId())){
                BeanUtils.copyProperties(queryParamMap.get(queryParam.getId()),queryParam);
            }
        });
    }

    /**
     *
     * @param dataQuery
     * @return
     */
    private String parseQuerySql(DataQuery dataQuery) {
        return sqlExpressionBuilder.buildSql(dataQuery.getSqlExpression());
    }
}
