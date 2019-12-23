/*
 * Copyright 2018-2024 the original author or authors.
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

import org.youi.dataworking.workingdata.entity.WorkingCrossData;
import org.youi.framework.esb.annotation.DomainCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.metadata.common.model.CrossReport;
import org.youi.metadata.common.model.RowText;

import java.util.List;

/**
 * 工作库交叉表服务
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public interface CrossReportService {

    /**
     *
     * 根据交叉表对象、交叉的数据立方体集合保存工作数据
     * @param crossReport
     * @param rowTexts 二维文本数据
     * @return
     */
    @EsbServiceMapping(trancode="8006010351",caption="保存交叉表的立方体数据为交叉表数据")
    List<WorkingCrossData> saveWorkingDatasFromCrossReport(
            @ServiceParam(name = "periodId") String periodId,
            @ServiceParam(name = "loginAreaId",pubProperty = "areaId") String areaId,
            CrossReport crossReport,
            @DomainCollection(domainClazz = RowText.class,name = "texts") List<RowText> rowTexts);
}
