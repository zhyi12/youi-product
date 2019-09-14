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
package org.youi.dataquery.presto.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.youi.dataquery.engine.DataQueryConstants;
import org.youi.dataquery.engine.model.*;
import org.youi.dataquery.engine.service.IQueryService;
import org.youi.dataquery.presto.dao.PrestoQueryDao;
import org.youi.framework.core.dataobj.cube.DataCube;
import org.youi.framework.core.dataobj.cube.Dimension;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("prestoQueryService")
public class PrestoQueryService implements IQueryService {

    @Autowired(required = false)
    private PrestoQueryDao prestoQueryDao;

    @Autowired(required = false)
    private PrestoCubeSqlBuilder prestoCubeSqlBuilder;

    @Autowired
    private PrestoCubeRowDataWriter prestoCubeRowDataWriter;

    /**
     * setter
     * @param prestoQueryDao
     */
    public void setPrestoQueryDao(PrestoQueryDao prestoQueryDao) {
        this.prestoQueryDao = prestoQueryDao;
    }


    public List<String> queryCatalogs(){
        return prestoQueryDao.queryCatalogs("");
    }

    public List<String> querySchemas(String catalog){
        return prestoQueryDao.querySchemas(catalog);
    }

    public List<String> queryTables(String catalog,String schema){
        return prestoQueryDao.queryTables(catalog,schema);
    }

    public List<Item> queryTableColumns(String catalog, String schema, String tableName){
        return prestoQueryDao.queryTableColumns(catalog,schema,tableName);
    }

    //执行查询，返回行数据
    public PagerRecords queryRowDatas(Pager pager){
        return null;
    }

    /**
     *
     * 维度、过滤条件
     * 立方体数据查询
     *
     * @param mainTableName
     * @param groups 分组维度集合
     * @param catalog 目录维度
     * @param measureItems 计量维度项
     * @param queryConditions 查询条件
     * @return
     */
    public DataCube queryDataCube(
            String mainTableName,
            List<Group> groups,
            Catalog catalog,
            List<MeasureItem> measureItems,
            CubeColumns cubeColumns,
            List<QueryCondition> queryConditions){

        List<Object> params = new ArrayList<>();
        //构建presto的立方体查询sql
        String cubeQuerySql = prestoCubeSqlBuilder.buildCubeQuerySql(mainTableName,groups,catalog,measureItems,queryConditions,params);
        //查询立方体数据
        List<CubeRowData> cubeRowDatas = prestoQueryDao.queryCubeRowDatas(cubeColumns,cubeQuerySql,params.toArray(new Object[0]));
        //新建数据立方体对象
        DataCube dataCube = new DataCube();
        //分组维度集合
        if(!CollectionUtils.isEmpty(groups)){
            groups.forEach(group -> dataCube.addDimension(group));
        }
        //目录维度
        if(catalog!=null){
            dataCube.addDimension(catalog);
        }
        //计量维度
        if(!CollectionUtils.isEmpty(measureItems)){
            Dimension measureDimension = new Dimension();
            measureDimension.setId(DataQueryConstants.DIM_MEASURE_ID);
            measureDimension.setItems(new ArrayList<>(measureItems));
            dataCube.addDimension(measureDimension);
        }
        //处理立方体数据
        prestoCubeRowDataWriter.write(dataCube,cubeRowDatas);
        //立方体数据计算

        return dataCube;
    }

    /**
     *
     * @param mainTableName
     * @param groups
     * @param catalog
     * @param measureItems
     * @return
     */
    private CubeColumns buildCubeColumns(
            String mainTableName,
            List<Group> groups,
            Catalog catalog,
            List<MeasureItem> measureItems) {
        CubeColumns cubeColumns = new CubeColumns();
        cubeColumns.setCatalog(catalog!=null);
        measureItems.forEach(measureItem -> cubeColumns.addMeasureColumn(measureItem.getColumnName()));
        if(CollectionUtils.isEmpty(groups)){
            groups.forEach(group -> cubeColumns.addGroupColumn(group.getColumnName()));
        }
        return cubeColumns;
    }

}
