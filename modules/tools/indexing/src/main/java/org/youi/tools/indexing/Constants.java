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
package org.youi.tools.indexing;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public final class Constants {
    /**
     * 私有构造函数
     */
    private Constants(){
        //ignore
    }

    public final static String INDEXING_FIELD_ID = "id"; //id属性
    public final static String INDEXING_FIELD_TEXT = "text";//文本属性
    public final static String INDEXING_FIELD_FULL_TEXT = "fullText";//检索用的文本属性
    public final static String INDEXING_FIELD_GROUP = "group";//分类属性
}
