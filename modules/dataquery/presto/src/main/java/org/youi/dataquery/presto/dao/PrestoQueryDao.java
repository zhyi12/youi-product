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

import org.springframework.lang.NonNull;
import org.youi.dataquery.engine.entity.*;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;

import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public interface PrestoQueryDao{

    /**
     * 查询行数据
     * @param querySql
     * @param params
     * @param queryOrders
     * @return
     */
    List<RowData> queryRowDatas(String querySql,
                                Object[] params,
                               List<QueryOrder> queryOrders);

    /**
     * 支持主表 + 分类项从表 的立方体数据
     * @return
     */
    List<CubeRowData> queryCubeRowDatas(CubeColumns cubeColumns,String cubeQuerySql, Object[] params);

    /**
     * 分页查询数据
     * @param pager 分页对象
     * @param queryOrders 排序字段集合
     * @param querySql 查询的sql语句
     * @param params sql语句中的？占位对应的值数组
     * @return
     */
    PagerRecords queryRowDataByPager(@NonNull Pager pager,
                                            @NonNull List<QueryOrder> queryOrders,
                                            @NonNull String querySql,
                                     Object[] params);

}
