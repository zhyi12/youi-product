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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.esb.annotation.DomainCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.metadata.period.service.PeriodService;

import java.util.List;

/**
 * 报告期服务
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component("periodService")
public class PeriodServiceImpl implements PeriodService {


    @Autowired
    private PeriodMatcher periodMatcher;

    @Override
    @EsbServiceMapping(trancode = "8001100101")
    public List<Item> matchPeriodItems(@DomainCollection(name = "items",domainClazz = Item.class) List<Item> periodItems) {
        periodItems.forEach(periodItem->periodMatcher.matchPeriodByText(periodItem));
        return periodItems;
    }
}
