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

import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.cube.Area;
import org.youi.framework.core.dataobj.cube.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class XlsReport implements Domain{

    private String title;//

    private String sheetName;//xls标签名称

    private Area bodyArea;//全部区域

    private Area dataArea;//数据区域

    private int mainEndCol;//主栏结束区域，主栏和数据区域中间允许有英文、编码或者空白列

    private String reportType;//1、主栏是指标/宾栏是属性 2、主栏是地区/宾栏是属性 3、主栏是地区/宾栏是指标+属性

    private Item headIndicatorItem;//头指标

    private List<Item> mainItems;//主栏项

    private List<Item> slaveItems;//宾栏项

    private List<Item> slaveIndicatorItems;//宾栏指标项

    private String[][] datas;//数据

    public Area getBodyArea() {
        return bodyArea;
    }

    public void setBodyArea(Area bodyArea) {
        this.bodyArea = bodyArea;
    }

    public Area getDataArea() {
        return dataArea;
    }

    public void setDataArea(Area dataArea) {
        this.dataArea = dataArea;
    }

    public int getMainEndCol() {
        return mainEndCol;
    }

    public void setMainEndCol(int mainEndCol) {
        this.mainEndCol = mainEndCol;
    }

    public List<Item> getMainItems() {
        return mainItems;
    }

    public void setMainItems(List<Item> mainItems) {
        this.mainItems = mainItems;
    }

    public List<Item> getSlaveItems() {
        return slaveItems;
    }

    public void setSlaveItems(List<Item> slaveItems) {
        this.slaveItems = slaveItems;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[][] getDatas() {
        return datas;
    }

    public void setDatas(String[][] datas) {
        this.datas = datas;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Item getHeadIndicatorItem() {
        return headIndicatorItem;
    }

    public void setHeadIndicatorItem(Item headIndicatorItem) {
        this.headIndicatorItem = headIndicatorItem;
    }

    public List<Item> getSlaveIndicatorItems() {
        return slaveIndicatorItems;
    }

    public void setSlaveIndicatorItems(List<Item> slaveIndicatorItems) {
        this.slaveIndicatorItems = slaveIndicatorItems;
    }

    public void addMainItem(String itemId, String itemText, int level) {
        if(mainItems==null){
            mainItems = new ArrayList<Item>();
        }
        Item item = new Item(itemId,itemText.trim());
        item.setLevel(level);
        mainItems.add(item);
    }

    public void addSlaveItem(String itemId, String itemText, int level) {
        if(slaveItems==null){
            slaveItems = new ArrayList<Item>();
        }
        Item item = new Item(itemId,itemText.trim());
        item.setLevel(level);
        slaveItems.add(item);
    }
}
