package org.youi.dataquery.engine.service;

import java.util.List;

/**
 * Created by zhouyi on 2019/8/21.
 */
public interface IQueryService {

    /**
     * 查询数据资源的catalog集合
     * @return
     */
    List<String> queryCatalogs();

    /**
     * 查询数据资源catalog下的schema集合
     * @param catalog
     * @return
     */
    List<String> querySchemas(String catalog);

    /**
     * 查询数据资源catalog、schema下的table集合
     * @param catalog
     * @param schema
     * @return
     */
    List<String> queryTables(String catalog,String schema);

    /**
     * 查询数据表的字段集合
     * @param catalog
     * @param schema
     * @param tableName
     * @return
     */
    List<String> queryTableColumns(String catalog,String schema,String tableName);
}
