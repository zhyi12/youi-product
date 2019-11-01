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
 * Created by zhouyi on 2019/11/1.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {TestConfig.class})
public class EchartsLineOptionWriterTest extends AbstractEchartsOptionWriterTest {

    @Autowired
    private EchartsOptionWriter echartsOptionWriter;

    private final static String CHART_TYPE = ChartType.Line.name();

    /**
     * @story 生成echarts图表的option
     * @case  001   折线图： 单个系列，y轴为值，x轴为坐标
     * @throws Exception
     */
    @Test
    public void writeLineOption() throws Exception {
        //mock 2个维度，一个序列的dataCube,period维度加入头项
        DataCube dataCube = DataCubeMockUtils.buildDataCubeWithPeriodHeader();
        //
        IOption option =  echartsOptionWriter.write(CHART_TYPE,null,dataCube);

        //验证option

        //写入html文件
        writeHtmlFile(option,CHART_TYPE);
    }


    /**
     * @story 生成echarts图表的option
     * @case  002   折线图： 单个系列，y轴为值，x轴为坐标，使用dataZoom，grid、标题
     * @throws Exception
     */

    /**
     * @story 生成echarts图表的option
     * @case  003   折线图： 多个系列，y轴为值，单一y轴，x轴为坐标
     * @throws Exception
     */

    /**
     * @story 生成echarts图表的option
     * @case  004   折线图： 多个系列，y轴为值，双y轴，x轴为坐标
     * @throws Exception
     */

    /**
     * @story 生成echarts图表的option
     * @case  005   折线图： 多个系列，y轴为值，双y轴，x轴为坐标，启用序列的折线区域样式
     * @throws Exception
     */

    /**
     * @story 生成echarts图表的option
     * @case  006   折线图： 多个系列，y轴为值，双y轴，x轴为坐标，启用序列的折线区域样式，显示图例
     * @throws Exception
     */



}