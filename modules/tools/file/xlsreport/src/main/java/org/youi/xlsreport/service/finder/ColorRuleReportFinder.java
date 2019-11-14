package org.youi.xlsreport.service.finder;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.youi.framework.core.dataobj.cube.Area;
import org.youi.xlsreport.model.XlsReport;
import org.youi.xlsreport.service.IReportFinderRuleAdapter;

import java.util.Iterator;

/**

 */

@Component
@Order(0)
public class ColorRuleReportFinder implements IReportFinderRuleAdapter {
    @Override
    public XlsReport parseReport(Sheet sheet) {

        Iterator<Row> rows = sheet.iterator();

        Area bodyArea = new Area();
        Area enMainArea = new Area();
        Area dataArea = new Area();

        int mainStartRow = 0,mainEndCol = 0,prevRowMaxHeaderCol = -1;
        while(rows.hasNext()){
            Row row = rows.next();
            String[] rowData = new String[row.getLastCellNum() - row.getFirstCellNum()];

            int rowMaxHeaderCol = -1;
            for(int i=row.getFirstCellNum();i<row.getLastCellNum();i++){
                Cell cell = row.getCell(i);
                if(cell!=null){
                    if(cell.getCellStyle() instanceof XSSFCellStyle){
                        XSSFCellStyle cs = (XSSFCellStyle) cell.getCellStyle();
                        //
                    }else  if(cell.getCellStyle() instanceof HSSFCellStyle){
                        //如果年鉴都是2003-2007 格式
                        HSSFCellStyle cs = (HSSFCellStyle) cell.getCellStyle();

                        if(cs.getFillForegroundColor()==43){
                            //头区域
                            bodyArea = processArea(bodyArea,row.getRowNum(),i);
                            rowMaxHeaderCol = Math.max(rowMaxHeaderCol,i);//
                        }else if(cs.getFillForegroundColor()==41){
                            //英文区域
                            if(bodyArea.getEndCol()>0&&i>bodyArea.getEndCol()){
                                continue;
                            }
                            enMainArea =processArea(enMainArea,row.getRowNum(),i);
                        }
                    }
                }
            }

            //寻找主栏开始行
            if(mainStartRow==0&&prevRowMaxHeaderCol>-1
                    &&rowMaxHeaderCol>-1&&prevRowMaxHeaderCol!=rowMaxHeaderCol){
                mainStartRow = row.getRowNum();
                mainEndCol = rowMaxHeaderCol;
            }

            prevRowMaxHeaderCol = rowMaxHeaderCol;
        }

        if(enMainArea.getEndCol()>0){
            //有英文区域
            dataArea.setStartRow(enMainArea.getStartRow());
            dataArea.setEndRow(enMainArea.getEndRow());
            dataArea.setStartCol(enMainArea.getEndCol()+1);
            dataArea.setEndCol(bodyArea.getEndCol());

            mainEndCol = enMainArea.getEndCol() - 1 ;
        }else{//没有英文区域
            dataArea.setStartRow(mainStartRow);
            dataArea.setEndRow(bodyArea.getEndRow());
            dataArea.setStartCol(mainEndCol+1);//默认为第一列
            dataArea.setEndCol(bodyArea.getEndCol());
        }

        //判断是否找到区域
        if(bodyArea.getEndCol()>0&&mainEndCol>=0&&
                dataArea.getEndCol()>0&&dataArea.getEndRow()>0){
            XlsReport reportAreaVO = new XlsReport();

            reportAreaVO.setBodyArea(bodyArea);
            reportAreaVO.setDataArea(dataArea);
            reportAreaVO.setMainEndCol(mainEndCol);

            return reportAreaVO;
        }

        return null;
    }

    /**
     *
     * @param area
     * @param rowNum
     * @param i
     * @return
     */
    private Area processArea(Area area, int rowNum, int i) {
        int areaRowStart = area.getStartRow(),//区域行开始
                areaRowEnd = area.getEndRow(),//区域行结束
                areaColStart = area.getStartCol(),//区域列开始
                areaColEnd = area.getEndCol();//区域列结束

        if(area.getStartRow()==0){
            areaRowStart = rowNum;
        }else {
            areaRowStart = Math.min(areaRowStart,rowNum);
        }

        if(areaRowEnd==0){
            areaRowEnd = rowNum;
        }else {
            areaRowEnd = Math.max(areaRowEnd,rowNum);
        }

        if(areaColStart==0){
            areaColStart = i;
        }else {
            areaColStart = Math.min(areaRowStart,i);
        }

        if(areaColEnd==0){
            areaColEnd = i;
        }else {
            areaColEnd = Math.max(areaColEnd,i);
        }

        Area result = new Area();

        result.setStartRow(areaRowStart);
        result.setEndRow(areaRowEnd);
        result.setStartCol(areaColStart);
        result.setEndCol(areaColEnd);

        return result;
    }
}
