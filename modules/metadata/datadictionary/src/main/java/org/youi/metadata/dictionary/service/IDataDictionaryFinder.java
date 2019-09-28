package org.youi.metadata.dictionary.service;

import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;

import java.util.List;

/**
 * Created by zhouyi on 2019/9/13.
 */
public interface IDataDictionaryFinder {

    /**
     * 查询资源数据库的catalog集合
     * @return
     */
    @EsbServiceMapping(trancode = "8001030501",caption = "查询资源数据库的catalog集合")
    List<Item> findDataCatalogs();

    /**
     * 根据catalog获取schemas集合
     * @param catalog 资源数据库的catalog
     * @return
     */
    @EsbServiceMapping(trancode = "8001030502",caption = "根据catalog获取schemas集合")
    List<Item> findSchemas(@ServiceParam(name = "catalog") String catalog);

    /**
     * 根据catalog和schema获取table集合
     * @param catalog 数据库catalog
     * @param schema 数据库schema
     * @return
     */
    @EsbServiceMapping(trancode = "8001030503",caption = "根据catalog和schema获取table集合")
    List<Item> findTables(@ServiceParam(name = "catalog") String catalog,@ServiceParam(name = "schema") String schema);


    /**
     * 分级获取数据资源树
     * @param parentId
     * @return
     */
    @EsbServiceMapping(trancode = "8001030504",caption = "分级获取数据资源树")
    List<TreeNode> getDataSourceIteratorTree(@ServiceParam(name = "id")String parentId);

    /**
     * 根据catalog,schem,tableName 获取表的列集合
     * @param catalog
     * @param schema
     * @param tableName
     * @return
     */
    @EsbServiceMapping(trancode = "8001030504",caption = "根据catalog,schem,tableName 获取表的列集合")
    List<Item> findTableColumns(
            @ServiceParam(name = "catalog") String catalog,
            @ServiceParam(name = "schema") String schema,
            @ServiceParam(name = "tableName") String tableName);

}
