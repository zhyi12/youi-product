package com.gwssi.xls.formula;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 */
public class XSSFFormulaProcessor implements IFormulaProcessor{

    private XSSFWorkbook workbook;

    private XSSFFormulaEvaluator xssfFormulaEvaluator;

    public XSSFFormulaProcessor(XSSFWorkbook workbook){
        this.workbook = workbook;
        this.xssfFormulaEvaluator = new XSSFFormulaEvaluator(workbook);
    }

    @Override
    public String computerCellValue(Cell cell) {
        return String.format(String.format("%.2f",xssfFormulaEvaluator.evaluate(cell).getNumberValue()));
    }
}
