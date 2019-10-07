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
package org.youi.metadata.conceptdesign.codegen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.designer.codegen.MongoCodeGen;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {})
public class CodeGenTest extends MongoCodeGen {
    @Value("classpath:model/conceptdesign/80010601_ConceptItem.json")
    private Resource modelConceptItemResource;

    @Value("classpath:model/conceptdesign/80010602_ConceptDiagram.json")
    private Resource modelConceptDiagramResource;

    @Test
    public void codeGen() throws Exception{
        //生成代码
        super.gencode(modelConceptItemResource);
        super.gencode(modelConceptDiagramResource);
    }

}
