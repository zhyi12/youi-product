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
     *
     * @return
     */
    @EsbServiceMapping
    List<Item> findDataCatalogs();

    /**
     *
     * @param catalog
     * @return
     */
    @EsbServiceMapping
    List<Item> findSchemas(@ServiceParam(name = "catalog") String catalog);

    /**
     *
     * @param catalog
     * @param schema
     * @return
     */
    @EsbServiceMapping
    List<Item> findTables(@ServiceParam(name = "catalog") String catalog,@ServiceParam(name = "schema") String schema);


    /**
     *
     * @param parentId
     * @return
     */
    @EsbServiceMapping
    List<TreeNode> getDataSourceIteratorTree(@ServiceParam(name = "id")String parentId);

    /**
     *
     * @param catalog
     * @param schema
     * @param tableName
     * @return
     */
    @EsbServiceMapping
    List<Item> findTableColumns(
            @ServiceParam(name = "catalog") String catalog,
            @ServiceParam(name = "schema") String schema,
            @ServiceParam(name = "tableName") String tableName);

}
