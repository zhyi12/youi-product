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
package org.youi.datacenter.ods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.youi.datacenter.ods.entity.OdsTableMapping;
import org.youi.datacenter.ods.service.OdsTableMappingManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class OdsBusinessDbTaskExecutor extends ThreadPoolTaskExecutor {

    @Autowired
    private OdsTableMappingManager odsTableMappingManager;

    @Autowired
    private OdsTableDataLoader odsTableDataLoader;

    @Override
    public void initialize() {
        super.initialize();
        this.setCorePoolSize(10);//10线程执行数据库同步
        this.setAllowCoreThreadTimeOut(true);
    }

    /**
     *
     * @param fireTime
     */
    public void syncBusinessDbTables(String dataSourceId,Long fireTime,Long prevFireTime){
        //从dataSourceId中解析catalog和schema
        int splitIndex = dataSourceId.indexOf("_");
        if(dataSourceId!=null && splitIndex>0){
            String catalog = dataSourceId.substring(0,splitIndex);
            String schema = dataSourceId.substring(splitIndex+1);
            List<OdsTableMapping> odsTableMappings =
                    odsTableMappingManager.getTableMappingsByDataSource(catalog,schema);

            //执行同步任务
            if(!CollectionUtils.isEmpty(odsTableMappings)){
                List<Future> results = new ArrayList();
                for(OdsTableMapping odsTableMapping:odsTableMappings){
                    //提交任务
                    results.add(this.submit(new BusinessDbTableLoaderTask(odsTableDataLoader,odsTableMapping,prevFireTime)));
                }
                //
                processResult(results);
            }
        }
    }

    /**
     *
     * @param results
     */
    private void processResult(List<Future> results) {
        //使用新的线程处理等待数据同步结果
        new Thread(()->{
            results.forEach((result)->{
                try {
                    result.get();
                } catch (InterruptedException e) {
                    logger.error("数据同步中断："+e.getMessage());
                } catch (ExecutionException e) {
                    logger.error("数据同步异常："+e.getMessage());
                }
            });
            //TODO 通知完成本批次的任务
        }).start();
    }
}
