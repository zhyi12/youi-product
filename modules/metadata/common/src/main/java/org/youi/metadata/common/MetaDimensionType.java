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

/**
 * 统计数据维度类型
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public enum MetaDimensionType {

    areas("D","行政区划"),//省市县
    provinces("D1","省"),//省
    cites("D2","市州"),//市
    counties("D3","区县"),//
    periods("P","报告期"),
    indicators("I","指标"),//计量维度，按项展开
    years("Y","年份"),
    halfYears("HY","半年"),
    months("M","月份"),
    tenDays("T","旬"),
    quarters("Q","季度"),
    accs("C","累计"),//累计维度
    attrs("A","属性"),//时间属性维度
    respondents("R","调查对象"),
    catalogs("L","目录"),
    categories("G","分组");//dimId 需要使用分组的实际ID

    private String key;

    private String text;

    MetaDimensionType(String key,String text){
        this.key = key;
        this.text = text;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
