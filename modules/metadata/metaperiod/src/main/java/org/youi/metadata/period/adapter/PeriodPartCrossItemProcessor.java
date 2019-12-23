/*
 * Copyright 2018-2024 the original author or authors.
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
package org.youi.metadata.period.adapter;

import org.springframework.stereotype.Component;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.metadata.common.ICrossItemProcessor;
import org.youi.metadata.common.MetaDimensionType;
import org.youi.metadata.common.utils.DimensionUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class PeriodPartCrossItemProcessor implements ICrossItemProcessor {

    @Override
    public boolean supports(List<Item> crossItem) {
        int yearDimensionCount = 0, partDimensionCount = 0;
        boolean hasPeriodDimension = false;
        for(Item item:crossItem){
            if(MetaDimensionType.years.name().equals(item.getDimId())){
                yearDimensionCount++;
            }else if(isPeriodPartDimension(item.getDimId())){
                partDimensionCount++;
            }else if(MetaDimensionType.periods.name().equals(item.getDimId())){
                hasPeriodDimension = true;
            }
        }
        //不包含报告期维度，通过年和其他维度组合报告期维度
        return !hasPeriodDimension && yearDimensionCount==1 && partDimensionCount==1;
    }

    /**
     * 是否可组合报告期维度
     * @param dimId
     * @return
     */
    private boolean isPeriodPartDimension(String dimId) {
        return MetaDimensionType.months.name().equals(dimId)//月报
                ||MetaDimensionType.quarters.name().equals(dimId)//季报
                ||MetaDimensionType.halfYears.name().equals(dimId);//半年报
    }

    @Override
    public List<Item> process(List<Item> crossItem) {
        Map<String,Item> partItems =  DimensionUtils.findDimensionItems(crossItem,
                new String[]{MetaDimensionType.years.name(),
                        MetaDimensionType.months.name(),
                        MetaDimensionType.quarters.name(),
                        MetaDimensionType.halfYears.name(),
                        MetaDimensionType.tenDays.name()});
        crossItem.removeIf((item -> partItems.containsKey(item.getDimId())));
        //增加报告期维度
        crossItem.add(buildPeriodItem(partItems));
        return crossItem;
    }

    /**
     *
     * @param partItems
     * @return
     */
    private Item buildPeriodItem(Map<String, Item> partItems) {
        Item periodItem = new Item();
        periodItem.setDimId(MetaDimensionType.periods.name());
        periodItem.setText(MetaDimensionType.periods.getText());

        String year = partItems.get(MetaDimensionType.years.name()).getId(),
               quarter = "0",month="00",tenDays="0";

        if(partItems.containsKey(MetaDimensionType.halfYears.name())){//半年
            quarter = partItems.get(MetaDimensionType.halfYears.name()).getId();
        }else if(partItems.containsKey(MetaDimensionType.quarters.name())){//季度
            quarter = partItems.get(MetaDimensionType.quarters.name()).getId();
        }else if(partItems.containsKey(MetaDimensionType.months.name())){//月份
            month = partItems.get(MetaDimensionType.months.name()).getId();
        }else if(partItems.containsKey(MetaDimensionType.tenDays.name())){//旬
            tenDays = partItems.get(MetaDimensionType.tenDays.name()).getId();
        }

        periodItem.setId(MessageFormat.format("{0}{1}{2}{3}",year,quarter,month,tenDays));
        return periodItem;
    }
}
