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
package org.youi.tools.indexing.entity;

import org.springframework.beans.BeanUtils;
import org.youi.framework.core.dataobj.cube.Item;

import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class MatchingItem extends Item {

    public MatchingItem(){
        //ignore
    }

    /**
     *
     * @param item
     */
    public MatchingItem(Item item){
        BeanUtils.copyProperties(item,this);
    }

    private List<IndexResult> matchingResults;

    public List<IndexResult> getMatchingResults() {
        return matchingResults;
    }

    public void setMatchingResults(List<IndexResult> matchingResults) {
        this.matchingResults = matchingResults;
    }
}
