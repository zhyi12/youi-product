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
package org.youi.quartz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.youi.framework.services.caller.ServiceCaller;
import org.youi.scheduler.common.IRestServiceCaller;
import org.youi.server.caller.service.ServerOAuth2Caller;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class RestServiceCaller implements IRestServiceCaller{

    @Autowired(required = false)
    private ServerOAuth2Caller serviceCaller;

    @Override
    public void callService(String serverName, String servicesName, String serviceName, MultiValueMap params) {
        try {
            serviceCaller.doService(serverName,servicesName,serviceName,params);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
