/*
 * Copyright 2018-2024 the original author or authors.
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
package org.youi.datacenter.ods.service;

import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ResultMessage;
import org.youi.framework.esb.annotation.ServiceParam;

/**
 *
 * 贴源数据库对业务数据库的相关服务
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public interface OdsBusinessDbService {

    /**
     * 加载业务镜像数据库对数据
     *
     * @param datasourceId 从定时任务传递的参数，存储数据源唯一标识,（catalog+'_'+schema）
     * @param fireTime 任务启动时间
     * @param prevFireTime 上次任务执行时间
     */
    @EsbServiceMapping
    void loadDataFromBusinessDb(
            @ServiceParam(name = "serviceTaskCode") String datasourceId,
            @ServiceParam(name = "fireTime") String fireTime,
            @ServiceParam(name = "prevFireTime") String prevFireTime);

}
