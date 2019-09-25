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
package org.youi.rowdata.xls.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.youi.rowdata.common.model.RowResult;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class XlsRowFileUtils {

    private final static Logger logger = LoggerFactory.getLogger(XlsRowFileUtils.class);


    /**
     * 1、index==-1时遍历全部sheet页
     * 2、index为指定值，并且
     * @param inputStream
     * @param index
     * @param rowReaders
     */
    public static void processSheets(InputStream inputStream,int index,List<Function<RowResult,Integer>> rowReaders){
        OPCPackage pkg = null;
        try {
            pkg = OPCPackage.open(inputStream);
            XSSFReader xssfReader = new XSSFReader(pkg);
            Iterator<InputStream> sheetsData =  xssfReader.getSheetsData();

            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
            StylesTable styles = xssfReader.getStylesTable();

            int curIndex = 0;
            while(sheetsData.hasNext()){
                if(index==-1&&curIndex<rowReaders.size()){//执行全部的sheet
                    processSheet(styles,strings,new RowContentHandler(rowReaders.get(curIndex)),sheetsData.next());
                }else if(index>-1&&index==curIndex){
                    processSheet(styles,strings,new RowContentHandler(rowReaders.get(0)),sheetsData.next());
                    break;
                }else if(curIndex>rowReaders.size()) {
                    break;
                }
                curIndex++;
            }
        } catch (InvalidFormatException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (OpenXML4JException e) {
            logger.error(e.getMessage());
        } catch (SAXException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(pkg);
        }
    }
    /**
     *
     * @param inputStream
     * @param rowReader
     */
    public static void precessRow(InputStream inputStream, Function<RowResult,Integer> rowReader){
        List<Function<RowResult,Integer>> rowReaders = new ArrayList<>();
        rowReaders.add(rowReader);
        processSheets(inputStream,0,rowReaders);
    }

    public static String[] readerHeader(InputStream inputStream){
        OPCPackage pkg = null;
        List<String> headers = new ArrayList<>();
        try {
            pkg = OPCPackage.open(inputStream);
            XSSFReader xssfReader = new XSSFReader(pkg);
            Iterator<InputStream> sheetsData =  xssfReader.getSheetsData();

            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
            StylesTable styles = xssfReader.getStylesTable();

            if(sheetsData.hasNext()){
                RowContentHandler rowContentHandler = new RowContentHandler((rowResult)->{
                    if(rowResult!=null&&!ArrayUtils.isEmpty(rowResult.getRowData())){
                        headers.addAll(CollectionUtils.arrayToList(rowResult.getRowData()));
                    }
                    return rowResult.getRowIndex();
                });
                rowContentHandler.setMaxRow(1);
                //只执行第一个xls sheet
                processSheet(styles,strings,rowContentHandler,sheetsData.next());
            }
        } catch (InvalidFormatException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (OpenXML4JException e) {
            logger.error(e.getMessage());
        } catch (SAXException e) {
            logger.error(e.getMessage());
        }finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(pkg);
        }

        return headers.toArray(new String[0]);
    }

    /**
     *
     * @param styles
     * @param strings
     * @param sheetHandler
     * @param sheetInputStream
     */
    private static void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, XSSFSheetXMLHandler.SheetContentsHandler sheetHandler, InputStream sheetInputStream) {

        InputSource sheetSource = new InputSource(sheetInputStream);

        try {
            XMLReader sheetParser = SAXHelper.newXMLReader();
            DataFormatter formatter = new DataFormatter();
            ContentHandler handler = new XSSFSheetXMLHandler(styles, null, strings, sheetHandler, formatter, false);
            sheetParser.setContentHandler(handler);
            sheetParser.parse(sheetSource);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("SAX parser appears to be broken - " + e);
        } catch (SAXException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     *
     * @param
     */
    private static class RowContentHandler implements XSSFSheetXMLHandler.SheetContentsHandler{

        private Function<RowResult, Integer> rowReader;

        private RowResult rowResult;

        private int maxRow = -1;

        public RowContentHandler(Function<RowResult, Integer> rowReader) {
            this.rowReader = rowReader;
        }

        public int getMaxRow() {
            return maxRow;
        }

        public void setMaxRow(int maxRow) {
            this.maxRow = maxRow;
        }

        @Override
        public void startRow(int rowNum) {
            if(maxRow==-1||rowNum<maxRow){
                this.rowResult = new RowResult();
                this.rowResult.setRowIndex(rowNum);
            }
        }

        @Override
        public void endRow(int rowNum) {
            if(maxRow==-1||rowNum<maxRow){
                rowReader.apply(rowResult);
                rowResult = null;
            }
        }

        @Override
        public void cell(String cellReference, String formattedValue, XSSFComment comment) {
            if(rowResult!=null){
                int columnIndex = excelColStrToNum(cellReference.replaceAll("[0-9]*","")) - 1;
                rowResult.addCell(formattedValue,columnIndex);
            }
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {

        }
    }

    /**
     * xls列字符转序号
     * @param colStr
     * @return
     */
    public static int excelColStrToNum(String colStr) {
        int length = colStr.length();
        int num = 0;
        int result = 0;
        for(int i = 0; i < length; i++) {
            char ch = colStr.charAt(length - i - 1);
            num = (int)(ch - 'A' + 1) ;
            num *= Math.pow(26, i);
            result += num;
        }
        return result;
    }
}
