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
package org.youi.periodfile.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.periodfile.entity.PeriodFile;
import org.youi.periodfile.service.PeriodFileManager;
import org.youi.periodfile.service.PeriodFileService;
import org.youi.xlsreport.model.XlsReport;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("periodFileService")
public class PeriodFileServiceImpl implements PeriodFileService {

    @Autowired
    private PeriodFileParser periodFileParser;

    @Autowired
    private PeriodFileManager periodFileManager;

    @EsbServiceMapping(trancode="8005080151",caption="固定期文件xls文件输出")
    public XlsReport parseXlsReport(@ServiceParam(name="id") String id){
        PeriodFile periodFile = periodFileManager.getPeriodFile(id);
        return periodFileParser.parseXlsReport(periodFile);
    }
}
