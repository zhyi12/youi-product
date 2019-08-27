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
package org.youi.dataquery.engine.core;

import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.CollectionUtils;
import org.youi.dataquery.engine.model.CalculateItem;
import org.youi.dataquery.engine.utils.CalculateItemUtils;
import org.youi.dataquery.engine.utils.DimensionUtils;
import org.youi.framework.core.dataobj.cube.*;
import org.youi.framework.util.StringUtils;

import java.util.*;

import static org.youi.dataquery.engine.DataQueryConstants.*;

/**
 *
 * 立方体计算
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class CubeDataCalculator {


    private  static ExpressionParser parser = new SpelExpressionParser();

    /**
     * 计算维度中的表达式数据
     * @param dataCube
     * @param dimId
     * @return
     */
    public DataCube calculateDimension(DataCube dataCube, String dimId){
        return calculateDimension(dataCube,dimId,null,null);
    }

    /**
     *
     * @param dataCube 立方体
     * @param dimId 计算维度的ID
     * @param calculateItems 计算项
     * @return
     */
    public DataCube calculateDimension(DataCube dataCube, String dimId, List<CalculateItem> calculateItems){
        return calculateDimension(dataCube,dimId,calculateItems,null);
    }

    /**
     *
     * @param dataCube 立方体
     * @param dimId 计算维度的ID
     * @param groupSumValues 分组计算参数
     * @return
     */
    public DataCube calculateDimension(DataCube dataCube, String dimId, Map<String,Double> groupSumValues){
        return calculateDimension(dataCube,dimId,null,groupSumValues);
    }

    /**
     * 清理立方体无用数据
     * @param dataCube
     */
    public void cleanCubeDatas(DataCube dataCube){
        if(dataCube.getDatas()==null)return;
        List<Item>[] crossColItems = DimensionUtils.expendedCrossColItems(dataCube.getDimensions());
        List<String> dataKeys = new ArrayList<>();

        //交叉维度生成data keys集合
        for(List<Item> crossColItem:crossColItems){
            DataItem dataItem = new DataItem();
            List<Item> crossItems = new ArrayList<>(crossColItem);
            if(!CollectionUtils.isEmpty(dataCube.getHeaderItems())){
                crossItems.addAll(dataCube.getHeaderItems());
            }
            for(Item item:crossItems){
                dataItem.addDim(item.getDimId(),item.getId());
            }
            try {
                dataKeys.add(dataItem.buildKey());
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        //可清理的keys集合
        List<String> removingKeys = new ArrayList<>();
        for(String cubeDataKey:dataCube.getDatas().keySet()){
            if(!dataKeys.contains(cubeDataKey)){
                removingKeys.add(cubeDataKey);
            }
        }
        //删除数据
        for(String removingKey:removingKeys){
            dataCube.getDatas().remove(removingKey);
        }
    }

    /**
     *
     * @param dataCube 立方体
     * @param dimId 计算的维度ID
     * @param calculateItems 计算项集合
     * @param groupSumValues 分组汇总
     * @return
     */
    private DataCube calculateDimension(DataCube dataCube,String dimId,List<CalculateItem> calculateItems,
                                        Map<String,Double> groupSumValues){
        if(dataCube.getDatas()==null){
            return  dataCube;
        }
        //
        List<Dimension> dimensions = processCalculateDimension(dataCube,dimId);
        Dimension calculateDimension = dimensions.get(dimensions.size()-1);//最后一个维度为计算维度

        //
        if(!dimId.equals(calculateDimension.getId())){
            return dataCube;
        }
        //处理外部加入的计算项
        if(!CollectionUtils.isEmpty(calculateItems)){
            List<CalculateItem> convertExpressionCalculateItems = new ArrayList<>(calculateItems);
            for(CalculateItem calculateItem:convertExpressionCalculateItems){
                calculateItem.setExpression(CalculateItemUtils.convertItemExpression(calculateItem));
            }
            calculateDimension.getItems().addAll(convertExpressionCalculateItems);
        }
        //展开维度为交叉单元格集合
        List<Item>[] crossColItems = DimensionUtils.expendedCrossColItems(dimensions);

        int calculateDimensionCount = calculateDimension.getItems().size();
        //
        List<List<Item>> blockItems = new ArrayList<>();
        for(int i=0;i<crossColItems.length;i++){
            if(i>0 && i%calculateDimensionCount==0){
                //分块执行计算
                calculateBlockItems(dataCube,calculateDimension,blockItems,groupSumValues);
                //新的数组
                blockItems = new ArrayList<>();
            }
            blockItems.add(crossColItems[i]);
        }

        if(blockItems.size()>0){
            //执行最后一块的计算
            calculateBlockItems(dataCube,calculateDimension,blockItems,groupSumValues);
        }

        return dataCube;
    }

    /**
     *
     * @param dataCube
     * @param dimId
     * @return
     */
    private List<Dimension> processCalculateDimension(DataCube dataCube,String dimId){
        List<Dimension> dimensions = new ArrayList<>();//维度集合
        Dimension calculateDimension = null;
        //分离计算维度和其他维度
        for(Dimension dimension: dataCube.getDimensions()){
            dimension.getItems().forEach(item -> item.setDimId(dimension.getId()));
            if(dimId.equals(dimension.getId())){
                calculateDimension = dimension;
            }else{
                dimensions.add(dimension);
            }
        }
        //计算维度放最后一个集合元素
        dimensions.add(calculateDimension);
        return dimensions;
    }

    /**
     *
     * @param dataCube
     * @param crossItem
     * @return
     */
    private DataItem parseValueDataItem(DataCube dataCube,List<Item> crossItem){
        List<Item> items = new ArrayList();

        if(!CollectionUtils.isEmpty(dataCube.getHeaderItems())){
            items.addAll(dataCube.getHeaderItems());
        }
        items.addAll(crossItem);
        DataItem dataItem = CalculateItemUtils.buildDataItem(items);
        String dataKey = dataItem.buildKey();

        if(dataCube.getDatas().containsKey(dataKey)){
            dataItem = dataCube.getDatas().get(dataKey);
        }

        if(dataItem.getData()==null){
            DataValue dataValue = new DataValue("0");
            dataValue.setValue(0d);
            dataItem.setData(dataValue);
        }

        if(dataItem.getData().getValue()==null){
            dataItem.getData().setValue(0d);
        }

        return dataItem;
    }
    /**
     * 分块计算
     * @param dataCube
     * @param calculateDimension
     * @param blockItems
     * @param groupSumValues
     */
    private void calculateBlockItems(DataCube dataCube,Dimension calculateDimension,
                                     List<List<Item>> blockItems,Map<String,Double> groupSumValues) {
        double sum = 0;
        Map<String,Double> values = new HashMap<>();

        int index = 0;
        Map<Item,DataItem> calculateDataItems = new LinkedHashMap<>();

        //把项的健值对添加到values中，
        for(List<Item> crossItem:blockItems){
            //
            Item item = calculateDimension.getItems().get(index++);
            //
            DataItem valueDataItem = parseValueDataItem(dataCube,crossItem);
            //计算合计
            sum+=valueDataItem.getData().getValue().doubleValue();
            values.put(item.getId(),valueDataItem.getData().getValue().doubleValue());

            //根据表达式计算
            if(StringUtils.isNotEmpty(item.getExpression())){
                calculateDataItems.put(item,valueDataItem);
            }
        }

        values.put(PARAM_ITEM_COUNT,sum);//分组合计
        values.put(PARAM_GROUP_SUM,1d);//用于占比计算等
        values.put(PARAM_ITEM_COUNT,new Integer(calculateDimension.getItems().size()).doubleValue());//维度项数

        //执行分组维度项计算
        if(!CollectionUtils.isEmpty(calculateDataItems)){
            this.doGroupCalculate(dataCube,values,calculateDataItems,groupSumValues);
        }
    }

    /**
     *
     * @param dataCube
     * @param values
     * @param calculateDataItems
     * @param groupSumValues
     */
    private void doGroupCalculate(DataCube dataCube,Map<String,Double> values,
                             Map<Item,DataItem> calculateDataItems,Map<String,Double> groupSumValues){
        List<Item> calculateItems = new ArrayList(calculateDataItems.keySet());
        Collections.reverse(calculateItems);
        for(Item calculateItem:calculateItems){
            DataItem dataItem = calculateDataItems.get(calculateItem);
            String dataKey = dataItem.buildKey();
            if(groupSumValues!=null){
                if(groupSumValues.containsKey(dataKey)){
                    values.put(PARAM_GROUP_SUM,groupSumValues.get(dataKey));
                }else if(groupSumValues.containsKey(PARAM_GROUP_SUM) && dataCube.getDatas().containsKey(dataKey)){
                    dataItem = dataCube.getDatas().get(dataKey);
                    dataItem.getData().setValue(groupSumValues.get(PARAM_GROUP_SUM));
                    dataItem.getData().setStrValue(DimensionUtils.format(groupSumValues.get(PARAM_GROUP_SUM)));
                    continue;
                }
            }
            double value = calculateExpressionValue(calculateItem.getExpression(),values);

            DataValue dataValue = new DataValue(DimensionUtils.format(value));
            dataValue.setValue(value);
            values.put(calculateItem.getId(),value);
            dataItem.setData(dataValue);
            //加入到cube中
            dataCube.addDataItem(dataItem);
        }
    }

    /**
     *
     * @param expression
     * @param values
     * @return
     */
    private double calculateExpressionValue(String expression, Map<String, Double> values) {
        Expression execExpression = parser.parseExpression(expression);
        StandardEvaluationContext context = new StandardEvaluationContext(values);
        try {
            return execExpression.getValue(context,Double.class);
        } catch (EvaluationException e) {
            //e.printStackTrace();
        }
        return 0d;
    }

}
