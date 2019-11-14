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
package org.youi.xlsreport.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.CollectionUtils;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.core.dataobj.tree.HtmlTreeNode;
import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.util.StringUtils;
import org.youi.xlsreport.model.XlsReport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public final class XlsReportUtils {

    /**
     * 私有构造函数
     */
    private XlsReportUtils(){
        //ignore
    }

    /**
     *
     * @param items
     * @return
     */
    public static TreeNode orderedItemsToTree(String idPrefix,List<Item> items){
        TreeNode root = new HtmlTreeNode("ROOT","");
        //通过有序的level构造树结构
        TreeNode parent = null;
        Map<Integer,TreeNode> treeNodeTraces = new HashMap<>();
        for(Item item:items){
            TreeNode itemTreeNode = new HtmlTreeNode(idPrefix+item.getId(),item.getText());
            if(item.getLevel()==0){
                parent = root;
            }else {
                parent = treeNodeTraces.get(item.getLevel()-1);
            }

            if(parent==null){
                parent = root;
            }
            parent.addChild(itemTreeNode);
            treeNodeTraces.put(item.getLevel(),itemTreeNode);
        }
        return root;
    }

    public static TreeNode orderedItemsToTree(List<Item> items){
        return orderedItemsToTree("",items);
    }

    /**
     * excel文件转换为html
     *
     * @param file excel文件对象
     * @return html
     */
    public static  String convert2Html(File file){
        return convert2Html(file,0);
    }

    /**
     * excel文件转换为html，指定要转换的sheet页
     *
     * @param file excel文件对象
     * @return html
     */
    public static String convert2Html(File file, int sheetNo){
        try(InputStream inputStream = new FileInputStream(file)){
            return convert2Html(inputStream, sheetNo);
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }

    public static  String convert2Html(InputStream inputStream) throws IOException{
        return convert2Html(inputStream,0);
    }

    public static  String convert2Html(InputStream inputStream, int sheetNo) throws IOException {
        try{
            StringBuilder builder = new StringBuilder();
            Excel2HtmlConverter excel2HtmlConverter = Excel2HtmlConverter.create(inputStream,builder);
            excel2HtmlConverter.printPage(sheetNo);
            return  builder.toString();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            inputStream.close();
        }
        return "";
    }
    
    /**
     *  TODO 迁移到工具类
     * @param xlsReports
     * @return
     */
    public static List<TreeNode> xlsReportToTreeNodes(List<XlsReport> xlsReports) {
        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        if(!CollectionUtils.isEmpty(xlsReports)){
            int index = 0;
            for(XlsReport xlsReport:xlsReports){
                if(canBuildTreeNodes(xlsReport)){
                    treeNodes.addAll(XlsReportUtils.orderedItemsToTree("R"+index++,xlsReport.getMainItems()).getChildren());
                }
            }
        }
        return treeNodes;
    }
    
    /**
    *
    * @param xlsReport
    * @return
    */
   private static boolean canBuildTreeNodes(XlsReport xlsReport){
       if(StringUtils.isNotEmpty(xlsReport.getTitle())){
           String title = xlsReport.getTitle().trim();
           //TODO 增加过滤规则正则表达式配置
           if(title.startsWith("分市县")){//
               return false;
           }
       }
       return !CollectionUtils.isEmpty(xlsReport.getMainItems());
   }

    /**
     * 判断是否为合并的单元格
     * @param sheet
     * @param cell
     * @return
     */
   public static boolean isInRangeCell(Sheet sheet,Cell cell){
       for(CellRangeAddress cellRangeAddress:sheet.getMergedRegions()){
           if(cellRangeAddress.isInRange(cell.getRowIndex(),cell.getColumnIndex())){
               return true;
           }
       }
       return false;
   }
}
