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
package org.youi.dataquery.presto.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.dataquery.engine.entity.*;
import org.youi.dataquery.engine.utils.DimensionUtils;
import org.youi.dataquery.presto.dao.PrestoQueryDao;
import org.youi.dataquery.presto.service.impl.PrestoQueryService;
import org.youi.framework.core.dataobj.cube.DataCube;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.util.PropertyUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {ServiceTestConfig.class})
public class PrestoQueryServiceTest {

    @Rule
    public MockitoRule mockitoRule= MockitoJUnit.rule();

    @Rule
    public ExpectedException thrown= ExpectedException.none();

    @Autowired
    public PrestoQueryService prestoQueryService;

    @Mock
    private PrestoQueryDao prestoQueryDao;//mock对象

    /**
     * 初始化服务的mock属性对象
     */
    @Before
    public void setup(){
        PropertyUtils.setPropertyValue(prestoQueryService,"prestoQueryDao",prestoQueryDao);
    }

    /**
     * SELECT t_.DATA_101_COL044,t_.DATA_101_COL007,sum(t_.S203_20180_A0) as S203_20180_A0 FROM stats_working_task_row_st001 as t_ GROUP by cube(t_.DATA_101_COL044,t_.DATA_101_COL007)
     * @Story            立方体数据查询 - prestoQueryService.queryDataCube
     * @Case             单表立方体数据查询 - queryDataCube_story_01001
     * @Description
     */
    @Test
    public void testQueryDataCube(){
        String mainTableName = "stats_working_task_row_st001";
        List<MeasureItem> measureItems = mockMeasureItems();//计量项
        List<Group> groups = mockGroups();//分组维度
        //模拟数据持久类
        List<CubeRowData> cubeRowDatas = mockCubeRowDatas(groups,measureItems);

        //预期生成的sql语句
        String cubeQuerySql = "SELECT t_.DATA_101_COL044,t_.DATA_101_COL007,sum(t_.S203_20180_A0) as S203_20180_A0 FROM stats_working_task_row_st001 as t_ WHERE t_.DATA_101_COL044  IN  (?,?) AND t_.DATA_101_COL007  IN  (?,?,?,?) GROUP by cube(t_.DATA_101_COL044,t_.DATA_101_COL007)";
        String[] params = {"1","2","01","02","03","07"};//预期的生成的参数
        CubeColumns cubeColumns = new CubeColumns();
        cubeColumns.addGroupColumn("DATA_101_COL007").addGroupColumn("DATA_101_COL044")
                .addMeasureColumn("S203_20180_A0");
        //模拟查询sql和预期sql参数返回的数据
        when(prestoQueryDao.queryCubeRowDatas(cubeColumns,cubeQuerySql,params)).thenReturn(cubeRowDatas);

        DataCube dataCube = prestoQueryService.queryDataCube(mainTableName,groups,null,measureItems,cubeColumns,null);
        //判断dataCube
        Assert.assertTrue(dataCube.getDatas().containsKey("I.S203_20180_A0|group001.1|group002.01"));
    }

    /**
     * @Story            立方体数据查询 - prestoQueryService.queryDataCube
     * @Case             带过滤条件的立方体数据查询 - queryDataCube_story_01002
     * @Description
     */
    @Test
    public void testQueryDataCubeWithConditions(){

    }

    /**
     * @Story            立方体数据查询 - prestoQueryService.queryDataCube
     * @Case             带目录子表的立方体数据查询 - queryDataCube_story_01003
     * @Description
     */
    @Test
    public void testQueryDataCubeWithCatalog(){

    }

    private List<MeasureItem> mockMeasureItems() {
        List<MeasureItem> measureItems = new ArrayList<>();
        MeasureItem measureItem = new MeasureItem();
        measureItem.setColumnName("S203_20180_A0");
        measureItem.setId("S203_20180_A0");
        measureItems.add(measureItem);
        return  measureItems;
    }

    private List<Group> mockGroups(){
        List<Group> groups = new ArrayList<>();

        Group group = new Group();
        group.setColumnName("DATA_101_COL044");
        group.setId("group001");

        group.setItems(mockItems(group.getId(),new String[]{"1","2"}));

        groups.add(group);

        group = new Group();
        group.setColumnName("DATA_101_COL007");
        group.setId("group002");
        group.setItems(mockItems(group.getId(),new String[]{"01","02","03","07"}));

        groups.add(group);

        return groups;
    }

    private List<Item> mockItems(String groupId,String[] itemIds) {
        List<Item> items = new ArrayList<>();
        for(String itemId:itemIds){
            Item item = new Item(itemId,itemId);
            item.setDimId(groupId);
            items.add(item);
        }
        return items;
    }

    /**
     *
     * @param groups
     * @param measureItems
     * @return
     */
    private List<CubeRowData> mockCubeRowDatas(List<Group> groups, List<MeasureItem> measureItems) {
        List<CubeRowData> cubeRowDatas = new ArrayList<>();

        List<Group> allGroups = new ArrayList<>(groups);
        List<Item> measureGroupItems = new ArrayList<>();

        Group measureGroup = new Group();
        measureGroup.setId("measure");
        measureGroup.setText("计量");
        measureItems.forEach(measureItem -> {
            Item measureGroupItem = new Item();
            measureGroupItem.setDimId(measureGroup.getId());
            measureGroupItem.setId(measureItem.getId());
            measureGroupItem.setText(measureItem.getText());
            measureGroupItems.add(measureGroupItem);
        });
        //
        measureGroup.setItems(measureGroupItems);
        //
        allGroups.add(measureGroup);

        List<Item>[] crossItemsList = DimensionUtils.expendedCrossColItems(allGroups);

        for(List<Item> crossItems:crossItemsList){
            CubeRowData cubeRowData = new CubeRowData();
            Map<String, Double> rowMeasures = new HashMap<>();
            Map<String, String> rowGroups = new HashMap<>();
            for(Item crossItem:crossItems){
                if(measureGroup.getId().equals(crossItem.getDimId())){
                    //计量
                    rowMeasures.put(crossItem.getId(),randomValue());
                }else{
                    rowGroups.put(crossItem.getDimId(),crossItem.getId());
                }
            }
            cubeRowData.setMeasures(rowMeasures);
            cubeRowData.setGroups(rowGroups);

            cubeRowDatas.add(cubeRowData);

        }
        return cubeRowDatas;
    }

    private Double randomValue() {
        return  new Double(Math.random()*10000).intValue() / 100d;
    }
}
