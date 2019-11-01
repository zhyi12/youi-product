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

/**
 * visualMap 是视觉映射组件，用于进行『视觉编码』，也就是将数据映射到视觉元素（视觉通道）。
 * <p>
 * 视觉元素可以是：
 * <p>
 * symbol: 图元的图形类别。
 * symbolSize: 图元的大小。
 * color: 图元的颜色。
 * colorAlpha: 图元的颜色的透明度。
 * opacity: 图元以及其附属物（如文字标签）的透明度。
 * colorLightness: 颜色的明暗度，参见 HSL。
 * colorSaturation: 颜色的饱和度，参见 HSL。
 * colorHue: 颜色的色调，参见 HSL。
 * visualMap 组件可以定义多个，从而可以同时对数据中的多个维度进行视觉映射。
 * <p>
 * visualMap 组件可以定义为 分段型（visualMapPiecewise） 或 连续型（visualMapContinuous），通过 type 来区分。例如：
 * <p>
 * option = {
 * visualMap: [
 * { // 第一个 visualMap 组件
 * type: 'continuous', // 定义为连续型 visualMap
 * ...
 * },
 * { // 第二个 visualMap 组件
 * type: 'piecewise', // 定义为分段型 visualMap
 * ...
 * }
 * ],
 * ...
 * };
 *
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class VisualMap {
}
