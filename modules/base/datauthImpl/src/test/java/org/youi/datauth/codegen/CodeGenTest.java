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
package org.youi.datauth.codegen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.designer.codegen.MongoCodeGen;

/**
 * @author 代码生成器
 * @see
 * @since 1.0.0
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {})
public class CodeGenTest extends MongoCodeGen {
    @Value("classpath:model/datauth/90010201_DataGroup.json")
    private Resource dataGroupResource;

    @Value("classpath:model/datauth/90010202_AgencyDataGroup.json")
    private Resource agencyDataGroupResource;

    @Value("classpath:model/datauth/90010203_UserDataGroup.json")
    private Resource userDataGroupResource;

    @Value("classpath:model/datauth/90010204_AgencyData.json")
    private Resource agencyDataResource;

    @Value("classpath:model/datauth/90010205_UserData.json")
    private Resource userDataResource;

    @Test
    public void codeGen() throws Exception{
        //生成代码
        super.gencode(dataGroupResource);
        super.gencode(agencyDataGroupResource);
        super.gencode(userDataGroupResource);
        super.gencode(agencyDataResource);
        super.gencode(userDataResource);
    }

}