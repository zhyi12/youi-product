package org.youi.xlsreport.service.finder;

import com.gwssi.xls.POIUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.youi.framework.core.dataobj.cube.Area;
import org.youi.framework.util.StringUtils;
import org.youi.xlsreport.model.XlsReport;
import org.youi.xlsreport.service.IReportFinderRuleAdapter;
import org.youi.xlsreport.util.XlsReportUtils;

import java.util.Iterator;

/**
 *
 */
@Component
@Order(1)
public class LineRuleReportFinder implements IReportFinderRuleAdapter {

    protected final Log logger = LogFactory.getLog(this.getClass());
    /**
     *
     * 第一个数字类型并且带上横线的单元格为数据区域第一个单元格
     *
     * @param sheet
     * @return
     */
    @Override
    public XlsReport parseReport(Sheet sheet) {

        Iterator<Row> rows = sheet.rowIterator();

        int bodyRowStart = 0,
                mainRowStart = 0,
                mainRowEnd = 0,
                mainColEnd = -1,
                dataColStart=0,
                bodyEndCol=0,
                addedCount = 10,
                compareBorderCount = addedCount+2;

        boolean skipNext = false;
        while (rows.hasNext()){
            Row row = rows.next();

            if(skipNext){
                skipNext = false;
                continue;
            }

            int rowNum = row.getRowNum();

            int rowBorderBottomCount = 0,rowBorderTopCount = 0,rowBorderLeftCount = 0;

            //计算含有上、下边框的单元格数
            for(int j=row.getFirstCellNum();j<=row.getLastCellNum();j++){
                Cell cell = row.getCell(j);
                if(cell!=null){
                    CellStyle cellStyle = cell.getCellStyle();

                    if(cellStyle.getBorderBottomEnum()!=null
                            &&cellStyle.getBorderBottomEnum().getCode()!=BorderStyle.NONE.getCode()){
                        rowBorderBottomCount+=getCellColspan(sheet,cell);//下边框
                        if(j==row.getFirstCellNum()){
                            rowBorderBottomCount+=addedCount;
                        }
                    }
                    //上边框
                    if(cellStyle.getBorderTopEnum()!=null&&cellStyle.getBorderTopEnum().getCode()!=BorderStyle.NONE.getCode()){
                        rowBorderTopCount+=getCellColspan(sheet,cell);
                        if(j==row.getFirstCellNum()){
                            rowBorderTopCount+=addedCount;
                        }
                    }

                    if(cellStyle.getBorderLeftEnum()!=null&&cellStyle.getBorderLeftEnum().getCode()!=BorderStyle.NONE.getCode()){
                        rowBorderLeftCount++;
                    }
                }
            }

            bodyEndCol = Math.max(bodyEndCol,rowBorderBottomCount - addedCount-1);
            bodyEndCol = Math.max(rowBorderTopCount - addedCount-1,bodyEndCol);

            //System.out.print(rowNum+" rowBorderTopCount:"+rowBorderTopCount);
            //System.out.println("rowBorderBottomCount:"+rowBorderBottomCount);
            //查找主栏结束行
            if(mainRowStart>0&&mainRowEnd==0&&mainRowStart<=rowNum){
                //找到的mainRowStart为代码列时 - isCodeRow
                if(mainRowStart==rowNum-1&&isCodeRow(sheet,mainRowStart)){
                    mainRowStart = rowNum;
                    //
                    if(rowBorderBottomCount!=bodyEndCol+addedCount+1){
                        continue;//下边框不能识别mainRowEnd时，进入下一行处理
                    }
                }
                //开始寻找mainRowEnd,带边的上、下的单元格数等于列数
                if(rowBorderBottomCount==bodyEndCol+addedCount+1){
                    mainRowEnd = rowNum;
                    logger.info("第"+rowNum+"通过下边框找到mainRowEnd");
                    break;//找到主栏结束行后退出
                }else if(rowBorderTopCount==bodyEndCol+addedCount+1&&mainRowStart<rowNum-1){
                    mainRowEnd = rowNum-1;
                    logger.info("第"+rowNum+"通过上边框找到mainRowEnd");
                    break;//找到主栏结束行后退出
                }
            }

            //查找主栏开始行
            if(bodyRowStart>0&&mainRowStart==0){
                if(rowBorderTopCount>=compareBorderCount&&bodyRowStart<rowNum){//通过下边框查找到main开始
                    if(rowBorderLeftCount>2&&rowBorderLeftCount+addedCount>=rowBorderTopCount){
                        continue;//跳过左边框和上边框单元格数相同的
                    }
                    mainRowStart = rowNum;

                    if(mainRowStart>0){
                        logger.info("第"+rowNum+"通过下边框查找到main开始");
                        //如果有下边框
                        if(rowBorderBottomCount==rowBorderTopCount){
                            mainRowEnd = mainRowStart;
                            logger.info("第"+rowNum+"主栏只有一行");
                        }
                    }
                }else if(rowBorderBottomCount>=compareBorderCount){//通过上边框查找到main开始
                    if(rowBorderLeftCount>2&&rowBorderLeftCount+addedCount>=rowBorderTopCount){
                        continue;//跳过左边框和上边框单元格数相同的
                    }
                    mainRowStart = rowNum+1;
                    if(mainRowStart>0){
                        logger.info("第"+rowNum+"通过上边框查找到main开始");
                        //跳过下一行的处理
                        continue;
                    }
                }

                if(bodyRowStart==mainRowStart){
                    mainRowStart = bodyRowStart+1;
                }

            }

            //寻找bodyRowStart
            if(bodyRowStart==0){
                if(rowBorderTopCount>compareBorderCount){//通过上边框查找到body开始
                    bodyRowStart = rowNum;
                    logger.info("第"+rowNum+"通过上边框查找到body开始");
                    //如果有下边框
                    if(rowBorderBottomCount==rowBorderTopCount&&!(rowBorderLeftCount>2&&rowBorderLeftCount+addedCount>=rowBorderTopCount)){
                        mainRowStart = bodyRowStart+getRowspan(sheet,row,bodyEndCol);
                        logger.info("第"+rowNum+"通过下边框查找到主栏从"+mainRowStart+"行开始");
                    }
                }else if(rowBorderBottomCount>compareBorderCount){//通过下边框查找到body开始
                    bodyRowStart = rowNum+1;
                    logger.info("第"+rowNum+"通过下边框查找到body开始");
                }
            }

            if(mainRowStart>0&&mainRowStart<=rowNum&&mainColEnd==-1){
                //识别mainColEnd
                //logger.info("mainColEnd");
                //找到第一列以为的第一个数据单元格,最多找1-5 列
                for(int j=row.getFirstCellNum()+1;j<=Math.min(5,row.getLastCellNum());j++){
                    Cell cell = row.getCell(j);
                    if(cell!=null&&mainColEnd==-1){
                        //跳过合并的单元格
                        if(XlsReportUtils.isInRangeCell(sheet,cell)){
                            continue;
                        }

                        String cellContent =
                                org.springframework.util.StringUtils.trimAllWhitespace(POIUtils.format(cell,null));
                        if(StringUtils.isEmpty(cellContent)||cellContent.matches("([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])")){//空单元格或者数字
                            mainColEnd = j-1;
                            dataColStart = j;
                            logger.info("第"+rowNum+"行找到mainColEnd："+mainColEnd);
                            break;
                        }else if(cellContent.matches("\\w*")){
                            //如果是英文和数字字符的组合，则设置为下一个
                            mainColEnd = j;//设置主栏结束列
                            dataColStart = j+1;
                            logger.info("第"+rowNum+"行找到mainColEnd："+mainColEnd);
                            break;
                        }
                    }
                }
            }
        }

        if(bodyRowStart>0&&mainRowStart>0&&mainColEnd>-1&&mainRowEnd>0){

            XlsReport reportAreaVO = new XlsReport();

            reportAreaVO.setMainEndCol(mainColEnd);

            Area bodyArea = new Area();
            Area dataArea = new Area();

            bodyArea.setStartRow(bodyRowStart);
            bodyArea.setEndRow(mainRowEnd);
            bodyArea.setStartCol(0);
            bodyArea.setEndCol(bodyEndCol);

            dataArea.setStartRow(mainRowStart);
            dataArea.setStartCol(Math.min(dataColStart,bodyEndCol));
            dataArea.setEndRow(mainRowEnd);
            dataArea.setEndCol(bodyEndCol);

            reportAreaVO.setBodyArea(bodyArea);
            reportAreaVO.setDataArea(dataArea);
            reportAreaVO.setMainEndCol(mainColEnd);

            return reportAreaVO;
        }
        return null;
    }

    /**
     *
     * @param sheet
     * @param row
     * @param maxCol
     * @return
     */
    private int getRowspan(Sheet sheet,Row row,int maxCol) {
        int rowspan = 1;
        if(CollectionUtils.isEmpty(sheet.getMergedRegions())){
            return rowspan;
        }

        for(int col=row.getFirstCellNum();col<=maxCol;col++){
            Cell cell = row.getCell(col);

            for(CellRangeAddress cellRangeAddress:sheet.getMergedRegions()){
                if(cellRangeAddress.isInRange(cell.getRowIndex(),cell.getColumnIndex())){
                    //合并起始单元格
                    if(cell.getRowIndex()==cellRangeAddress.getFirstRow()
                            &&cell.getColumnIndex()==cellRangeAddress.getFirstColumn()){
                        rowspan = Math.max(rowspan,cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()+1);
                    }
                }
            }
        }
        return rowspan;
    }

    /**
     * 获取单元格列占位
     * @param sheet
     * @param cell
     * @return
     */
    private int getCellColspan(Sheet sheet, Cell cell) {
        if(CollectionUtils.isEmpty(sheet.getMergedRegions())){
            return 1;
        }

        for(CellRangeAddress cellRangeAddress:sheet.getMergedRegions()){
            if(cellRangeAddress.isInRange(cell.getRowIndex(),cell.getColumnIndex())){
                //合并起始单元格
                if(cell.getRowIndex()==cellRangeAddress.getFirstRow()
                        &&cell.getColumnIndex()==cellRangeAddress.getFirstColumn()){
                    return cellRangeAddress.getLastColumn() - cellRangeAddress.getFirstColumn()+1;
                }else if(cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()>1&&
                        cell.getRowIndex() == cellRangeAddress.getLastRow()){//大于1行的合并区域结束行
                    return 1;
                }else {
                    return 0;
                }
            }
        }
        return 1;
    }

    /**
     * 是否是代码行
     * @return
     */
    private boolean isCodeRow(Sheet sheet,Integer rowNum){
        Row row  = sheet.getRow(rowNum);
        Cell cell = row.getCell(row.getFirstCellNum());
        String cellText = POIUtils.format(cell,null);

        if(StringUtils.trimAllWhitespace(cellText).equals("甲")){
            return true;
        }
        return false;
    }

}
