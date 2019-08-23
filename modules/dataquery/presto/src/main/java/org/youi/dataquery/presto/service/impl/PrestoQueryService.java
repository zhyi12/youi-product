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
package org.youi.dataquery.presto.service.impl;

import org.springframework.stereotype.Service;
import org.youi.dataquery.engine.service.IQueryService;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("prestoQueryService")
public class PrestoQueryService implements IQueryService {

    //执行查询，返回行数据
    public PagerRecords queryRowDatas(Pager pager){
        return null;
    }
}
