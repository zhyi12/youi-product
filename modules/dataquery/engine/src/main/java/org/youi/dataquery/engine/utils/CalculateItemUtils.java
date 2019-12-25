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
import org.springframework.util.StringUtils;
import org.youi.dataquery.engine.model.CalculateItem;
import org.youi.dataquery.engine.model.RefCalculateItem;
import org.youi.framework.core.dataobj.cube.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class CalculateItemUtils {

    private static Pattern REF_MAPPING_PATTERN = Pattern.compile("(^\\w+)\\.(\\w+)");
    /**
     *
     * @param item 关联的维度项
     * @param dimId 计算维度
     * @return
     */
    public static RefCalculateItem buildRefCalculateItem(Item item,String dimId){

        if(StringUtils.isEmpty(item.getMappedId())){
            return null;
        }

        Matcher matcher = REF_MAPPING_PATTERN.matcher(item.getMappedId());
        if(matcher.find()){
            RefCalculateItem refCalculateItem = new RefCalculateItem();
            refCalculateItem.setId(item.getMappedId()+"_"+item.getId());
            refCalculateItem.setDimId(dimId);
            refCalculateItem.setRefDimId(matcher.group(1));
            refCalculateItem.setMappedId(item.getId());
            refCalculateItem.setRefType(matcher.group(2));//
            refCalculateItem.setText(item.getText()+item.getMappedId());//
            refCalculateItem.setExpression("["+refCalculateItem.getRefType()+"]");
            return refCalculateItem;
        }

        return null;
    }

    public static DataItem buildDataItem(List<Item> crossItem){
        DataItem dataItem = new DataItem();
        for(Item item:crossItem){
            dataItem.addDim(item.getDimId(),item.getId());
        }
        return dataItem;
    }

    /**
     *
     * @param dataKey
     * @return
     */
    public static List<Item> parseCrossItems(String dataKey){
        String testDataKey = "|"+dataKey+"|";
        Pattern pattern = Pattern.compile("\\|(\\w+)\\.(\\w+)");
        Matcher matcher = pattern.matcher(testDataKey);

        List<Item> items = new ArrayList<>();
        while (matcher.find()){
            if(matcher.groupCount()==2){
                Item item = new Item(matcher.group(2),"");
                item.setDimId(matcher.group(1));
                items.add(item);
            }
        }
        return items;
    }

    /**
     * @param dataItem
     * @return
     */
    public static Double findDataItemValue(DataItem dataItem) {
        Double value = null;
        if (dataItem != null && dataItem.getData() != null) {
            return dataItem.getData().getValue().doubleValue();
        }
        return value;
    }

    /**
     *
     * @param calculateItem
     * @return
     */
    public static String convertItemExpression(CalculateItem calculateItem) {
        List<Item> expressionItems= calculateItem.getExpressionItems();
        List<String> expressionItemIds = new ArrayList<>();
        if(!CollectionUtils.isEmpty(expressionItemIds)){
            for(Item item:expressionItems){
                expressionItemIds.add("['"+item.getId()+"']");
            }
        }
        return MessageFormat.format(calculateItem.getExpression(),expressionItemIds.toArray());
    }

}
