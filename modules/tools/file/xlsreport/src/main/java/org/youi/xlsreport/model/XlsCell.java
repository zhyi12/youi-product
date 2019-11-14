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
package org.youi.xlsreport.model;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class XlsCell {

    private String text;

    private int rowspan;

    private int colspan;

    private boolean lastRow;    // 最后一行单元格 加下边框

    private boolean lastCol;    // 最后一列单元格 加右边框

    private boolean mainBlock;  // 主栏单元格 加全边框

    private boolean salveBlock;  // 宾栏单元格 字体14，全边框


    /**
     * 解析html中单元格的class文本
     *
     * @param cellClass css class 字符串
     */
    public void parseCellClass(String cellClass) {
        if (cellClass.contains("slave-block")){
            setSalveBlock(true);
        }

        if (cellClass.contains("main-block")){
            setMainBlock(true);
        }

        if (cellClass.contains("last-row")){
            setLastRow(true);
        }

        if (cellClass.contains("last-col")){
            setLastCol(true);
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRowspan() {
        return rowspan;
    }

    public void setRowspan(int rowspan) {
        this.rowspan = rowspan;
    }

    public int getColspan() {
        return colspan;
    }

    public void setColspan(int colspan) {
        this.colspan = colspan;
    }

    public boolean isLastRow() {
        return lastRow;
    }

    public void setLastRow(boolean lastRow) {
        this.lastRow = lastRow;
    }

    public boolean isLastCol() {
        return lastCol;
    }

    public void setLastCol(boolean lastCol) {
        this.lastCol = lastCol;
    }

    public boolean isMainBlock() {
        return mainBlock;
    }

    public void setMainBlock(boolean mainBlock) {
        this.mainBlock = mainBlock;
    }

    public boolean isSalveBlock() {
        return salveBlock;
    }

    public void setSalveBlock(boolean salveBlock) {
        this.salveBlock = salveBlock;
    }
}
