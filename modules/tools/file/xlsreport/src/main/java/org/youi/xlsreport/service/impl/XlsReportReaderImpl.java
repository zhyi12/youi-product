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
package org.youi.xlsreport.service.impl;

import com.gwssi.xls.ISheetProcessor;
import com.gwssi.xls.SheetUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.youi.framework.util.StringUtils;
import org.youi.xlsreport.model.XlsReport;
import org.youi.xlsreport.service.IReportFinderDispatcher;
import org.youi.xlsreport.service.XlsReportReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */

@Component
public class XlsReportReaderImpl implements XlsReportReader {

    private final static Log logger = LogFactory.getLog(XlsReportReaderImpl.class);

    @Autowired
    private IReportFinderDispatcher reportFinderDispatcher;

    @Autowired
    private Xls101ReportFinder xls101ReportFinder;

    /**
     *
     * @param inputStream
     * @return
     */
    public List<XlsReport> readReport(InputStream inputStream, boolean readDatas) {

        ReportSheetProcessor sheetProcessor = new ReportSheetProcessor(reportFinderDispatcher,readDatas);

        SheetUtils.processSheets(inputStream, sheetProcessor);

        return sheetProcessor.getXlsReports();
    }

    @Override
    public XlsReport read101Report(InputStream inputStream) {
        return xls101ReportFinder.read101Report(inputStream);
    }

    private static class ReportSheetProcessor implements ISheetProcessor{

        private IReportFinderDispatcher reportFinderDispatcher;
        private boolean readDatas;

        private List<XlsReport> xlsReports = new ArrayList<>();//

        public ReportSheetProcessor(IReportFinderDispatcher reportFinderDispatcher,boolean readDatas) {
            this.reportFinderDispatcher = reportFinderDispatcher;
            this.readDatas = readDatas;
        }

        @Override
        public void processSheet(String sheetName, Sheet sheet) {
            XlsReport xlsReport = reportFinderDispatcher.findReport(sheet,readDatas);
            if(xlsReport!=null){
                if(StringUtils.isEmpty(xlsReport.getTitle())){
                    xlsReport.setTitle(sheetName);
                }
                xlsReports.add(xlsReport);
            }else {
                logger.warn(sheetName+"未识别出xls报表");
            }
        }

        public List<XlsReport> getXlsReports() {
            return xlsReports;
        }
    }
}
