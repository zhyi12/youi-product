/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.xlsreport.service.impl;

import com.gwssi.xls.POIUtils;
import com.gwssi.xls.SheetUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.xlsreport.model.XlsReport;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */

@Component
public class Xls101ReportFinder {

    private final static short COLOR_ITEM_PREV_TEXT = 43;

    private final static short COLOR_ITEM_NEXT_TEXT = 53;

    public XlsReport read101Report(InputStream inputStream) {

        final XlsReport xlsReport = new XlsReport();

        SheetUtils.processSheets(inputStream, sheet -> {
            processXlsReportSheet(sheet, xlsReport);
            return xlsReport;
        }, 1);

        return xlsReport;
    }

    /**
     *
     * @param sheet
     * @param xlsReport
     */
    private void processXlsReportSheet(Sheet sheet, XlsReport xlsReport) {
        xlsReport.setSheetName(sheet.getSheetName());
        Iterator<Row> rowIterator = sheet.rowIterator();


        List<Item>  mainItems = new ArrayList<>();
        int index = 0;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            int rowNum = row.getRowNum();

            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = findItemCell(sheet,cellIterator.next());
                if(cell!=null&&cell.getCellStyle()!=null){
                    //43  itemText 可以从前一个单元格找到
                    //53  itemText 可以从后一个单元格找到
                    //49 不需要找单元格
                    short cellBgColor = cell.getCellStyle().getFillForegroundColor();

                    System.out.println(POIUtils.format(cell,null)+": "+cellBgColor);
                    if(cellBgColor==COLOR_ITEM_PREV_TEXT||cellBgColor==49||cellBgColor==COLOR_ITEM_NEXT_TEXT){
                        Item item = new Item();
                        item.setId("COL"+StringUtils.leftPad(Integer.toString(++index),3,"0"));
                        item.setText(findItemText(sheet,cell,cellBgColor));
                        item.setMappedId("cells["+cell.getRowIndex()+"]["+cell.getColumnIndex()+"]");
                        mainItems.add(item);
                    }
                }
            }
        }
        xlsReport.setMainItems(mainItems);
    }

    /**
     *
     * @param sheet
     * @param cell
     * @return
     */
    private Cell findItemCell(Sheet sheet, Cell cell) {
        //合并单元格
        List<CellRangeAddress> cellRangeAddresses = sheet.getMergedRegions();

        if(!CollectionUtils.isEmpty(cellRangeAddresses)){
            for(CellRangeAddress cellRangeAddress:cellRangeAddresses){
                if(isHideCellInMerge(cell,cellRangeAddress)){
                    return null;
                }
            }
        }
        return cell;
    }

    /**
     *
     * @param cell
     * @param cellRangeAddress
     * @return
     */
    private boolean isHideCellInMerge(Cell cell,CellRangeAddress cellRangeAddress){
        return cellRangeAddress.isInRange(cell.getRowIndex(),cell.getColumnIndex())
                &&(cellRangeAddress.getFirstRow()!=cell.getRowIndex()||cellRangeAddress.getFirstColumn()!=cell.getColumnIndex());
    }

    /**
     *
     * @param sheet
     * @param cellBgColor
     * @param cell
     * @return
     */
    private String findItemText(Sheet sheet, Cell cell, short cellBgColor) {
        String text = null;
        List<CellRangeAddress> cellRangeAddresses = sheet.getMergedRegions();

        int rowNum = cell.getRowIndex(),
            columnIndex = cell.getColumnIndex();

        if(cellBgColor==COLOR_ITEM_PREV_TEXT){
            Cell prevCell = sheet.getRow(rowNum).getCell(columnIndex-1);
            if(!CollectionUtils.isEmpty(cellRangeAddresses)){
                //判断是否在合并单元格内
                for(CellRangeAddress cellRangeAddress:cellRangeAddresses){
                    if(cellRangeAddress.isInRange(prevCell.getRowIndex(),prevCell.getColumnIndex())){
                        prevCell = sheet.getRow(cellRangeAddress.getFirstRow()).getCell(cellRangeAddress.getFirstColumn());
                        break;
                    }
                }
            }
            text= POIUtils.format(prevCell,null);
        }else if(cellBgColor==COLOR_ITEM_NEXT_TEXT){
            Cell nextCell = cell.getRow().getCell(cell.getColumnIndex()+findNextCellIndex(sheet,cell)) ;
            text = POIUtils.format(nextCell,null);
        }

        return StringUtils.isEmpty(text)?"CELL["+rowNum+"]["+columnIndex+"]":text;
    }


    /**
     *
     * @param sheet
     * @param cell
     * @return
     */
    private int findNextCellIndex(Sheet sheet,Cell cell){
        List<CellRangeAddress> cellRangeAddresses = sheet.getMergedRegions();
        if(!CollectionUtils.isEmpty(cellRangeAddresses)){
            //判断是否在合并单元格内
            for(CellRangeAddress cellRangeAddress:cellRangeAddresses){
                if(cellRangeAddress.isInRange(cell.getRowIndex(),cell.getColumnIndex())){
                    return cellRangeAddress.getLastColumn() - cellRangeAddress.getFirstColumn()+1;
                }
            }
        }
        return 1;
    }

}
