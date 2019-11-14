package org.youi.xlsreport.service;

import org.apache.poi.ss.usermodel.Sheet;
import org.youi.xlsreport.model.XlsReport;

/**
 *
 */
public interface IReportFinderDispatcher {

    /**
     *
     * @param sheet
     * @return
     */
    XlsReport findReportWithData(Sheet sheet);

    /**
     *
     * @param sheet
     * @return
     */
    XlsReport findReport(Sheet sheet);

    /**
     *
     * @param sheet
     * @param readData
     * @return
     */
    XlsReport findReport(Sheet sheet, boolean readData);

}
