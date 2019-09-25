package com.gwssi.xls;

import org.apache.poi.xssf.usermodel.XSSFCell;

/**
 *
 */
public interface ICellProcessor {

    /**
     *
     * @param copyCell
     * @param rowIndex
     * @param colIndex
     */
    void processCell(XSSFCell copyCell, int rowIndex, int colIndex);
}
