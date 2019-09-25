package com.gwssi.xls;

import com.gwssi.xls.formula.IFormulaProcessor;
import org.apache.poi.ss.format.CellFormat;
import org.apache.poi.ss.format.CellFormatResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * POI related utils
 * Created by liu on 2016/12/5.
 */
public class POIUtils {
    private static DataFormatter formatter = new DataFormatter();

    private static NumberFormat numberFormat = NumberFormat.getInstance();

    static {
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setGroupingUsed(false);
    }

    /**
     * Format Cell
     * @param cell
     * @return
     */
    public static String format(Cell cell,IFormulaProcessor formulaProcessor) {

        if(cell==null){//cell为null快速返回空值
            return "";
        }

        CellStyle style = cell.getCellStyle();

        String value = "";

        String cellFormula;
        try {
            cellFormula = cell.getCellFormula();
        } catch (IllegalStateException e) {//空单元格获取formula将抛出异常
            cellFormula = null;
        }

        try {
            if(style!=null&&style.getDataFormatString()!=null&&
            		style.getDataFormatString().indexOf("reserved")<0){//过滤掉reserved-0x17 至 reserved-0x24 的保留样式
            	String format = style.getDataFormatString();
                CellFormat cf = CellFormat.getInstance(format);
                CellFormatResult result = cf.apply(cell);
                if(style.getDataFormat()== 2 ){//数字格式
                    try{
                        Integer.parseInt(result.text);
                        value = String.format("%.2f",cell.getNumericCellValue());
                    }catch (NumberFormatException e){
                        value = result.text;//如果是文本设置了自定义的数字格式
                    }catch (IllegalStateException e){
                        value = result.text;
                    }
                }else{
                    try{
                        value = numberFormat.format(cell.getNumericCellValue());
                        if("0".equals(value)){
                            value = "";
                        }
                    }catch (Exception e){
                        value = result.text;
                    }
                }
            }else if(formulaProcessor!=null&&org.youi.framework.util.StringUtils.isNotEmpty(cellFormula)){
                return formulaProcessor.computerCellValue(cell);
            }else {
                CellType type = cell.getCellTypeEnum();
                if (type == CellType.NUMERIC &&isReservedDate(cell.getCellStyle().getDataFormat())) {
                    return formatReservedDate(cell);
                }
                value = formatter.formatCellValue(cell);
            }
        } catch (Exception e) {
            value = "";
            e.printStackTrace();
        }


        return value;
    }

    private static boolean isReservedDate(int formatIndex) {
        return  (formatIndex >= 27 && formatIndex <= 31);
    }

    

    private static String formatReservedDate(Cell cell) {
    	DateFormat FORMAT_reserved_0x1F = new SimpleDateFormat("yyyy年M月d日");
        Date date = cell.getDateCellValue();
        return FORMAT_reserved_0x1F.format(date);
    }
}
