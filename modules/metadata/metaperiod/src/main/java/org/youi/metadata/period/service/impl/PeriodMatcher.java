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
package org.youi.metadata.period.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.metadata.period.model.Period;
import org.youi.metadata.period.service.IPeriodMatcherAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class PeriodMatcher implements ApplicationContextAware {

    private List<IPeriodMatcherAdapter> periodMatcherAdapters;

    /**
     *
     * @param item
     * @return
     */
    public Item matchPeriodByText(Item item){
        if(!CollectionUtils.isEmpty(periodMatcherAdapters)){
            for(IPeriodMatcherAdapter periodMatcherAdapter:periodMatcherAdapters){
                if(periodMatcherAdapter.supports(item.getText())){
                    Period period = periodMatcherAdapter.match(item.getText());
                    if(period!=null){
                        //
                        item.setMappedId(period.buildKey().getId());
                        if(Boolean.TRUE.equals(period.getAcc())){
                            item.setLevel(1);//
                        }
                        break;
                    }
                }
            }
        }
        return item;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.initPeriodMatcherAdapters(applicationContext);
    }

    private void initPeriodMatcherAdapters(ApplicationContext context) {
        if (periodMatcherAdapters == null) {
            Map<String, IPeriodMatcherAdapter> adapterBeans =
                    BeanFactoryUtils.beansOfTypeIncludingAncestors(context, IPeriodMatcherAdapter.class, true, false);
            if (adapterBeans != null) {
                periodMatcherAdapters = new ArrayList<>(adapterBeans.values());
                periodMatcherAdapters.sort(new OrderComparator());
            }
        }
    }
}
