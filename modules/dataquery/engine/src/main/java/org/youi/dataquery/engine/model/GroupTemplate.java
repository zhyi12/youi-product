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
package org.youi.dataquery.engine.model;

import org.apache.commons.lang.ArrayUtils;
import org.youi.framework.core.dataobj.cube.Item;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 分组模版
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class GroupTemplate extends Group{

    private static final long serialVersionUID = 3255488310092262803L;

    private String groupId;//分组ID

    private double[] sections;//分段数据 [100,200,300,500]

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public double[] getSections() {
        return sections;
    }

    public void setSections(double[] sections) {
        this.sections = sections;
        this.buildSectionsItems();
    }

    /**
     *
     */
    private void buildSectionsItems() {
        if(ArrayUtils.isNotEmpty(sections)){
            List<Item> items  = new ArrayList<>();
            items.add(new Item("0","小于"+sections[0]));
            if(sections.length>1){
                for(int i=1;i<sections.length;i++){
                    items.add(new Item(new Integer(i).toString(),"大于"+sections[i-1]+"小于等于"+sections[i]));
                }
            }
            //大于等于
            items.add(new Item(new Integer(sections.length).toString(),"大于等于"+sections[sections.length-1]));
            this.setItems(items);
        }
    }
}
