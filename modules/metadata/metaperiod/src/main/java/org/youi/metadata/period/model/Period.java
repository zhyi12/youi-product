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
package org.youi.metadata.period.model;

import org.apache.commons.lang.StringUtils;
import org.youi.framework.core.dataobj.Domain;

import java.text.MessageFormat;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class Period implements Domain {

    private static final String PERIOD_FORMAT = "{0}{1}{2}{3}";

    private String id;

    private String year = "0000";//年

    private String quarter = "0";//季

    private String month = "00";//月

    private String tenDays = "0";//旬

    private Boolean acc;//是否累计报告期

    public String getId() {
        return id;
    }

    public Period setId(String id) {
        this.id = id;
        return this;
    }

    public String getYear() {
        return year;
    }

    public Period setYear(String year) {
        this.year = year;
        return this;
    }

    public String getQuarter() {
        return quarter;
    }

    public Period setQuarter(String quarter) {
        this.quarter = quarter;
        return this;
    }

    public String getMonth() {
        return month;
    }

    public Period setMonth(String month) {
        this.month = month;
        return this;
    }

    public String getTenDays() {
        return tenDays;
    }

    public Period setTenDays(String tenDays) {
        this.tenDays = tenDays;
        return this;
    }

    public Boolean getAcc() {
        return acc;
    }

    public Period setAcc(Boolean acc) {
        this.acc = acc;
        return this;
    }

    public Period buildKey(){
        this.id  = MessageFormat.format(PERIOD_FORMAT,year,quarter,
                StringUtils.leftPad(month,2,"0"),tenDays);
        return this;
    }
}
