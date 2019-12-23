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

import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class WorkingCrossItemProcessor implements ICrossItemProcessor, Ordered {
    @Override
    public boolean supports(List<Item> crossItem) {
        return true;
    }

    @Override
    public List<Item> process(List<Item> crossItem) {
        crossItem.forEach(item -> {
            String dimensionId = findDimensionIdByType(item.getDimId());
            if(dimensionId!=null){
                item.setDimId(dimensionId);
            }
        });
        return crossItem;
    }

    /**
     *
     * @param dimId
     * @return
     */
    private String findDimensionIdByType(String dimId) {
        for(MetaDimensionType metaDimensionType:MetaDimensionType.values()){
            if(dimId!=null && dimId.contains(metaDimensionType.name())){
                return metaDimensionType.name();
            }
        }
        return null;
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
