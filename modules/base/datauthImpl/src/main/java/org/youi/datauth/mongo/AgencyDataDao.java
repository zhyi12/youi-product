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
package org.youi.datauth.mongo;

import org.youi.framework.mongo.DaoMongo;
import org.youi.datauth.entity.AgencyData;

/**
 * <p>系统描述: </p>
 * <p>功能描述: AgencyData-数据持久层接口</p>
 * @author 代码生成器
 * @since 1.0.0
 */
public interface AgencyDataDao extends DaoMongo<AgencyData, String>  {

    /**
     * jpa 约定查询： 根据授权数据类型和机构ID获取机构数据
     * @param authDataType
     * @param agencyId
     * @return
     */
    AgencyData getDataByAuthDataTypeAndAgencyId(String authDataType, String agencyId);
}