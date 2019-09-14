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
package org.youi.dataquery.query.entity;

import org.youi.framework.core.dataobj.Domain;

import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class SqlExpression implements Domain{

    private static final long serialVersionUID = -3274083372645863111L;

    private String source;

    private List<DataResourceItem> items;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<DataResourceItem> getItems() {
        return items;
    }

    public void setItems(List<DataResourceItem> items) {
        this.items = items;
    }

}
