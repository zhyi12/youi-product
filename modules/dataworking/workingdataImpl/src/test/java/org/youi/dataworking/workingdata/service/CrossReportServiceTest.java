package org.youi.dataworking.workingdata.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.metadata.common.model.CrossReport;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {ServiceTestConfig.class})
public class CrossReportServiceTest {

    @Autowired
    private CrossReportService crossReportService;

    @Test
    public void saveWorkingDatasFromCrossReport() {
        //交叉表、立方体集合
        CrossReport crossReport = new CrossReport();//交叉表

        //
//        crossReportService.saveWorkingDatasFromCrossReport(crossReport,dataCubes);
    }
}