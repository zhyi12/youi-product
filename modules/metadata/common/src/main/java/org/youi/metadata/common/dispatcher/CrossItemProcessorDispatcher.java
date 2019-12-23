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
package org.youi.metadata.common.dispatcher;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.util.CollectionUtils;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.metadata.common.ICrossItemProcessor;
import org.youi.metadata.common.ICrossItemProcessorDispatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class CrossItemProcessorDispatcher implements ICrossItemProcessorDispatcher, ApplicationContextAware {

    private List<ICrossItemProcessor> crossItemProcessors;//交叉项处理类集合

    @Override
    public List<Item> process(List<Item> crossItem) {
        if(!CollectionUtils.isEmpty(crossItemProcessors)){
            for(ICrossItemProcessor crossItemProcessor:crossItemProcessors){
                if(crossItemProcessor.supports(crossItem)){
                    crossItem = crossItemProcessor.process(crossItem);
                }
            }
        }
        return crossItem;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.initCrossItemProcessors(applicationContext);
    }

    /**
     * 初始化交叉维度处理器
     * @param context
     */
    private void initCrossItemProcessors(ApplicationContext context) {
        if (crossItemProcessors == null) {
            Map<String, ICrossItemProcessor> adapterBeans =
                    BeanFactoryUtils.beansOfTypeIncludingAncestors(context, ICrossItemProcessor.class, true, false);
            if (adapterBeans != null) {
                crossItemProcessors = new ArrayList<>(adapterBeans.values());
                crossItemProcessors.sort(new OrderComparator());
            }
        }
    }
}
