package com.gwssi.xls;

import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 */
public interface ISheetProcessor {

    void processSheet(String sheetName, Sheet sheet);
}
