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

import org.youi.framework.core.dataobj.cube.Area;
import org.youi.framework.core.dataobj.cube.Item;

import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class BlockArea extends Area {

    private static final long serialVersionUID = -6599240249117189496L;

    private String id;

    private String text;

    private List<DimensionArea> dimensions;

    private List<Item> pubItems;

    public String getId() {
        return id;
    }

    public BlockArea setId(String id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public BlockArea setText(String text) {
        this.text = text;
        return this;
    }

    public List<DimensionArea> getDimensions() {
        return dimensions;
    }

    public BlockArea setDimensions(List<DimensionArea> dimensions) {
        this.dimensions = dimensions;
        return this;
    }

    public List<Item> getPubItems() {
        return pubItems;
    }

    public BlockArea setPubItems(List<Item> pubItems) {
        this.pubItems = pubItems;
        return this;
    }
}
