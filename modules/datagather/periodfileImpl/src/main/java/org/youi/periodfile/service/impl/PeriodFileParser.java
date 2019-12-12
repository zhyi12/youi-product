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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.youi.fileserver.filestore.service.FileClientManager;
import org.youi.periodfile.entity.PeriodFile;
import org.youi.xlsreport.model.XlsReport;
import org.youi.xlsreport.service.XlsReportReader;
import org.youi.xlsreport.util.XlsReportUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class PeriodFileParser {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private FileClientManager fileClientManager;

    @Autowired(required = false)
    private XlsReportReader xlsReportReader;

    /**
     *
     * @param periodFile
     * @return
     */
    public XlsReport parseXlsReport(PeriodFile periodFile) {
        Assert.notNull(periodFile,"periodFile不能为空.");
        Assert.notNull(periodFile.getFilePath(),"文件未上传.");

        return fileClientManager.parseFile(periodFile.getFilePath(),(file -> {
            try(InputStream inputStream = new FileInputStream(file)){
                List<XlsReport> xlsReports = xlsReportReader.readReport(inputStream,false);

                XlsReport xlsReport;
                if(!CollectionUtils.isEmpty(xlsReports)){
                    xlsReport = xlsReports.get(0);//返回第一个xls表式
                }else{
                    xlsReport = new XlsReport();//构建新的xlsReport对象
                }
                xlsReport.setContent(XlsReportUtils.convert2Html(file,0));
                return xlsReport;
            }catch (IOException e){
                logger.error(e.getMessage());
                return new XlsReport();//返回空对象
            }
        }));
    }
}
