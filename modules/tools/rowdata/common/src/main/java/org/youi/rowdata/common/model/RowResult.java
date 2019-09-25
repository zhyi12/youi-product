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
package org.youi.rowdata.common.model;

import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class RowResult {

    private int rowIndex;

    private String[] rowData;

    private List<String> headers;

    public String[] getRowData() {
        return rowData;
    }

    public void setRowData(String[] rowData) {
        this.rowData = rowData;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public synchronized void addCell(String cellValue,int colIndex) {
        if(rowData==null){
            rowData = new String[]{};
        }

        if(colIndex>rowData.length){
            //补充空单元格
            int currentLen = rowData.length;
            for(int i=0;i<colIndex-currentLen;i++){
                rowData = StringUtils.addStringToArray(rowData,"");
            }
        }

        rowData = StringUtils.addStringToArray(rowData,cellValue);
    }
}
