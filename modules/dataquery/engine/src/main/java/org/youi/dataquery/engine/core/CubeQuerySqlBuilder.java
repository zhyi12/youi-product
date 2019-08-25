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
package org.youi.dataquery.engine.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.lang.NonNull;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.youi.dataquery.engine.DataQueryConstants;
import org.youi.dataquery.engine.entity.Catalog;
import org.youi.dataquery.engine.entity.Group;
import org.youi.dataquery.engine.entity.MeasureItem;
import org.youi.dataquery.engine.entity.QueryCondition;
import org.youi.dataquery.engine.utils.DimensionUtils;
import org.youi.framework.core.orm.Condition;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public abstract class CubeQuerySqlBuilder {

    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 约定格式的主从表立方体数据查询
     *  主表主键 _id
     *  从表外健 rowId
     * @param mainTableName
     * @param groups
     * @param catalog
     * @param measureItems
     * @param queryConditions
     * @return
     */
    public String buildCubeQuerySql(
            @NonNull String mainTableName,
            List<Group> groups,
            Catalog catalog,
            List<MeasureItem> measureItems,
            List<QueryCondition> queryConditions,List<Object> params) {

        queryConditions = processQueryConditions(groups,catalog,queryConditions);

        StringBuilder sqlBuilder = new StringBuilder();
        //TODO 默认主表_id 字段为主键，从表rowId字段为外健，可增加配置规则
        sqlBuilder.append("SELECT ")
                .append(buildOutColumnsSql(groups,catalog,measureItems))
                .append(" FROM ").append(mainTableName).append(" as "+DataQueryConstants.TABLE_QUERY_PREFIX)
                .append(catalog==null?"":(MessageFormat.format(" left join {0} as {1} on {2}._id={1}.rowId ",catalog.getTableName(),DataQueryConstants.CATALOG_TABLE_QUERY_PREFIX,DataQueryConstants.TABLE_QUERY_PREFIX)))
                .append(buildWhereSql(groups,catalog,queryConditions,params))
                .append(buildGroupSql(groups,catalog));

        if(logger.isDebugEnabled()){
            logger.debug("cube query sql : "+sqlBuilder.toString());
        }
        return sqlBuilder.toString();
    }

    /**
     *
     * @param groups
     * @param catalog
     * @param queryConditions
     * @return
     */
    protected  List<QueryCondition> processQueryConditions(List<Group> groups,Catalog catalog,List<QueryCondition> queryConditions){
        List<QueryCondition> conditions = new ArrayList<>();
        //子表的名录名条件
        if(!StringUtils.isEmpty(catalog)){
            conditions.add(buildCatalogCondition(catalog));
        }

        if(!CollectionUtils.isEmpty(groups)){
            groups.forEach(group -> {
                if(!DimensionUtils.isTreeGroupItems(group)){//非树形结构的分组项，加入查询条件
                    conditions.add(buildGroupCondition(group,catalog));
                }
            });
        }
        //参数中的条件
        if(!CollectionUtils.isEmpty(queryConditions)){
            //计量条件 measure
            //分组条件 group
            //计算条件 having
            conditions.addAll(queryConditions);
        }
        return conditions;
    }

    /**
     * 构建分组查询条件
     * @param group
     * @param catalog
     * @return
     */
    protected QueryCondition buildGroupCondition(Group group, Catalog catalog){
        List<Object> values = new ArrayList<>();

        if(group!=null && !CollectionUtils.isEmpty(group.getItems())){
            group.getItems().forEach(item -> values.add(item.getId()));
        }

        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setProperty(parseColumnPrefix(group.getTableName(),catalog)+"."+group.getColumnName());
        queryCondition.setOperator(Condition.IN);
        queryCondition.setValue(values.toArray());//
        return queryCondition;
    }

    /**
     * 构建目录查询条件
     * @param catalog
     * @return
     */
    protected QueryCondition buildCatalogCondition(Catalog catalog){
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setProperty(DataQueryConstants.CATALOG_TABLE_QUERY_PREFIX+"."+catalog.getColumnName());
        queryCondition.setOperator(Condition.EQUALS);
        queryCondition.setValue(catalog.getId());//
        return queryCondition;
    }

    /**
     * 生成group by 语句
     * @param groups
     * @param catalog
     * @return
     */
    private String buildGroupSql(List<Group> groups,
                                 Catalog catalog) {
        List<String> columnExpressions = new ArrayList<>();
        //维度列
        groups.forEach(group -> columnExpressions.add(parseColumnPrefix(group.getTableName(),catalog)+"."+group.getColumnName()));
        return buildInnerGroupSql(groups,catalog,columnExpressions);
    }

    protected abstract String buildInnerGroupSql(List<Group> groups, Catalog catalog,List<String> columnExpressions);

    /**
     * 生成查询条件语句
     * @param groups
     * @param catalog
     * @param queryConditions
     * @return
     */
    protected abstract String buildWhereSql(List<Group> groups,
                                            Catalog catalog, List<QueryCondition> queryConditions,List<Object> params);

    /**
     * 生成输出列语句
     * @param groups
     * @param catalog
     * @param measureColumns
     * @return
     */
    private String buildOutColumnsSql(List<Group> groups,
                                      Catalog catalog, List<MeasureItem> measureColumns) {
        List<String> columnExpressions = new ArrayList<>();
        //维度列
        groups.forEach(group -> {
            //输出的查询字段
            columnExpressions.add(parseColumnPrefix(group.getTableName(),catalog)+"."+group.getColumnName());
        });
        //计量列
        measureColumns.forEach(measureItem ->
                columnExpressions.add(buildMeasureSql(measureItem,catalog)));

        if(catalog!=null){
            //目录项列
            columnExpressions.add(DataQueryConstants.CATALOG_TABLE_QUERY_PREFIX+"."+DataQueryConstants.DIM_CATALOG_ITEM_NAME);
        }

        return StringUtils.arrayToDelimitedString(columnExpressions.toArray(),",");
    }

    /**
     * 计量列
     * @param measureItem
     * @param catalog
     * @return
     */
    private String buildMeasureSql(MeasureItem measureItem,Catalog catalog){
        return measureItem.getAggregate()+"("+parseColumnPrefix(measureItem.getTableName(),catalog)+
                "."+measureItem.getColumnName()+") as "+measureItem.getColumnName();
    }

    /**
     *SELECT t_.DATA_101_COL007,t_.DATA_101_COL007,sum(t_.S203_20180_A0) FROM stats_working_task_row_st001 as t_ GROUP by cube(t_.DATA_101_COL007,t_.DATA_101_COL007)
     * @param tableName
     * @param catalog
     * @return
     */
    private String parseColumnPrefix(String tableName,Catalog catalog){
        String prefix = "";
        if(catalog!=null && catalog.getTableName().equals(tableName)){
            //子表中的其他分类字段
            prefix = DataQueryConstants.CATALOG_TABLE_QUERY_PREFIX;
        }else{
            //主表的分类字段
            prefix = DataQueryConstants.TABLE_QUERY_PREFIX;
        }
        return prefix;
    }
}
