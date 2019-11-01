package org.youi.chart.echarts;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.youi.chart.engine.IOption;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * Created by zhouyi on 2019/11/1.
 */
public abstract class AbstractEchartsOptionWriterTest {

    //
    private ObjectMapper objectMapper = new ObjectMapper();

    private static AtomicInteger CASE_INDEX = new AtomicInteger(0);

    @Value("classpath:webapp/chart/chart.html.ftl")
    private Resource chartFtlResource;//输出

    /**
     *
     * @param option
     */
    protected void writeHtmlFile(IOption option,String chartType) throws Exception{
        String value = objectMapper.writeValueAsString(option);

        String fileName = chartType+"_"+ StringUtils.leftPad(Integer.valueOf(CASE_INDEX.incrementAndGet()).toString(),3,"0");
        try(FileReader fileReader = new FileReader(chartFtlResource.getFile())){
            String tmpHtml = FileCopyUtils.copyToString(fileReader);
            tmpHtml = tmpHtml.replaceAll("\\#\\{option\\}",value);
            File outFile = new File(chartFtlResource.getFile().getParent(),fileName+".html");
            FileCopyUtils.copy(tmpHtml.getBytes(),new FileOutputStream(outFile));
        }catch (IOException e){

        }
    }

}