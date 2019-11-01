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

/**
 *
 * 平行坐标系（Parallel Coordinates） 是一种常用的可视化高维数据的图表。

 例如 series-parallel.data 中有如下数据：

 [
 [1,  55,  9,   56,  0.46,  18,  6,  '良'],
 [2,  25,  11,  21,  0.65,  34,  9,  '优'],
 [3,  56,  7,   63,  0.3,   14,  5,  '良'],
 [4,  33,  7,   29,  0.33,  16,  6,  '优'],
 { // 数据项也可以是 Object，从而里面能含有对线条的特殊设置。
 value: [5,  42,  24,  44,  0.76,  40,  16, '优']
 lineStyle: {...},
 }
 ...
 ]
 数据中，每一行是一个『数据项』，每一列属于一个『维度』。（例如上面数据每一列的含义分别是：『日期』,『AQI指数』, 『PM2.5』, 『PM10』, 『一氧化碳值』, 『二氧化氮值』, 『二氧化硫值』）。

 平行坐标系适用于对这种多维数据进行可视化分析。每一个维度（每一列）对应一个坐标轴，每一个『数据项』是一条线，贯穿多个坐标轴。在坐标轴上，可以进行数据选取等操作
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class ParallelSeries extends AbstractSeries{

//    left: 80,
//    top: 60,
//    right: 80,
//    bottom: 60,
//    width: 'auto',
//    height: 'auto',
//    layout: 'horizontal',
//    axisExpandable: false,
//    axisExpandCenter: null,
//    axisExpandCount: 0,
//    axisExpandWidth: 50,
//    axisExpandTriggerOn: 'click',
//    parallelAxisDefault: {...},
}
