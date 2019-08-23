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
package org.youi.dataquery.presto.util;

import org.springframework.util.StringUtils;
import org.youi.dataquery.engine.entity.QueryOrder;
import org.youi.framework.core.orm.Pager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public final class PrestoSqlUtils {

    /**
     * 私有构造函数
     */
    private PrestoSqlUtils(){
        //ignore
    }

    /**
     * 构建presto分页查询语句
     * @param querySql
     * @param pager
     * @param queryOrders
     * @return
     */
    public static String buildPagerSql(String querySql, Pager pager, List<QueryOrder> queryOrders) {
        int startIndex = pager.getStartIndex();
        return MessageFormat.format("SELECT * FROM (SELECT ROW_NUMBER() over({0}) as Row,PT_.* FROM ({1}) as PT_) PTT_ WHERE PTT_.Row BETWEEN {2} AND {3}",
                buildOrderSql(queryOrders),querySql,startIndex,startIndex+pager.getPageSize());
    }

    /**
     * 构建排序串
     * @param queryOrders
     * @return
     */
    public static String buildOrderSql(List<QueryOrder> queryOrders) {
        List<String> orderSqls = new ArrayList<>();
        for(QueryOrder queryOrder:queryOrders){
            orderSqls.add(queryOrder.getProperty()+(queryOrder.isAscending()? " asc":" desc"));
        }
        return "ORDER BY "+ StringUtils.arrayToDelimitedString(orderSqls.toArray(),",");
    }

    /**
     * 总记录条数语句
     * @param querySql
     * @return
     */
    public static String buildCountSql(String querySql) {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("SELECT count(1) as count FROM ( ").append(querySql).append(")");
        return sqlBuilder.toString();
    }
}
