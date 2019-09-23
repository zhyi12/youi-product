package org.youi.server.caller.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.youi.server.caller.ModuleConfig;

import static org.junit.Assert.*;

/**
 * Created by zhouyi on 2019/9/23.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {ModuleConfig.class,ServiceTestConfig.class})
public class ServerOAuth2CallerTest {

    @Autowired
    private ServerOAuth2Caller serverOAuth2Caller;

    @Test
    public void testDoService(){
        //metadataServices/services/dataQueryManager/getPagerDataQuerys
        String serverName = "metadataServices";
        String servicesName = "dataQueryManager";
        String serviceName = "getPagerDataQuerys";

        MultiValueMap params = new LinkedMultiValueMap();

        serverOAuth2Caller.doService(serverName,servicesName,serviceName,params);
    }
}