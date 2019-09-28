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
package org.youi.metadata.dictionary.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.youi.metadata.dictionary.entity.MetaDataItem;
import org.youi.metadata.dictionary.mongo.MetaDataItemDao;
import org.youi.rowdata.common.model.BatchResult;
import org.youi.rowdata.common.util.RowDataUtils;
import org.youi.rowdata.xls.XlsRowFileExecutor;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class MetaDataItemImporter extends XlsRowFileExecutor<MetaDataItem> {

    @Autowired(required = false)
    private MetaDataItemDao metaDataItemDao;

    //xls文件中列对应的属性
    private final static String[] METADATA_FILE_PROPERTY_COLUMNS = {
            "","folderId","code","text","oldName","name","description","dataType","dataFormat",
            "convert","version","updateTime","updateDescription","updateOperator",
            "owner","grade","scope","accordingTo"
    };

    /**
     * 从xls文件导入数据字典
     * @param file
     * @return
     */
    public List<BatchResult> fromXls(File file) {
        List<BatchResult> batchResults = this.processFile(file, rowResult -> {
            MetaDataItem metaDataItem = RowDataUtils.mapObject(METADATA_FILE_PROPERTY_COLUMNS, rowResult.getRowData(), MetaDataItem.class);
            metaDataItem.setId(metaDataItem.getCode());
            return metaDataItem;
        }, (records) -> {
            BatchResult batchResult = new BatchResult();
            //保存该批次数据到数据库
            metaDataItemDao.saveAll(records);
            return batchResult;
        });
        return batchResults;
    }
}
