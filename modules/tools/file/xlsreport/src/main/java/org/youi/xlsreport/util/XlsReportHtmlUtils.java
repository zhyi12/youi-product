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

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.youi.framework.core.dataobj.tree.HtmlTreeNode;
import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.xlsreport.model.XlsArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class XlsReportHtmlUtils {

    private static final String  SLAVE_TEXT_SPLIT= "/";//分隔符

    private static final String  SLAVE_TEXT_SPLIT_REPLACE= "|^|";//分隔符的转义字符

    /**
     * 还原分隔符为/
     * @param text
     * @return
     */
    public static String restoreBySplitText(String text){
        return  text.replace(SLAVE_TEXT_SPLIT_REPLACE,SLAVE_TEXT_SPLIT);
    }

    /**
     * 根据文本树生成单列宾栏的html
     * @param treePaths
     * @param treeNodes
     * @param maxLevel
     * @return
     */
    public static List<String> buildRowInnerForOneColumnHtmls(List<String> treePaths, List<TreeNode> treeNodes, int maxLevel){
        List<String> rowHtmls = new ArrayList<>();
        processOneColumnTreeNodes(rowHtmls,treePaths,treeNodes,maxLevel);
        return rowHtmls;
    }

    /**
     * 根据文本树生成多列宾栏的html
     * @param treePaths
     * @param treeNodes
     * @param maxLevel
     * @return
     */
    public static List<String> buildRowInnerForMultiColumnHtmls(List<String> treePaths, List<TreeNode> treeNodes, int maxLevel){
        List<String> rowHtmls = new ArrayList<>();
        Map<Integer,XlsArea> levelMerges = new HashMap<>();
        Map<String,XlsArea> mergedAreas = new HashMap<>();
        processTreeNodes(rowHtmls,treePaths,treeNodes,maxLevel,levelMerges,mergedAreas);
        return rowHtmls;
    }

    /**
     *
     * @param rowHtmls
     * @param treePaths
     * @param treeNodes
     * @param maxLevel
     */
    private static void processOneColumnTreeNodes(List<String> rowHtmls, List<String> treePaths, List<TreeNode> treeNodes, int maxLevel) {
        for(TreeNode treeNode:treeNodes) {
            if(isItemTreeNode(treeNode, maxLevel)){
                rowHtmls.add(buildOneColumnHtml(treeNode));
            }
            if(!CollectionUtils.isEmpty(treeNode.getChildren())){
                processOneColumnTreeNodes(rowHtmls,treePaths,treeNode.getChildren(),maxLevel);
            }
        }
    }

    /**
     *
     * @param treeNode
     * @return
     */
    private static String buildOneColumnHtml(TreeNode treeNode) {
        List<String> styleClasses = new ArrayList<>();
        styleClasses.add("cell");

        String styleHtml = "";
        if(treeNode.getLevel()>1){
            styleHtml = " style=\"text-indent: "+(treeNode.getLevel()-1)*16+"px;\" ";
        }
        return "<td "+styleHtml+" class=\""+StringUtils.arrayToDelimitedString(styleClasses.toArray(new String[0])," ")+"\">"+treeNode.getText()+"</td>";
    }

    /**
     * 遍历树，生成行的内部html
     * @param rowHtmls
     * @param treePaths
     * @param treeNodes
     * @param maxLevel
     * @param levelMerges
     * @param mergedAreas
     */
    private static void processTreeNodes(List<String> rowHtmls, List<String> treePaths,
                                         List<TreeNode> treeNodes, int maxLevel,Map<Integer,XlsArea> levelMerges,Map<String,XlsArea> mergedAreas) {
        int rowIndex = 0;
        for(TreeNode treeNode:treeNodes){
            boolean isItemTreeNode = isItemTreeNode(treeNode,maxLevel);
            if(isItemTreeNode){
                rowHtmls.add(buildRowHtml(treeNode,maxLevel,rowIndex,levelMerges,mergedAreas));
            }

            if(!CollectionUtils.isEmpty(treeNode.getChildren())){
                XlsArea area = new XlsArea();
                area.setMergeText(treeNode.getText());
                int mergedRows = calculateRows(treeNode,treePaths);
                if(isItemTreeNode){
                    mergedRows--;
                    area.setMergeText("");//当前节点本身也是item节点时，合并单元格不显示文本
                }
                area.setRows(mergedRows);
                area.setMergeId("m_"+Math.abs(treeNode.getId().hashCode()));
                levelMerges.put(treeNode.getLevel(),area);
                processTreeNodes(rowHtmls,treePaths,treeNode.getChildren(),maxLevel,levelMerges,mergedAreas);

                //清除当前节点的行合并信息
                levelMerges.put(treeNode.getLevel(),new XlsArea());
            }
            rowIndex++;
        }
    }

    /**
     * 计算合并单元格数量
     * @param treeNode
     * @param treePaths
     * @return
     */
    private static int calculateRows(TreeNode treeNode, List<String> treePaths) {
        int count = 0;
        String fullPath = parseFullPath(treeNode);
        for(String treePath:treePaths){
            if(treePath.startsWith(fullPath)){
                count++;
            }
        }
        return count;
    }

    /**
     *
     * @param treeNode
     * @return
     */
    private static String parseFullPath(TreeNode treeNode) {
        String treeFullPath = treeNode.getId();
        if(treeFullPath.endsWith("/")){
            treeFullPath = treeFullPath.substring(0,treeFullPath.length()-1);
        }
        return treeFullPath;
    }

    /**
     *
     * @param treeNode
     * @param maxLevel
     * @param rowIndex
     * @param levelMerges
     * @param mergedAreas
     * @return
     */
    private static String buildRowHtml(TreeNode treeNode, int maxLevel,int rowIndex,Map<Integer,XlsArea> levelMerges,Map<String,XlsArea>  mergedAreas) {
        StringBuilder htmls = new StringBuilder("\n");
        int level = treeNode.getLevel();
        String[] texts = treeNode.getId().split("/");

        for(int i=1;i<=level;i++){
            String text = texts[i];
            List<String> styles = new ArrayList<>();
            styles.add("cell");

            htmls.append("<td ");
            if(levelMerges.containsKey(i)&&levelMerges.get(i).getRows()>1){//处理合并单元格
                styles.add("merged");
                XlsArea area = levelMerges.get(i);
                String mergeId = area.getMergeId();
                if(isFirstCellInMerge(mergeId,rowIndex,mergedAreas)){
                    htmls.append(" id=\""+mergeId+"\" ").append(" rowspan=\""+area.getRows()+"\" ");
                    mergedAreas.put(mergeId,area);
                    if(org.youi.framework.util.StringUtils.isEmpty(area.getMergeText())){
                        text = "";
                    }
                }else{
                    styles.add("hide");
                    htmls.append(" style=\"display:none;\" data-merge-id=\""+mergeId+"\" ");
                }
            }

            htmls.append(" class=\"").append(StringUtils.arrayToDelimitedString(styles.toArray(new String[0])," ")).append("\" ");

            if(i==level&&maxLevel>level){
                htmls.append(" colspan=\"").append(maxLevel-level+1).append("\">").append(text);
            }else{
                htmls.append(">").append(text);
            }

            htmls.append("</td>");
        }

        //补充隐藏的单元格
        if(level<maxLevel){
            for(int j=level;j<maxLevel;j++){
                htmls.append("<td class=\"cell\" style=\"display:none;\"></td>");
            }
        }

        return htmls.toString();
    }

    /**
     * 判断是否为第一个合并的单元格
     * @param mergeId
     * @param rowIndex
     * @param mergedAreas
     * @return
     */
    private static boolean isFirstCellInMerge(String mergeId, int rowIndex,Map<String,XlsArea>  mergedAreas) {
        if(rowIndex>0){
            return false;
        }
        if(mergedAreas.containsKey(mergeId)){
            return false;
        }
        return true;
    }

    /**
     * 判断是否为item节点（占行）
     * @param treeNode
     * @param maxLevel
     * @return
     */
    private static boolean isItemTreeNode(TreeNode treeNode,int maxLevel){
        return treeNode instanceof HtmlTreeNode && ((HtmlTreeNode) treeNode).getGroup().equals("item");
    }
}
