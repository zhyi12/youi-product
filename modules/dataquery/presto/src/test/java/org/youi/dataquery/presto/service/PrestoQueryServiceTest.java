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
package org.youi.dataquery.presto.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.dataquery.presto.service.impl.PrestoQueryService;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {ServiceTestConfig.class})
public class PrestoQueryServiceTest {

    @Rule
    public MockitoRule mockitoRule= MockitoJUnit.rule();

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Autowired
    public PrestoQueryService prestoQueryService;

    //@Mock 定义mock对象

    @Before
    public void setup(){
        //初始化
    }

    @Test
    public void queryTableTest(){
        Pager pager = new Pager(10,1,Pager.QUERY_TYPE_ALL);
        PagerRecords pagerRecords = prestoQueryService.queryRowDatas(pager);
        //

    }
}
