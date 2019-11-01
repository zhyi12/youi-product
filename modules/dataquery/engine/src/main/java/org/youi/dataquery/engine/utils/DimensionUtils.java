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
package org.youi.dataquery.engine.utils;

import org.springframework.util.CollectionUtils;
import org.youi.dataquery.engine.model.Group;
import org.youi.dataquery.engine.model.ItemTreeNode;
import org.youi.framework.core.dataobj.cube.DataCube;
import org.youi.framework.core.dataobj.cube.Dimension;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.core.dataobj.tree.TreeNode;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public final class DimensionUtils {

    private static NumberFormat numberFormat = DecimalFormat.getInstance();

    static {
        numberFormat.setGroupingUsed(false);
        numberFormat.setMaximumFractionDigits(2);
    }

    /**
     * 私有构造函数
     */
    private DimensionUtils(){
        //ignore
    }

    /**
     * Cube中的数字格式化
     * @param value
     * @return
     */
    public static String format(Object value) {
        if(value==null)return "";

        if(value instanceof Number){
            return numberFormat.format(value);
        }
        return value.toString();
    }

    /**
     * 判断分类项是否为树形结构
     * @param group
     * @return
     */
    public static boolean isTreeGroupItems(Group group){
        if(group!=null && !CollectionUtils.isEmpty(group.getItems())){
            for(Item item:group.getItems()){
                if(item.getLevel()>1){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * 构建维度项的树结构
     * @param treeItems 有序的维度项集合
     * @param maxLevel
     * @return
     */
    public static Map<String,ItemTreeNode> buildItemTreeNodes(List<Item> treeItems, int maxLevel){
        Map<String,ItemTreeNode> treeNodes = new LinkedHashMap();
        ItemTreeNode[] levelParentTreeNodes =  new ItemTreeNode[maxLevel];
        int index = 0;
        for(Item treeItem:treeItems){
            int level = treeItem.getLevel();
            ItemTreeNode treeNode = new ItemTreeNode(treeItem.getId(),treeItem.getText(),index++);

            if(treeItem.getExpression()!=null){
                treeNode.setGroup("expression-item");
            }
            if(level<=1){
                level = 1;
            }else{
                TreeNode parentTreeNode = levelParentTreeNodes[level-2];
                parentTreeNode.addChild(treeNode);
            }
            treeNode.setLevel(level);
            levelParentTreeNodes[level-1] = treeNode;
            treeNodes.put(treeItem.getId(),treeNode);
        }
        return treeNodes;
    }

    /**
     * 一维展开cube的维度
     * @param dimensions
     * @return
     */
    public static List<Item>[] expendedCrossColItems(List<Dimension> dimensions){
        int dimensionCount = dimensions.size();
        int itemCount = 1;
        int spans = 1;
        List<Integer> spansList = new ArrayList<>();

        //计算占位
        for (int i = dimensionCount; i > 0; i--) {
            Dimension dimension = dimensions.get(i-1);
            if (i < dimensionCount) {
                spans = spans * (dimensions.get(i).getItems().size());
            }
            spansList.add(spans);
            itemCount = itemCount * dimension.getItems().size();
        }
        //构建维度项的一维交叉数组，方便数据计算
        List<Item> crossColItems[] = new List[itemCount];
        int index =0;
        double itemBlocks = 1;
        for(Dimension dimension:dimensions){
            //对维度进行笛卡尔组合
            for(int i=0;i<itemCount;i++){
                double itemIndex = (Math.floor(new Integer(i).doubleValue() / spansList.get(dimensionCount - index - 1))) % dimension.getItems().size();
                Item item = dimension.getItems().get(new Double(itemIndex).intValue());
                if(crossColItems[i]==null){
                    crossColItems[i] = new ArrayList<>();
                }
                item.setDimId(dimension.getId());
                crossColItems[i].add(item);
            }
            if(index<dimensionCount-1){
                itemBlocks = itemBlocks*dimension.getItems().size();
            }
            index++;
        }
        return crossColItems;
    }

    /**
     * 查找维度
     * @param dataCube
     * @param dimId
     * @return
     */
    public static Dimension findDimension(DataCube dataCube,String dimId){
        if(dimId!=null && dataCube!=null && dataCube.getDimensions()!=null){
            for(Dimension dimension:dataCube.getDimensions()){
                if(dimId.equals(dimension.getId())){
                    return dimension;
                }
            }
        }
        return null;
    }

}
