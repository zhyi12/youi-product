/*
* YOUI框架
* Copyright 2018 the original author or authors.
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
package org.youi.dataworking.workingdata.service;

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
import org.youi.framework.util.PropertyUtils;

import static org.mockito.Mockito.*;

import org.youi.dataworking.workingdata.entity.PhysicalTable;
import org.youi.dataworking.workingdata.mongo.PhysicalTableDao;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {ServiceTestConfig.class})
public class PhysicalTableManagerTest{

    @Rule
    public MockitoRule mockitoRule= MockitoJUnit.rule();

    @Rule
    public ExpectedException thrown= ExpectedException.none();
	
	@Autowired
	private PhysicalTableManager physicalTableManager;

    @Mock
    private PhysicalTableDao physicalTableDao;

    @Before
    public void setup(){
        PropertyUtils.setPropertyValue(physicalTableManager,"physicalTableDao",physicalTableDao);
    }
    /***************************************savePhysicalTable交易的测试用例***************************************/
    /**
    * @Story			savePage	           保存
    * @Case			    04_001 testSavePhysicalTable
    * @Description
    */
    @Test
    public void testSavePhysicalTable(){
        PhysicalTable physicalTable = new PhysicalTable();

        physicalTableManager.savePhysicalTable(physicalTable);
        //thrown.expect(BusException.class);//预期返回BusException异常
        //保存：调用了一次pageDao的save方法
        verify(physicalTableDao, times(1)).save(physicalTable);
    }

    /***************************************removePhysicalTable交易的测试用例***************************************/
    /**
    * @Story			savePage	           保存
    * @Case			    05_001 testRemovePhysicalTable
    * @Description
    */
    @Test
    public void testRemovePhysicalTable(){
        String physicalTableId = "id";
        physicalTableManager.removePhysicalTable(physicalTableId);
        verify(physicalTableDao, times(1)).remove(physicalTableId);
    }

}
