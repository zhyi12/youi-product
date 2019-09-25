package com.gwssi.xls.formula;

import org.apache.poi.ss.usermodel.Cell;

/**
 *
 */
public interface IFormulaProcessor {
    /**
     * 根据公式值计算单元格的值
     * @param cell
     * @return
     */
    String computerCellValue(Cell cell) ;
}
