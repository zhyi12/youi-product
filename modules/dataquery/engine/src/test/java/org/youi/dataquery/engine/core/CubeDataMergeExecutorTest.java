package org.youi.dataquery.engine.core;

import org.junit.Test;
import org.youi.dataquery.engine.utils.DimensionUtils;
import org.youi.framework.core.dataobj.cube.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zhouyi on 2019/8/26.
 */
public class CubeDataMergeExecutorTest {

    @Test
    public void testMergeCubes(){

        List<DataCube> mergingCubes = new ArrayList<>();
        List<Item> mergeItems = new ArrayList<>();

        mergeItems.add(new Item("20194000","2019年第四季度"));
        mergeItems.add(new Item("20193000","2019年第三季度"));

        mergingCubes.add(DataCubeTestUtils.buildDataCube());
        mergingCubes.add(DataCubeTestUtils.buildDataCube());

        Dimension dimension = new Dimension();
        dimension.setId("P");
        dimension.setText("报告期");
        dimension.setItems(mergeItems);

        //DataCube mergedDataCube = cubeDataCalculator.mergeCubes(mergingCubes,dimension);
        //System.out.println(mergedDataCube);

        //merge

    }


}