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

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.youi.dataquery.query.entity.DataQuery;
import org.youi.dataquery.query.entity.QueryParam;
import org.youi.dataquery.query.entity.SqlExpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * sql查询参数生成
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class SqlParamBuilder {

    private final static Pattern paramPattern = Pattern.compile("(\\?)");

    private final static String PARAM_VALUE_EMPTY = "_EMPTY";

    public Object[] parseParams(DataQuery dataQuery, List<QueryParam> queryParams){
        Matcher matcher = paramPattern.matcher(dataQuery.getSqlExpression().getSource());
        List<QueryParam> dataQueryParams = dataQuery.getQueryParams();

        Map<String,String> paramMap = new HashMap<>();

        if(!CollectionUtils.isEmpty(queryParams)){
            queryParams.forEach(queryParam -> paramMap.put(queryParam.getProperty(),queryParam.getValue()));
        }

        List params = new ArrayList();

        int index = 0;
        while(matcher.find()){
            index++;
            if(dataQueryParams!=null && index<=dataQueryParams.size()){
                String property = dataQueryParams.get(index-1).getProperty();
                if(paramMap.containsKey(property)){
                    params.add(paramMap.get(property));
                    continue;
                }
            }
            params.add(PARAM_VALUE_EMPTY);
        }

        return params.toArray();
    }


    public List<QueryParam> parseParams(DataQuery dataQuery){
        String sqlSource = dataQuery.getSqlExpression().getSource();
        Matcher matcher = paramPattern.matcher(sqlSource);
        List<QueryParam> params = new ArrayList();

        int index = 0;
        while(matcher.find()){
            QueryParam queryParam = new QueryParam();
            queryParam.setId(buildParamId(sqlSource,matcher.end()));
            queryParam.setName(StringUtils.leftPad(new Integer(++index).toString(),3,"0"));
            queryParam.setValue(PARAM_VALUE_EMPTY);
            params.add(queryParam);
        }
        return params;
    }

    private String buildParamId(String sqlSource, int end) {
        String prefix = sqlSource.substring(end - 15 ,end);
        return "I"+new Integer(Math.abs(prefix.hashCode())).toString();
    }


    public static void main(String[] args){
        SqlParamBuilder sqlParamBuilder = new SqlParamBuilder();
        DataQuery dataQuery = new DataQuery();
        SqlExpression sqlExpression = new SqlExpression();

        sqlExpression.setSource("SELECT {0},{1},{2} FROM {3} where (? = '_EMPTY' or {4}=?)");

        dataQuery.setSqlExpression(sqlExpression);

        List<QueryParam>  params = sqlParamBuilder.parseParams(dataQuery);

        System.out.println(params);
    }

}
