package com.gwssi.xls.formula;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 */
public class FormulaProcessorFactory {

    private static FormulaProcessorFactory instance = new FormulaProcessorFactory();

    private FormulaProcessorFactory(){

    }

    public static FormulaProcessorFactory getInstance(){
        return instance;
    }


    public IFormulaProcessor createFormulaProcessor(Workbook wb){
        IFormulaProcessor formulaProcessor = null;
        if(wb instanceof XSSFWorkbook){
            formulaProcessor = new XSSFFormulaProcessor((XSSFWorkbook)wb);
        }else{
            formulaProcessor = new HSSFFormulaProcessor((HSSFWorkbook)wb);
        }
        return formulaProcessor;
    }
}
