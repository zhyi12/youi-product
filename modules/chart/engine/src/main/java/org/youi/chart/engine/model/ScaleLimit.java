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
package org.youi.chart.engine.model;

import org.youi.framework.core.dataobj.Domain;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class ScaleLimit implements Domain {

    private Integer mix;

    private Integer max;

    public Integer getMix() {
        return mix;
    }

    public ScaleLimit setMix(Integer mix) {
        this.mix = mix;
        return this;
    }

    public Integer getMax() {
        return max;
    }

    public ScaleLimit setMax(Integer max) {
        this.max = max;
        return this;
    }
}
