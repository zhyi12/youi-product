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
package org.youi.dataquery.engine;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public final class DataQueryConstants {

    public final static String DATA_RESOURCE_CATALOG = "catalog";//
    public final static String DATA_RESOURCE_SCHEMA = "schema";//
    public final static String DATA_RESOURCE_TABLE = "table";//
    public final static String DATA_RESOURCE_TABLE_COLUMN = "table-column";//

    public final static String DIM_CATALOG_ID = "L";//目录维度

    public final static String DIM_CATALOG_COLUMN_NAME = "catalog";//目录维度列名称

    public final static String DIM_MEASURE_ID = "I";//计量维度id

    public final static String DIM_CATALOG_ITEM_NAME = "catalogItemId";//目录项列名称

    public final static String TABLE_QUERY_PREFIX = "t_";//
    public final static String CATALOG_TABLE_QUERY_PREFIX = "tc_";//

    public final static String PARAM_GROUP_SUM = "groupSum";//
    public final static String PARAM_SUM = "sum";//
    public final static String PARAM_ITEM_COUNT = "itemCount";//


    public final static String PARAM_RANKING = "ranking";//占比
    public final static String PARAM_PROPORTION = "proportion";//排名


}
