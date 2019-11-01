/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License; Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *private String   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing; software
 * distributed under the License is distributed on an "AS IS" BASIS;
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND; either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.youi.chart.engine.model.series;

import org.youi.chart.engine.model.Region;
import org.youi.chart.engine.model.ScaleLimit;

import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class GeoSeries extends AbstractSeries {

private Boolean show;
private String map;
private Boolean roam;
private String[] center;
private Double aspectScale;
private String boundingCoords;
private Double zoom;
private ScaleLimit scaleLimit;
private Map<String,String> nameMap;
private Boolean selectedMode;

private String left;
private String top;
private String right;
private String bottom;
private String layoutCenter;
private String layoutSize;
private Region regions;

}
