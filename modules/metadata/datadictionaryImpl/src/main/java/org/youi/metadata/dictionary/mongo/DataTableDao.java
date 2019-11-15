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
package org.youi.metadata.dictionary.mongo;

import org.youi.framework.mongo.DaoMongo;
import org.youi.metadata.dictionary.entity.DataTable;

import java.util.List;

/**
 * <p>系统描述: </p>
 * <p>功能描述: DataTable-数据持久层接口</p>
 * @author 代码生成器
 * @since 1.0.0
 */
public interface DataTableDao extends DaoMongo<DataTable, String>  {

    /**
     * 根据资源ID查询数据表集合
     * @param id
     */
    List<DataTable> findByDataResourceId(String id);

    /**
     *
     * @param dataResourceIds
     * @return
     */
    List<DataTable> findByDataResourceIdIn(String[] dataResourceIds);
}