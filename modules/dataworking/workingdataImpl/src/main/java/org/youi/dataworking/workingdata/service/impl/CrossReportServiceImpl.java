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
package org.youi.dataworking.workingdata.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youi.dataworking.workingdata.entity.WorkingCrossData;
import org.youi.dataworking.workingdata.service.CrossReportService;
import org.youi.dataworking.workingdata.service.WorkingCrossDataManager;
import org.youi.dataworking.workingdata.util.WorkingCrossDataUtils;
import org.youi.framework.esb.annotation.DomainCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.metadata.common.ICrossItemProcessorDispatcher;
import org.youi.metadata.common.model.CrossReport;
import org.youi.metadata.common.model.RowText;

import java.util.*;

/**
 * 交叉表服务
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("crossReportService")
public class CrossReportServiceImpl implements CrossReportService {

    @Autowired(required = false)
    private ICrossItemProcessorDispatcher crossItemProcessorDispatcher;

    @Autowired
    private WorkingCrossDataManager workingCrossDataManager;//交叉数据服务类

    @Override
    @EsbServiceMapping(trancode="8006010351",caption="保存交叉表的立方体数据为交叉表数据")
    public List<WorkingCrossData> saveWorkingDatasFromCrossReport(
            @ServiceParam(name = "periodId") String periodId,
            @ServiceParam(name = "loginAreaId",pubProperty = "areaId") String areaId,
            CrossReport crossReport,
            @DomainCollection(domainClazz = RowText.class,name = "texts") List<RowText> rowTexts) {
        List<WorkingCrossData> workingDatas =
                WorkingCrossDataUtils.crossReportToWorkingDatas(periodId, areaId, crossReport, rowTexts,(crossItem)->{
                    if(crossItemProcessorDispatcher!=null){
                        //处理交叉维度
                        return crossItemProcessorDispatcher.process(crossItem);
                    }
                    return crossItem;
                });
        return mergeAndSaveWorkingCrossDatas(workingDatas);
    }

    /**
     * 数据合并
     * @param workingDatas
     * @return
     */
    private List<WorkingCrossData> mergeAndSaveWorkingCrossDatas(List<WorkingCrossData> workingDatas){
        //主键集合获取历史数据
        List<String> ids = new ArrayList<>();
        Map<String,WorkingCrossData> savingDataMap = new HashMap<>();
        workingDatas.forEach(workingCrossData -> {
            ids.add(workingCrossData.getId());
            savingDataMap.put(workingCrossData.getId(),workingCrossData);
        });
        //数据库已经存在的数据
        List<WorkingCrossData> exsitsDatas = workingCrossDataManager.getWorkingCrossDataByIds(ids);

        Set<String> skips = new HashSet<>();//记录不需要更新的数据ID集合

        exsitsDatas.forEach(exsitsData -> {
            WorkingCrossData savingData = savingDataMap.get(exsitsData.getId());
            //如果数据库中存储的数据的单位换算率较小，则不更新数据，并记录数据日志
            //如果数据相同，则不更新数据
            if(savingData.getUnitRate()<exsitsData.getUnitRate() || savingData.getValue().equals(exsitsData.getValue())){
                //跳过更新的数据
                skips.add(savingData.getId());
            }
        });
        //删除不需要更新的数据
        workingDatas.removeIf(workingCrossData -> skips.contains(workingCrossData.getId()));
        //返回更新的数据
        return workingCrossDataManager.saveWorkingCrossDatas(workingDatas);
    }

}
