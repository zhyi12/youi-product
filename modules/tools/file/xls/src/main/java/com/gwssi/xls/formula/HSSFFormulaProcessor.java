package com.gwssi.xls.formula;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 *
 */
public class HSSFFormulaProcessor implements IFormulaProcessor{

    private HSSFWorkbook workbook;

    private HSSFFormulaEvaluator hssfFormulaEvaluator;

    public HSSFFormulaProcessor(HSSFWorkbook workbook){
        this.workbook = workbook;
        this.hssfFormulaEvaluator = new HSSFFormulaEvaluator(workbook);
    }

    @Override
    public String computerCellValue(Cell cell) {

        return hssfFormulaEvaluator.evaluate(cell).getStringValue();
    }
}
