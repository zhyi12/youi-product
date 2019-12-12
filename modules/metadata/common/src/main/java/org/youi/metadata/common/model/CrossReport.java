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
import org.youi.framework.core.dataobj.cube.Item;

import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class CrossReport implements Domain {

    private static final long serialVersionUID = -2431135170014626095L;

    private List<Item> headerItems;//表头项集合

    private List<BlockArea> mainAreas;//主栏区域集合

    private List<BlockArea> slaveAreas;//宾栏区域集合

    public List<Item> getHeaderItems() {
        return headerItems;
    }

    public CrossReport setHeaderItems(List<Item> headerItems) {
        this.headerItems = headerItems;
        return this;
    }

    public List<BlockArea> getMainAreas() {
        return mainAreas;
    }

    public CrossReport setMainAreas(List<BlockArea> mainAreas) {
        this.mainAreas = mainAreas;
        return this;
    }

    public List<BlockArea> getSlaveAreas() {
        return slaveAreas;
    }

    public CrossReport setSlaveAreas(List<BlockArea> slaveAreas) {
        this.slaveAreas = slaveAreas;
        return this;
    }
}
