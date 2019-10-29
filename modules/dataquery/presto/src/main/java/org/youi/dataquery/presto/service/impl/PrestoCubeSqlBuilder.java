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

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.youi.dataquery.engine.core.CubeQuerySqlBuilder;
import org.youi.dataquery.engine.model.Catalog;
import org.youi.dataquery.engine.model.Group;
import org.youi.dataquery.engine.model.QueryCondition;
import org.youi.framework.core.Constants;
import org.youi.framework.core.exception.BusException;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class PrestoCubeSqlBuilder extends CubeQuerySqlBuilder {

    @Override
    protected String buildInnerGroupSql(List<Group> groups, Catalog catalog,List<String> columnExpressions) {
        StringBuilder sqlBuilder = new StringBuilder();
        //TODO 生成层级联动的分组维度,比如 国家 - 省 ，年 - 月类型的级联列
        sqlBuilder.append(" GROUP by cube(")
            .append(StringUtils.arrayToDelimitedString(columnExpressions.toArray(),","))
            .append(")");
        return sqlBuilder.toString();
    }

    @Override
    protected String buildWhereSql(List<Group> groups, Catalog catalog, List<QueryCondition> queryConditions,List<Object> params) {
        if(CollectionUtils.isEmpty(queryConditions)){
            return "";//快速返回空串
        }

        StringBuilder sqlBuilder = new StringBuilder();
        //group
        Map<String,List<QueryCondition>> orConditionMap = new HashMap<>();//或条件
        List<String> conditionSqls = new ArrayList<>();//and条件

        queryConditions.forEach(queryCondition -> {
            if(StringUtils.isNotEmpty(queryCondition.getGroup())){
                List<QueryCondition> groupQueryConditions = new ArrayList<>();
                if(orConditionMap.containsKey(queryCondition.getGroup())){
                    groupQueryConditions = orConditionMap.get(queryCondition.getGroup());
                }
                groupQueryConditions.add(queryCondition);
                //
                orConditionMap.put(queryCondition.getGroup(),groupQueryConditions);
            }else{
                conditionSqls.add(buildConditionSql(queryCondition,params));
            }
        });

        if(!CollectionUtils.isEmpty(orConditionMap)){
            orConditionMap.forEach((conditionGroup,groupQueryConditions)->{
                conditionSqls.add(buildOrConditionSqls(groupQueryConditions,params));
            });
        }

        sqlBuilder.append(" WHERE ").append(StringUtils.arrayToDelimitedString(conditionSqls.toArray()," AND "));

        return sqlBuilder.toString();
    }

    /**
     *
     * @param groupQueryConditions
     * @param params
     * @return
     */
    private String buildOrConditionSqls(List<QueryCondition> groupQueryConditions,List<Object> params) {
        List<String> conditionSqls = new ArrayList<>();//or条件
        groupQueryConditions.forEach(queryCondition -> {
            conditionSqls.add(buildConditionSql(queryCondition,params));
        });
        return StringUtils.arrayToDelimitedString(conditionSqls.toArray()," OR ");
    }

    /**
     *
     * @param queryCondition
     * @param params
     * @return
     */
    private String buildConditionSql(QueryCondition queryCondition,List<Object> params) {
        StringBuilder sqlBuilder = new StringBuilder();
        String operator = queryCondition.getOperator();

        sqlBuilder.append(queryCondition.getProperty()).append(" ");
        if(Condition.IN.equals(operator)){
            List<Object>  values = buildMultiParamValues(queryCondition.getValue());//
            sqlBuilder.append(" IN  ").append("("+buildQuestionMarks(values.size())+")");
            params.addAll(values);
        }else if(Condition.NOT_IN.equals(operator)){
            List<Object>  values = buildMultiParamValues(queryCondition.getValue());//
            sqlBuilder.append(" NOT IN  ").append("("+buildQuestionMarks(values.size())+")");
            params.addAll(values);
        }else if(Condition.LIKE.equals(operator)){
            sqlBuilder.append(operator).append(" ? ");
            params.add(" %"+queryCondition.getValue()+"%");
        }else if(Condition.START.equals(operator)){
            sqlBuilder.append(" LIKE ? ");
            params.add(" %"+queryCondition.getValue());
        }else if(Condition.NOT_EQUALS.equals(operator)){
            sqlBuilder.append(" != ").append("?");
        }else if(Condition.IS_NULL.equals(operator)){
            sqlBuilder.append(" is null ");
        }else if(Condition.IS_NOT_NULL.equals(operator)){
            sqlBuilder.append(" is not null ");
        }else{
            sqlBuilder.append(operator).append(" ? ");
            params.add(queryCondition.getValue());
        }

        return sqlBuilder.toString();
    }

    private List<Object> buildMultiParamValues(Object value){
        List<Object> params = new ArrayList<>();
        if(value.getClass().isArray()){
            //数组类型
            Object[] values = (Object[])value;
            for(Object oValue:values){
                params.add(oValue);
            }
        }else if(List.class.isAssignableFrom(value.getClass())){
            //集合类型
            params.addAll((List)value);
        }else{
            params.add(value);
        }

        return params;
    }

    private String buildQuestionMarks(int count){
        List<String> questionMarks = new ArrayList<>();
        for(int i=0;i<count;i++){
            questionMarks.add("?");
        }
        return StringUtils.arrayToDelimitedString(questionMarks.toArray(),",");
    }
}
