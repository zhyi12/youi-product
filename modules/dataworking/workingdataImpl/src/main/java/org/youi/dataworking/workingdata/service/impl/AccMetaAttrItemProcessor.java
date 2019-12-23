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
package org.youi.dataworking.workingdata.service.impl;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.metadata.common.ICrossItemProcessor;
import org.youi.metadata.common.MetaDimensionType;

import java.util.ArrayList;
import java.util.List;

/**
 * 交叉项中包含属性项，并且属性项为累计值的处理
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class AccMetaAttrItemProcessor implements ICrossItemProcessor, Ordered {

    private final static String ACC_META_ATTR_POSTFIX  = "_ACC";

    @Override
    public boolean supports(List<Item> crossItem) {
        for(Item item:crossItem){
            if(MetaDimensionType.attrs.name().equals(item.getDimId())
                && item.getId().endsWith(ACC_META_ATTR_POSTFIX)){
                //存在属性项，并且属性项ID中包含累计标识
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Item> process(List<Item> crossItem) {
        List<Item> splitItems = new ArrayList<>();
        crossItem.removeIf(item -> {
            if(MetaDimensionType.attrs.name().equals(item.getDimId())){
                Item attrItem = new Item(item.getId().replace(ACC_META_ATTR_POSTFIX,""),item.getText());
                attrItem.setDimId(MetaDimensionType.attrs.name());
                splitItems.add(attrItem);
                //
                Item accItem = new Item("1","累计值");
                accItem.setDimId(MetaDimensionType.accs.name());
                splitItems.add(accItem);//增加累计值维度
                return true;
            }
            return false;
        });

        crossItem.addAll(splitItems);
        return crossItem;
    }

    @Override
    public int getOrder() {
        return 9;
    }
}
