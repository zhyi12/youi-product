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

import org.springframework.messaging.Message;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class TcpServiceFactory {

    /**
     * 接收 tcp 消息，返回相应响应信息
     * @param message
     * @return
     */
    public String  doService(Message<byte[]> message){
        String msg = new String(message.getPayload());
        System.out.println(msg);
        return msg.toUpperCase();
    }
}
