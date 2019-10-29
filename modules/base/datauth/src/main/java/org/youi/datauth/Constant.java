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
package org.youi.datauth;

import org.youi.framework.esb.DataAccesses;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public final class Constant {

    /**
     * 私有构造函数
     */
    private Constant(){
        //ignore
    }

    /**
     * 数据权限缓存key
     */
    public final static String CACHE_DATA_ACCESS = DataAccesses.class.getName()+"_CACHE";
}
