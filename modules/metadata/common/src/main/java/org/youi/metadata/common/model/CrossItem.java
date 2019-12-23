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
package org.youi.metadata.common.model;

import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.cube.AbstractItem;
import org.youi.framework.core.dataobj.cube.Item;

import java.util.LinkedList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class CrossItem extends AbstractItem implements Domain {

    private static final long serialVersionUID = -788285044082926039L;

    private String cubeId;//数据立方体ID

    private List<Item> items = new LinkedList<>();//交叉的项

    public String getCubeId() {
        return cubeId;
    }

    public CrossItem setCubeId(String cubeId) {
        this.cubeId = cubeId;
        return this;
    }

    public List<Item> getItems() {
        return items;
    }

    public CrossItem setItems(List<Item> items) {
        this.items = items;
        return this;
    }

    /**
     * 添加交叉项
     * @param item
     * @return
     */
    public CrossItem addItem(Item item){
        if(items==null){
            items = new LinkedList<>();
        }
        if(item!=null){
            items.add(item);
        }
        return this;
    }
}
