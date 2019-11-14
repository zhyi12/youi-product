package org.youi.xlsreport.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.xlsreport.ModuleConfig;
import org.youi.xlsreport.model.XlsReport;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zhouyi on 2019/11/14.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ModuleConfig.class)
public class XlsReportReaderTest {

    @Autowired
    private XlsReportReader xlsReportReader;

    @Value("classpath:xls/2019年7月规模以上工业主要能源产品生产情况.xlsx")
    private Resource xlsFile;

    @Test
    public void readReport() throws Exception {
        try(InputStream inputStream = xlsFile.getInputStream()){
            List<XlsReport> xlsReports =  xlsReportReader.readReport(inputStream,false);
            System.out.println(xlsReports);
        }
    }

}