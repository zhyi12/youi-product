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
package org.youi.chart.three;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@RunWith(SpringRunner.class)
@ContextConfiguration
public class MapDownloaderTest {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("classpath:areas.text")
    private Resource areasFile;

    private final static String BASE_URL = "http://xzqh.mca.gov.cn/data/";
    /**
     * 下载中国地图数据
     */
    @Test
    public void downloadChinaMap(){
        //http://xzqh.mca.gov.cn/data/632700.json
//        http://xzqh.mca.gov.cn/data/632700_Point.geojson
        //http://xzqh.mca.gov.cn/data/632700_Line.geojson
        try(FileReader inputStream = new FileReader(areasFile.getFile())){
            String[] areaIds = FileCopyUtils.copyToString(inputStream).split("\n");

            for(String areaId:areaIds){
                if(areaId.endsWith("00")){
                    downloadFile(areaId+".json");
                    downloadFile(areaId+"_Point.geojson");
                    downloadFile(areaId+"_Line.geojson");
                    System.out.println(areaId);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }catch (IOException e){

        }

    }

    @Test
    public void downloadAreaMapJson() throws Exception{
        String areaId = "quanguo";
        downloadFile(areaId+".json");
        downloadFile(areaId+"_Point.geojson");
        downloadFile(areaId+"_Line.geojson");
    }

    private void downloadFile(String uri) throws IOException{
        try {
            String jsonStr = restTemplate.getForObject(BASE_URL+uri,String.class);
            FileCopyUtils.copy(new String(jsonStr.getBytes("ISO-8859-1"),"UTF-8").getBytes(),new File(areasFile.getFile().getParentFile(),uri));
        } catch (RestClientException e) {
            System.out.println("error:"+uri);
        } catch (IOException e) {
            System.out.println("error:"+uri);
        }
    }
}
