package org.youi.metadata.dictionary.service;

import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.metadata.dictionary.entity.DataTableCheckResult;

import java.util.List;

/**
 *
 * 数据表检查服务
 * Created by zhouyi on 2019/10/19.
 */
public interface DataTableCheckService {

    /**
     *
     * @param catalog
     * @param schema
     * @param tableName
     * @return
     */
    @EsbServiceMapping(trancode = "8001035301",caption = "通过数据库检查数据表")
    DataTableCheckResult doCheckFromDb(
            @ServiceParam(name = "catalog") String catalog,
            @ServiceParam(name = "schema") String schema,
            @ServiceParam(name = "tableName") String tableName);
}
