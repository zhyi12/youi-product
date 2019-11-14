package org.youi.xlsreport.service;

import org.apache.poi.ss.usermodel.Sheet;
import org.youi.xlsreport.model.XlsReport;

/**
 *
 */
public interface IReportFinderRuleAdapter {


    /**
     * 区域识别规则
     * @param sheet
     * @return
     */
    XlsReport parseReport(Sheet sheet);
}
