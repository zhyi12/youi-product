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
package org.springframework.integration.samples.tcpclientserver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.handler.ServiceActivatingHandler;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.messaging.MessageHandler;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@EnableIntegration
@IntegrationComponentScan
@Configuration
public class Config {

    /**
     * 接收tcp协议请求
     */
    @Bean
    public TcpInboundGateway tcpInboundGateway(AbstractServerConnectionFactory connectionFactory){
        TcpInboundGateway tcpInboundGateway = new TcpInboundGateway();
        tcpInboundGateway.setConnectionFactory(connectionFactory);
        tcpInboundGateway.setRequestChannelName("tcp");

        return  tcpInboundGateway;
    }

    /**
     * tcp 调用的服务工厂
     */
    @Bean
    public TcpServiceFactory tcpServiceFactory(){
        return new TcpServiceFactory();
    }

    /**
     * tcpInboundGateway 的 requestChannelName ,激活服务调用操作，并返回信息
     */
    @Bean
    @ServiceActivator(inputChannel="tcp")
    public MessageHandler messageHandle() {
        return new ServiceActivatingHandler(tcpServiceFactory());
    }

    @Bean
    public AbstractServerConnectionFactory serverCF() {
        return new TcpNetServerConnectionFactory(50337);
    }


}
