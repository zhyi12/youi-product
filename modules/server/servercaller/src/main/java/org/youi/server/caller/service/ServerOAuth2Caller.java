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
package org.youi.server.caller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.youi.framework.services.data.ResContext;
import org.youi.framework.util.StringUtils;
import org.youi.server.caller.config.ServerCallerProperties;
import org.youi.server.caller.model.AccessToken;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class ServerOAuth2Caller {

    @Autowired(required = false)
    private ServerCallerProperties serverCallerProperties;

    @Autowired
    private ServerRestTemplate serverRestTemplate;

    @Autowired
    private ServerOAuth2Connector serverOAuth2Connector;

    /**
     * 服务调用
     * @return
     */
    public ResContext doService(String serverName, String servicesName, String serviceName, MultiValueMap params){
        String url = StringUtils.arrayToDelimitedString(new String[]{
                serverCallerProperties.getApiBaseUri(),
                serverName,
                "services",
                servicesName,
                serviceName+".json"
        },"/");

        String authorization = "Bearer "+serverOAuth2Connector.connect();

        MultiValueMap  valueMap = new LinkedMultiValueMap();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, authorization);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        HttpEntity entity = new HttpEntity(valueMap, headers);

        ResponseEntity<String> responseEntity = serverRestTemplate.exchange(url, HttpMethod.GET,entity,String.class);
        return new ResContext();
    }

}
