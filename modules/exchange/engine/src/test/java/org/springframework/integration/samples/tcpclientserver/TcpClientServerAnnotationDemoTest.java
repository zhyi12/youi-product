/*
 * Copyright 2015-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.samples.tcpclientserver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.endpoint.AbstractEndpoint;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.integration.test.context.SpringIntegrationTest;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;


/**
 * Demonstrates the use of a gateway as an entry point into the integration flow.
 * The message generated by the gateway is sent over tcp by the outbound gateway
 * to the inbound gateway. In turn the inbound gateway sends the message to an
 * echo service and the echoed response comes back over tcp and is returned to
 * the test case for verification.
 *
 * The test uses explicit transformers to convert the byte array payloads to
 * Strings.
 *
 * @author Gary Russell
 * @author Gunnar Hillert
 * @author Artem Bilan
 *
 */
@ContextConfiguration(classes = TcpClientServerAnnotationDemoTest.Config.class)
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@SpringIntegrationTest(noAutoStartup = "*tcpOutGate*")
public class TcpClientServerAnnotationDemoTest {

	@Autowired
	Config.Gateway gw;

	@Autowired
	AbstractClientConnectionFactory client;

	@Autowired
	@Qualifier("tcpClientServerAnnotationDemoTest.Config.tcpOutGate.serviceActivator")
	AbstractEndpoint outGateway;

	@Before
	public void setup() {
		if (!this.outGateway.isRunning()) {
			this.outGateway.start();
		}
	}

	@Test
	public void testHappyDay() {
		String result = gw.viaTcp("Hello world!");
		assertEquals("HELLO WORLD!", result);
	}

	@EnableIntegration
	@IntegrationComponentScan
	@Configuration
	public static class Config {

		@MessagingGateway(defaultRequestChannel = "toTcp")
		public interface Gateway {
			String viaTcp(String in);
		}

		@Bean
		@ServiceActivator(inputChannel = "toTcp")
		public MessageHandler tcpOutGate(AbstractClientConnectionFactory connectionFactory) {
			TcpOutboundGateway gate = new TcpOutboundGateway();
			gate.setConnectionFactory(connectionFactory);
			gate.setOutputChannelName("resultToString");
			return gate;
		}

		@Bean
		public MessageChannel fromTcp() {
			return new DirectChannel();
		}

		@MessageEndpoint
		public static class UnPack {
			//解包比特流
			@Transformer(inputChannel = "resultToString")
			public String convertResult(byte[] bytes) {
				return new String(bytes);
			}
		}

		@Bean
		public AbstractClientConnectionFactory clientCF() {
			return new TcpNetClientConnectionFactory("localhost", 50337);
		}

	}

}
