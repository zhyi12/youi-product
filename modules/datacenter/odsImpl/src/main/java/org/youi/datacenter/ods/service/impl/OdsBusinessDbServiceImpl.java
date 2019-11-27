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
package org.youi.datacenter.ods.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youi.datacenter.ods.service.OdsBusinessDbService;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ResultMessage;
import org.youi.framework.esb.annotation.ServiceParam;

import java.text.MessageFormat;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("odsBusinessDbService")
public class OdsBusinessDbServiceImpl implements OdsBusinessDbService {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private OdsBusinessDbTaskExecutor odsBusinessDbTaskExecutor;//

    @Override
    @ResultMessage(info = "数据同步任务启动成功")
    @EsbServiceMapping
    public void loadDataFromBusinessDb(
            @ServiceParam(name = "serviceTaskCode") String datasourceId,
            @ServiceParam(name = "fireTime") String fireTime,
            @ServiceParam(name = "prevFireTime") String prevFireTime
    ) {
        logger.info(MessageFormat.format("同步数据: {0},上次同步时间 {1}，本次同步时间 {2}",datasourceId,fireTime,prevFireTime));
        //
        odsBusinessDbTaskExecutor.syncBusinessDbTables(datasourceId,
                Long.parseLong(fireTime),Long.parseLong(prevFireTime));
    }
}
