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
package org.youi.chart.echarts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.chart.engine.IOption;
import org.youi.chart.engine.model.value.ChartType;
import org.youi.framework.core.dataobj.cube.DataCube;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {TestConfig.class})
public class EchartsBarOptionWriterTest extends AbstractEchartsOptionWriterTest {

    @Autowired
    private EchartsOptionWriter echartsOptionWriter;

    private final static String CHART_TYPE = ChartType.Bar.name();

    /**
     * @story 生成echarts图表的option
     * @case  001   柱状图： 单个系列，y轴为值，x轴为坐标
     * @throws Exception
     */
    @Test
    public void writeBarOption() throws Exception {
        //mock 2个维度，一个序列的dataCube,period维度加入头项
        DataCube dataCube = DataCubeMockUtils.buildDataCubeWithPeriodHeader();
        //
        IOption option =  echartsOptionWriter.write(CHART_TYPE,null,dataCube);

        //验证option

        //写入html文件
        writeHtmlFile(option,CHART_TYPE);
    }
}
