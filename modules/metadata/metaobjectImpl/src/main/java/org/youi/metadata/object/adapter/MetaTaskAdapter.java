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
package org.youi.metadata.object.adapter;

import org.springframework.stereotype.Component;
import org.youi.metadata.common.IMetaObjectCreateAdapter;
import org.youi.metadata.common.IMetaParentFinderAdapter;
import org.youi.metadata.object.MetaObjectConstants;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class MetaTaskAdapter implements IMetaParentFinderAdapter,IMetaObjectCreateAdapter {

    @Override
    public boolean supports(String metaObjectName) {
        return MetaObjectConstants.META_OBJECT_NAME_REPORT.equals(metaObjectName);
    }

    public String findParentMetaObjectName(){
        return MetaObjectConstants.META_OBJECT_NAME_TASK;
    }
}
