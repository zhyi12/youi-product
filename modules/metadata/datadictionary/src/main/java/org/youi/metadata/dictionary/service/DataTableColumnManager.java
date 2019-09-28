/*
 * YOUI框架
 * Copyright 2018 the original author or authors.
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
package org.youi.metadata.dictionary.service;

import java.util.List;
import java.util.Collection;

import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.*;

import org.youi.metadata.dictionary.entity.DataTableColumn;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
public interface DataTableColumnManager{

    /**
     * 条件查询列表
     */
    @EsbServiceMapping(trancode="8001030401",caption="主键查询数据列")
    List<DataTableColumn> getDataTableColumns(
        @ConditionCollection(domainClazz=DataTableColumn.class) Collection<Condition> conditions,
        @OrderCollection Collection<Order> orders);
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8001030402",caption="主键查询数据列")
    DataTableColumn getDataTableColumn(@ServiceParam(name="id") String id);

	/**
	 * 分页查询用户
	 * @return 分页对象
	 */
    @EsbServiceMapping(trancode="8001030403",caption="分页查询数据列")
    PagerRecords getPagerDataTableColumns(Pager pager,//分页条件
    @ConditionCollection(domainClazz=DataTableColumn.class) Collection<Condition> conditions,//查询条件
        @OrderCollection Collection<Order> orders);
    /**
     * 保存并返回对象
     */
    @EsbServiceMapping(trancode="8001030404",caption="保存数据列")
    DataTableColumn saveDataTableColumn(DataTableColumn o);

    /**
     * 根据主键集合删除对象
     * @param id
     */
    @EsbServiceMapping(trancode="8001030405",caption="主键删除数据列")
    void removeDataTableColumn(@ServiceParam(name="id") String id);

    @EsbServiceMapping(trancode="8001030406",caption="更新数据库表列集合")
    void updateDataTableColumns(
            @ServiceParam(name="dataResourceId") String dataResourceId,
            @ServiceParam(name="tableName") String tableName,
            @DomainCollection(domainClazz = DataTableColumn.class,name = "columns") List<DataTableColumn> columns);
    /**
     *
     * @param dataResourceId
     * @param tableName
     * @return
     */
    List<DataTableColumn> findByDataResourceIdAndTableName(String dataResourceId, String tableName);
}
