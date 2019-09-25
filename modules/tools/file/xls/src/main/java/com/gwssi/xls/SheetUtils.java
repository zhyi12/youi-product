package com.gwssi.xls;

import com.gwssi.xls.formula.FormulaProcessorFactory;
import com.gwssi.xls.formula.IFormulaProcessor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 *
 */
public final class SheetUtils {

    private final static Log logger = LogFactory.getLog(SheetUtils.class);


    static {
        ZipSecureFile.setMinInflateRatio(0l);//
    }

    /**
     * 私有构造函数
     */
    private SheetUtils() {
        //ignore
    }

    /**
     * @param file
     * @return
     */
    public static List<String> getSheetNames(File file) {
        List<String> sheetNames = new LinkedList<>();

        if (file == null) {
            return sheetNames;
        }

        processSheets(file, new ISheetProcessor() {
            @Override
            public void processSheet(String sheetName, Sheet sheet) {
                sheetNames.add(sheetName);
            }
        });

        return sheetNames;
    }

    /**
     *
     * @param in
     * @param sheetProcessor
     * @param index
     */
    public static void processSheets(InputStream in, ISheetProcessor sheetProcessor, int index) {
        processSheets(in,sheet -> {
            sheetProcessor.processSheet(sheet.getSheetName(),sheet);
            return "";
        },index);
    }

    public static void processSheets(File file, ISheetProcessor sheetProcessor, ISheetHandleException sheetHandleException){
        try(InputStream inputStream = new FileInputStream(file)){
            processSheets(inputStream,
                    sheet -> {
                        sheetProcessor.processSheet(sheet.getSheetName(),sheet);
                        return "";
                    },
                    exception -> {
                        sheetHandleException.handleException(exception);
                        return "";
                    },
                    0);
        }catch (IOException e){
            logger.warn(e.getMessage());
        }
    }

    /**
     * @param in
     * @param sheetFunc
     */
    public static <R> void processSheets(InputStream in, Function<Sheet,R> sheetFunc, Function<Exception,R> errorFunc, int index) {
        Workbook wb = null;

        try {
            wb = WorkbookFactory.create(in);
            int sheetCount = 1;

            if (index == 0) {
                sheetCount = wb.getNumberOfSheets();
            }

            Iterator<Sheet> sheetIterator = wb.sheetIterator();

            int curIndex = 0;
            while (curIndex<sheetCount&&sheetIterator.hasNext()){
                Sheet sheet = sheetIterator.next();
                try {
                    if(!wb.isSheetHidden(curIndex)) {
                        //sheetProcessor.processSheet(sheet.getSheetName(),sheet);
                        sheetFunc.apply(sheet);
                    }
                } catch (Exception e) {
                    if(errorFunc != null){
                        errorFunc.apply(e);
                    }
                    //忽略异常，输出警告信息
                    logger.warn("共"+sheetCount+"页"+"第"+curIndex+"页异常"+e.getMessage());
                }
                curIndex++;
            }

        } catch (IOException e) {
            if(errorFunc != null){
                errorFunc.apply(e);
            }
            logger.warn(e.getMessage());
        } catch (InvalidFormatException e) {
            if(errorFunc != null){
                errorFunc.apply(e);
            }
            logger.warn(e.getMessage());
        } catch(Exception e){
            if(errorFunc != null){
                errorFunc.apply(e);
            }
        } finally{
            IOUtils.closeQuietly(wb);
            IOUtils.closeQuietly(in);
        }
    }

    public static <R> void processSheets(InputStream in, Function<Sheet,R> sheetFunc, int index) {
        processSheets(in,sheetFunc,null,index);
    }

    /**
     * @param file
     * @param sheetProcessor
     */
    public static void processSheets(File file, ISheetProcessor sheetProcessor) {
        try(InputStream inputStream = new FileInputStream(file)){
            processSheets(inputStream, sheetProcessor, 0);
        }catch (IOException e){
            logger.warn(e.getMessage());
        }
    }

    public static <R> R visitSheets(File file, Function<Sheet,R> sheetFunc,int index) {
        try(InputStream inputStream = new FileInputStream(file)){
            processSheets(inputStream, sheetFunc, index);
        }catch (IOException e){
            logger.warn(e.getMessage());
        }

        return null;
    }

    public static void processSheets(InputStream inputStream, ISheetProcessor sheetProcessor) {
        processSheets(inputStream, sheetProcessor, 0);
    }

    /**
     * @param sheet
     * @param output
     */
    public static void copySheet2XssfWorkbook(Sheet sheet, OutputStream output) {
    	IFormulaProcessor formulaProcessor = FormulaProcessorFactory.getInstance().createFormulaProcessor(sheet.getWorkbook());
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook();
            XSSFSheet copySheet = workbook.createSheet();

           Iterator<Row> iterator = sheet.rowIterator();

           int i=0;//
           while(iterator.hasNext()&&i<200){
               Row row = iterator.next();
               if (row == null) continue;
               if (i == 0) {//列宽度设置
                   for (int j = 0; j < row.getLastCellNum(); j++) {
                       copySheet.setColumnWidth(j, sheet.getColumnWidth(j));
                   }
               }
               i++;

               XSSFRow xssfRow = copySheet.createRow(row.getRowNum());
               try {
                   workbook = copyRow(row, xssfRow, formulaProcessor, workbook);
                   //
               } catch (Exception e) {
//                   e.printStackTrace();
                   System.out.println(row.getRowNum()+" - "+e.getMessage());
               }
           }

            //合并单元格
            mergerRegion(copySheet, sheet);

            output.flush();
            workbook.write(output);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        } finally {
            IOUtils.closeQuietly(workbook);
            IOUtils.closeQuietly(output);
        }
    }

    /**
     * 按指定行复制
     * @param sheet
     * @param dataMap
     */
    public static Workbook copySheet2XssfWorkbook(Sheet sheet,Map<Integer,Integer> dataMap) {
    	int maxRowNum = 0;
    	for (Integer key : dataMap.keySet()) {
    		if(key>maxRowNum){
    			maxRowNum = key;
			}
    	}
    	
    	maxRowNum = Math.max(maxRowNum, sheet.getLastRowNum());//取最大的行数
    	
    	IFormulaProcessor formulaProcessor = FormulaProcessorFactory.getInstance().createFormulaProcessor(sheet.getWorkbook());
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook();
            XSSFSheet copySheet = workbook.createSheet();
            
			for (int i = 0; i < maxRowNum; i++) {
				if(dataMap.containsKey(i+1)){
					int rowIndex = dataMap.get(i+1);
					if(rowIndex == -1){//添加行
						XSSFRow xssfRow = copySheet.createRow(i);
						Row row = xssfRow;
						workbook = copyRow(row, xssfRow, formulaProcessor, workbook);
					}else{
						Row row = sheet.getRow(rowIndex-1);
						if (row == null){
							XSSFRow xssfRow = copySheet.createRow(i);
							row = xssfRow;
							workbook = copyRow(row, xssfRow, formulaProcessor, workbook);
							continue;
						}
						
						if (i == 0) {// 列宽度设置
							for (int j = 0; j < row.getLastCellNum(); j++) {
								copySheet.setColumnWidth(j, sheet.getColumnWidth(j));
							}
						}
						
						XSSFRow xssfRow = copySheet.createRow(i);
						try {
							workbook = copyRow(row, xssfRow, formulaProcessor, workbook);
							//
						} catch (Exception e) {
							// e.printStackTrace();
							System.out.println(row.getRowNum() + " - " + e.getMessage());
						}
					}
					
				}else{
					XSSFRow xssfRow = copySheet.createRow(i);
					Row row = sheet.getRow(i);
					if (row == null){
						row = xssfRow;
					}
					workbook = copyRow(row, xssfRow, formulaProcessor, workbook);
				}
				
			}
			return workbook;
        } catch (Exception e) {
            logger.warn(e.getMessage());
        } finally {
            IOUtils.closeQuietly(workbook);
        }
        
        return null;
    }

    /**
     * 复制行
     *
     * @param row
     * @param xssfRow
     * @param workbook
     * @return
     */
    private static XSSFWorkbook copyRow(Row row, XSSFRow xssfRow, IFormulaProcessor formulaProcessor, XSSFWorkbook workbook) {
        CellCopyPolicy cellCopyPolicy = new CellCopyPolicy();
        cellCopyPolicy.setCopyCellStyle(false);
        cellCopyPolicy.setCopyHyperlink(false);

        int cols = Math.min(row.getLastCellNum(),200);
        //设置行高度
        xssfRow.setHeight(row.getHeight());

        if (row instanceof XSSFRow) {
            for (int j = 0; j < cols; j++) {
                XSSFCell xssfCell = xssfRow.createCell(j);
                xssfCell.copyCellFrom(row.getCell(j), cellCopyPolicy);
                XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
                //样式转换
                if (row.getCell(j) != null && row.getCell(j).getCellStyle() != null) {
                    copyCellStyle(row.getCell(j).getCellStyle(), xssfCellStyle);
                    xssfCell.setCellValue(POIUtils.format(row.getCell(j), formulaProcessor));//单元格包含的公式的处理
                }
                if(row.getRowNum()<100){
                    xssfCell.setCellStyle(xssfCellStyle);
                }
            }
        } else if (row instanceof HSSFRow) {
            for (int j = 0; j < cols; j++) {
                XSSFCell xssfCell = xssfRow.createCell(j);
                
                //样式转换
                XSSFCellStyle xssfCellStyle = workbook.createCellStyle();
                if (row.getCell(j) != null && row.getCell(j).getCellStyle() != null) {
                    copyCellStyle(row.getCell(j).getCellStyle(), xssfCellStyle);
                    //设置数据类型单元格的格式
//                    if (row.getCell(j).getCellTypeEnum() == CellType.NUMERIC) {
                    xssfCellStyle.setDataFormat(0);//设置格式为通用格式
//                    }
                    xssfCell.setCellValue(POIUtils.format(row.getCell(j), formulaProcessor));//单元格包含的公式的处理
                }else{
                	xssfCell.copyCellFrom(row.getCell(j), cellCopyPolicy);
                }

                xssfCell.setCellStyle(xssfCellStyle);
            }
        }

        return workbook;
    }
    
    /**
     * 复制一个单元格样式到目的单元格样式 （poi自带的copystyle在不同work之间的复制报错）
     *
     * @param fromStyle
     * @param toStyle
     */
    public static void copyCellStyle(CellStyle fromStyle,
                                     CellStyle toStyle) {
        toStyle.setAlignment(fromStyle.getAlignmentEnum());
        toStyle.setBorderBottom(fromStyle.getBorderBottomEnum());
        toStyle.setBorderLeft(fromStyle.getBorderLeftEnum());
        toStyle.setBorderRight(fromStyle.getBorderRightEnum());
        toStyle.setBorderTop(fromStyle.getBorderTopEnum());
        toStyle.setTopBorderColor(fromStyle.getTopBorderColor());
        toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
        toStyle.setRightBorderColor(fromStyle.getRightBorderColor());
        toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());

        //背景和前景   
        toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());
        toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());

        toStyle.setDataFormat(fromStyle.getDataFormat());
        toStyle.setFillPattern(fromStyle.getFillPatternEnum());
        //toStyle.setFont(Workbook.getFontAt(fromStyle.getFontIndex()));  
        toStyle.setHidden(fromStyle.getHidden());
        toStyle.setIndention(fromStyle.getIndention());//首行缩进  
        toStyle.setLocked(fromStyle.getLocked());
        toStyle.setRotation(fromStyle.getRotation());//旋转  
        toStyle.setVerticalAlignment(fromStyle.getVerticalAlignmentEnum());
        toStyle.setWrapText(fromStyle.getWrapText());

    }

    /**
     * 功能：复制原有sheet的合并单元格到新创建的sheet
     *
     * @param targetSheet
     * @param sourceSheet
     */
    public static void mergerRegion(Sheet targetSheet, Sheet sourceSheet) throws Exception {
        if (targetSheet == null || sourceSheet == null) {
            throw new IllegalArgumentException("targetSheet或者sourceSheet不能为空，抛出该异常！");
        }

        for (int i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
            CellRangeAddress oldRange = sourceSheet.getMergedRegion(i);
            CellRangeAddress newRange = new CellRangeAddress(
                    oldRange.getFirstRow(), oldRange.getLastRow(),
                    oldRange.getFirstColumn(), oldRange.getLastColumn());
            targetSheet.addMergedRegion(newRange);
        }
    }

    /**
     * @param output
     * @param sourceSheet
     * @param rows
     * @param cols
     * @param cellProcessor
     */
    public static void createNewSheet(OutputStream output, Sheet sourceSheet, int rows, int cols, ICellProcessor cellProcessor) {
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook();

            IFormulaProcessor formulaProcessor = FormulaProcessorFactory.getInstance().createFormulaProcessor(sourceSheet.getWorkbook());

            XSSFSheet copySheet = workbook.createSheet();

            for (int i = 0; i < rows; i++) {
                XSSFRow copyRow = copySheet.createRow(i);
                Row row = sourceSheet.getRow(i);
                for (int j = 0; j < cols; j++) {
                    XSSFCell copyCell = copyRow.createCell(j);
                    Cell sourceCell = null;
                    if (row != null) {
                        sourceCell = row.getCell(j);
                        if (sourceCell != null) {
                            copyCell.setCellValue(POIUtils.format(sourceCell, formulaProcessor));
                        }
                    }
                    cellProcessor.processCell(copyCell, i, j);
                }
            }
            workbook.write(output);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        } finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(workbook);
        }
    }

    /**
     * 创建新的Excel sheet页
     *
     * @param output        输出流
     * @param rows          sheet页行数
     * @param cols          sheet页列数
     * @param cellProcessor 单元格处理器
     */
    public static void createNewSheet(OutputStream output, int rows, int cols, ICellProcessor cellProcessor) {
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook();
            XSSFSheet newSheet = workbook.createSheet();

            for (int i = 0; i < rows; i++) {
                // 创建行
                XSSFRow row = newSheet.createRow(i);
                for (int j = 0;j < cols;j++) {
                    // 创建单元格
                    XSSFCell cell = row.createCell(j);
                    cellProcessor.processCell(cell,i,j);
                }
            }
            workbook.write(output);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        } finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(workbook);
        }
    }

    public static XSSFCellStyle buildTitleStyle(XSSFWorkbook xwb){
        XSSFCellStyle titleCellStyle = xwb.createCellStyle();
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont font = xwb.createFont();
        font.setFontHeightInPoints((short) 18);//设置字体大小   
        font.setBold(true);//粗体显示  
        titleCellStyle.setFont(font);
        addBorder(titleCellStyle);
        return titleCellStyle;
    }

    public static XSSFCellStyle buildContentStyle(XSSFWorkbook xwb){
        XSSFCellStyle contentStyle = xwb.createCellStyle();
        contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentStyle.setWrapText(true);
        addBorder(contentStyle);
        return contentStyle;
    }

    public static void addBorder(XSSFCellStyle cellStyle){
        cellStyle.setBorderBottom(BorderStyle.THIN); // 底部边框
        cellStyle.setBorderLeft(BorderStyle.THIN);  // 左边边框
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
    }
}
