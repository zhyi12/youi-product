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
package org.youi.metadata.period.adapter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.youi.metadata.period.model.Period;
import org.youi.metadata.period.service.IPeriodMatcherAdapter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Order(1)
@Component
public class QuarterMatcherAdapter implements IPeriodMatcherAdapter {

    private static final Pattern PERIOD_TEXT_QUARTER_MATCH = Pattern.compile("^([0-9]{4}).*1-([1-4])季");

    private static final Pattern PERIOD_TEXT_QUARTER_ACC_MATCH = Pattern.compile("^([0-9]{4}).*1-([1-4])季");

    @Override
    public boolean supports(String text) {
        return PERIOD_TEXT_QUARTER_MATCH.matcher(text).find()
                ||PERIOD_TEXT_QUARTER_ACC_MATCH.matcher(text).find();
    }

    @Override
    public Period match(String text) {
        Period period = new Period();
        Matcher matcher = PERIOD_TEXT_QUARTER_ACC_MATCH.matcher(text);

        if(matcher.find()){
            //累计报告期
            period.setYear(matcher.group(1));
            period.setMonth(matcher.group(2));
            period.setAcc(Boolean.TRUE);
        }else{
            //当前报告期
            matcher = PERIOD_TEXT_QUARTER_MATCH.matcher(text);
            if(matcher.find()){
                period.setYear(matcher.group(1));
                period.setMonth(matcher.group(2));
            }
        }
        return period;
    }
}
