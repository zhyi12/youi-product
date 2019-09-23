package org.youi.server.caller.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.youi.server.caller.ModuleConfig;

import static org.junit.Assert.*;

/**
 * Created by zhouyi on 2019/9/23.
 */

@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {ModuleConfig.class,ServiceTestConfig.class})
public class ServerOAuth2ConnectorTest {

    @Autowired
    private ServerOAuth2Connector serverOAuth2Connector;

    @Test
    public void testLogin(){
        serverOAuth2Connector.doOAuth2Login();
    }
}