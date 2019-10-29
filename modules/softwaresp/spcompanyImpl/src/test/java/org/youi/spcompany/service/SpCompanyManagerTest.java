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
package org.youi.spcompany.service;

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
import org.youi.framework.util.PropertyUtils;

import static org.mockito.Mockito.*;

import org.youi.spcompany.entity.SpCompany;
import org.youi.spcompany.mongo.SpCompanyDao;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {ServiceTestConfig.class})
public class SpCompanyManagerTest{

    @Rule
    public MockitoRule mockitoRule= MockitoJUnit.rule();

    @Rule
    public ExpectedException thrown= ExpectedException.none();
	
	@Autowired
	private SpCompanyManager spCompanyManager;

    @Mock
    private SpCompanyDao spCompanyDao;

    @Before
    public void setup(){
        PropertyUtils.setPropertyValue(spCompanyManager,"spCompanyDao",spCompanyDao);
    }
    /***************************************saveSpCompany交易的测试用例***************************************/
    /**
    * @Story			savePage	           保存
    * @Case			    04_001 testSaveSpCompany
    * @Description
    */
    @Test
    public void testSaveSpCompany(){
        SpCompany spCompany = new SpCompany();

        spCompanyManager.saveSpCompany(spCompany);
        //thrown.expect(BusException.class);//预期返回BusException异常
        //保存：调用了一次pageDao的save方法
        verify(spCompanyDao, times(1)).save(spCompany);
    }

    /***************************************removeSpCompany交易的测试用例***************************************/
    /**
    * @Story			savePage	           保存
    * @Case			    05_001 testRemoveSpCompany
    * @Description
    */
    @Test
    public void testRemoveSpCompany(){
        String spCompanyId = "id";
        spCompanyManager.removeSpCompany(spCompanyId);
        verify(spCompanyDao, times(1)).remove(spCompanyId);
    }

}
