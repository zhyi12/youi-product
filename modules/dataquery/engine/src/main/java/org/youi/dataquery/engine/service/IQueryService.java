package org.youi.dataquery.engine.service;

import org.springframework.lang.NonNull;
import org.youi.dataquery.engine.model.QueryOrder;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;

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
    List<Item> queryTableColumns(String catalog, String schema, String tableName);

    /**
     * 分页查询数据
     * @param pager
     * @param queryOrders
     * @param querySql
     * @param params
     * @return
     */
    PagerRecords queryRowDataByPager(@NonNull Pager pager,
                                       @NonNull List<QueryOrder> queryOrders,
                                       @NonNull String querySql,
                                       Object[] params);

    /**
     *
     * @param querySql
     * @param params
     * @return
     */
    List<Item> getQueryColumns(String querySql, Object[] params);
}
