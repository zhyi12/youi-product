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
package org.youi.product.exchange.config;

import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.outbound.AsyncAmqpOutboundGateway;
import org.springframework.integration.annotation.IntegrationComponentScan;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Configuration
@EnableRabbit
public class AmqpConfig {


    @Autowired(required = false)
    public  ConnectionFactory connectionFactory;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        return rabbitTemplate;
    }

    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(){
        return new AsyncRabbitTemplate(rabbitTemplate());
    }

    @Bean
    public AsyncAmqpOutboundGateway asyncAmqpOutboundGateway(){
        AsyncAmqpOutboundGateway asyncAmqpOutboundGateway = new AsyncAmqpOutboundGateway(asyncRabbitTemplate());

        //使用表达式设置rabbitmq 的 RoutingKey
        asyncAmqpOutboundGateway.setRoutingKeyExpressionString("'si.test.binding'");
        asyncAmqpOutboundGateway.setExchangeName("si.test.exchange");
        asyncAmqpOutboundGateway.setOutputChannelName("amqpChanel");

        return asyncAmqpOutboundGateway;
    }

}
