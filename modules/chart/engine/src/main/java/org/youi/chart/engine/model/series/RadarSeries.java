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
package org.youi.chart.engine.model.series;

import org.youi.chart.engine.model.value.ChartType;
import org.youi.framework.util.StringUtils;

/**
 * 雷达图序列
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class RadarSeries extends AbstractSeries{

    private static final long serialVersionUID = -2323226982050661889L;

    private Integer radarIndex;

    public RadarSeries(){
        this.setType(StringUtils.lowerCaseFirstLetter(ChartType.Radar.name()));
    }

    public Integer getRadarIndex() {
        return radarIndex;
    }

    public RadarSeries setRadarIndex(Integer radarIndex) {
        this.radarIndex = radarIndex;
        return this;
    }
}
