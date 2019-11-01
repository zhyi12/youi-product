package org.youi.chart.engine;

import org.youi.framework.core.dataobj.cube.DataCube;

/**
 * Created by zhouyi on 2019/11/1.
 */
public interface IChartOptionProcessor {

    /**
     * 支持的图表类型判断
     * @param chartType
     * @return
     */
    boolean supports(String chartType);

    /**
     * 写入option
     * @param option
     * @param dataCube
     */
    void writeOption(IOption option, DataCube... dataCube);
}
