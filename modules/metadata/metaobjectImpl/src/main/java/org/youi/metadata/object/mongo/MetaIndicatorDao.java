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
package org.youi.metadata.object.mongo;

import org.youi.framework.mongo.DaoMongo;
import org.youi.metadata.object.entity.MetaIndicator;

/**
 * 数据持久化类： 元数据对象 - 指标
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public interface MetaIndicatorDao extends DaoMongo<MetaIndicator,String> {

}
