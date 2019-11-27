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

import org.youi.datacenter.ods.entity.OdsTableMapping;

import java.util.concurrent.Callable;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class BusinessDbTableLoaderTask implements Callable<String> {


    private OdsTableDataLoader odsTableDataLoader;//

    private OdsTableMapping odsTableMapping;//

    private Long prevLoadTime;//上次执行时间

    public BusinessDbTableLoaderTask(OdsTableDataLoader odsTableDataLoader,
                                     OdsTableMapping odsTableMapping,
                                     Long prevLoadTime) {
        this.odsTableDataLoader = odsTableDataLoader;
        this.odsTableMapping = odsTableMapping;
        this.prevLoadTime = prevLoadTime;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Override
    public String call() throws Exception {
        odsTableDataLoader.loadData(odsTableMapping,prevLoadTime);
        //TODO 记录执行情况
        return "";
    }
}
