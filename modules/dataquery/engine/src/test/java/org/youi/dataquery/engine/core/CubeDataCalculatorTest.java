package org.youi.dataquery.engine.core;

import org.junit.Assert;
import org.junit.Test;
import org.youi.dataquery.engine.DataQueryConstants;
import org.youi.dataquery.engine.model.CalculateItem;
import org.youi.framework.core.dataobj.cube.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zhouyi on 2019/8/26.
 */
public class CubeDataCalculatorTest {

    private  CubeDataCalculator cubeDataCalculator = new CubeDataCalculator();


    /**
     *
     */
    @Test
    public void testCalculateDimension(){
        List<CalculateItem> calculateItems = new ArrayList<>();
        CalculateItem calculateItem = new CalculateItem();
        calculateItem.setId("cal1");
        calculateItem.setText("计算项");
        calculateItem.setExpression("[m001]+[m002]");
        calculateItems.add(calculateItem);

        DataCube dataCube = DataCubeTestUtils.buildDataCube();
        cubeDataCalculator.calculateDimension(dataCube,
                DataQueryConstants.DIM_MEASURE_ID,calculateItems);

        //验证计算结果
        Assert.assertTrue(dataCube.getDatas().get("A.item001|I.cal1").getData().getValue().doubleValue() ==
                dataCube.getDatas().get("A.item001|I.m001").getData().getValue().doubleValue()+
                        dataCube.getDatas().get("A.item001|I.m002").getData().getValue().doubleValue());
    }

}