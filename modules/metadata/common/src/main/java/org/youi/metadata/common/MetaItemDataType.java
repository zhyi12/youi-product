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
package org.youi.metadata.common;

public enum MetaItemDataType {

    integer("int","整数"),
    bigint("bigint","长整数"),
    number("number","数值"),
    date("date","日期"),
    string("string","文本");

    private String key;

    private String text;

    MetaItemDataType(String key, String text){
        this.key = key;
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public MetaItemDataType setKey(String key) {
        this.key = key;
        return this;
    }

    public String getText() {
        return text;
    }

    public MetaItemDataType setText(String text) {
        this.text = text;
        return this;
    }
}
