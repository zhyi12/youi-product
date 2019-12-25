package org.youi.dataquery.engine.core;

import org.junit.Assert;
import org.junit.Test;
import org.youi.dataquery.engine.DataQueryConstants;
import org.youi.dataquery.engine.model.CalculateItem;
import org.youi.dataquery.engine.model.RefCalculateItem;
import org.youi.dataquery.engine.utils.CubeDimensionUtils;
import org.youi.framework.core.dataobj.cube.*;
import org.youi.metadata.common.utils.DimensionUtils;

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
        calculateItem.setId("cal2");
        calculateItem.setText("小计");
        calculateItem.setExpression("[sum]");
        calculateItems.add(calculateItem);

        //
        //refDimId
        DataCube dataCube = DataCubeTestUtils.buildDataCube();
        List<RefCalculateItem> refCalculateItems = new ArrayList<>();

        RefCalculateItem refCalculateItem = new RefCalculateItem();
        refCalculateItem.setId("refCal1");
        refCalculateItem.setRefDimId("m001");
        refCalculateItem.setText(" 项目1占比");
//        refCalculateItem.setExpression("[ranking]");//排名
        refCalculateItem.setExpression("[proportion]");//占比
        refCalculateItems.add(refCalculateItem);

        dataCube = cubeDataCalculator.calculateDimension(dataCube,
                "A",DataQueryConstants.DIM_MEASURE_ID,refCalculateItems);
        DataCubeTestUtils.printDataCube(dataCube);
        //基于维度A做指定其他维度项的占比、排名计算
        cubeDataCalculator.calculateDimension(dataCube,
                "A",calculateItems);

        DataCubeTestUtils.printDataCube(dataCube);
        //验证计算结果
//        Assert.assertTrue(dataCube.getDatas().get("A.item001|I.cal1").getData().getValue().doubleValue() ==
//                dataCube.getDatas().get("A.item001|I.m001").getData().getValue().doubleValue()+
//                        dataCube.getDatas().get("A.item001|I.m002").getData().getValue().doubleValue());
    }

    /**
     * 计算排名
     */
    @Test
    public void testCalculateRanking() {
        DataCube dataCube = DataCubeTestUtils.buildDataCube();
        cubeDataCalculator.calculateRanking(dataCube,"A",DataQueryConstants.DIM_MEASURE_ID,"m001");
        DataCubeTestUtils.printDataCube(dataCube);
    }

    /**
     * 计算占比
     */
    @Test
    public void testCalculateProportion() {
        DataCube dataCube = DataCubeTestUtils.buildDataCube();
        cubeDataCalculator.calculateProportion(dataCube,"A",DataQueryConstants.DIM_MEASURE_ID,"m001");
        DataCubeTestUtils.printDataCube(dataCube);
    }

    /**
     * 在立方体维度项设置需要计算占比（基于计算维度）
     */
    @Test
    public void testCalculateRefFromCube() {

        DataCube dataCube = DataCubeTestUtils.buildDataCube();
        Dimension dimension = CubeDimensionUtils.findDimension(dataCube,DataCubeTestUtils.DIM_GROUP_A);
        //
        dimension.getItems().get(0).setMappedId("I.proportion");//按照m001项在I维度上占比
        dimension.getItems().get(1).setMappedId("I.ranking");//按照m002项在I维度上排序

        cubeDataCalculator.calculateDimension(dataCube,DataCubeTestUtils.DIM_GROUP_A);
        DataCubeTestUtils.printDataCube(dataCube);
    }
}