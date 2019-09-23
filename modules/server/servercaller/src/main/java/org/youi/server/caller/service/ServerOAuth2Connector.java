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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.NamedThreadLocal;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.youi.server.caller.config.ServerCallerProperties;
import org.youi.server.caller.model.AccessToken;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class ServerOAuth2Connector {

    private final static Log logger = LogFactory.getLog(ServerOAuth2Connector.class);

    @Autowired
    private ServerRestTemplate serverRestTemplate;

    @Autowired
    private ServerSecurityService serverSecurityService;

    @Autowired(required = false)
    private ServerCallerProperties serverCallerProperties;

    @Value("${spring.application.name}")
    private String appName;

    private static final ThreadLocal<AccessToken> accessTokenThreadLocal =
            new NamedThreadLocal<>("access token");

    /**
     *
     */
    public AccessToken doOAuth2Login(){
        String clientSecret = serverSecurityService.encodePassword(appName);
        //
        String code = getOAuth2Code(appName,clientSecret);//获取code
        if(code!=null){
            //获取token
            AccessToken accessToken = getOAuth2Token(appName,clientSecret,code);
            //TODO token过期判断，刷新token
            if(accessToken!=null){
                logger.info("token 获取完成");
                return accessToken;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String getOAuth2Code(String clientId,String clientSecret){
        String authorizeCodeUrl = serverCallerProperties.getApiBaseUri()
                +"/uaa/oauth/authorize?grant_type=password&response_type=code&redirect_uri=/oauth/code&client_id={clientId}&client_secret={clientSecret}";

        Map<String,String> params = new HashMap<>();
        params.put("clientId",clientId);
        params.put("clientSecret",clientSecret);

        String loginBasicKey = serverCallerProperties.getServerPrefix()+clientId+":"+clientSecret;
        String authorization = "Basic "+ Base64.getEncoder().encodeToString(loginBasicKey.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, authorization);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity(new LinkedMultiValueMap(), headers);

        ResponseEntity<String> result = serverRestTemplate.exchange(authorizeCodeUrl, HttpMethod.GET, entity, String.class, params);
        return result.getBody();
    }

    /**
     *
     * @return
     */
    public AccessToken getOAuth2Token(String clientId,String clientSecret,String code){
        MultiValueMap  valueMap = new LinkedMultiValueMap();
        HttpHeaders headers = new HttpHeaders();
        String authorization = "Basic "+Base64.getEncoder().encodeToString((clientId+":123456").getBytes());
        headers.add(HttpHeaders.AUTHORIZATION, authorization);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        HttpEntity entity = new HttpEntity(valueMap, headers);
        String authorizeCodeUrl =serverCallerProperties.getApiBaseUri()+"/uaa/oauth/token?grant_type=authorization_code&redirect_uri=/oauth/code&code="+code;

        ResponseEntity<AccessToken> tokenResponseEntity = serverRestTemplate.exchange(authorizeCodeUrl, HttpMethod.POST,entity,AccessToken.class);
        return tokenResponseEntity.getBody();
    }

    /**
     * 连接oauth2 执行认证
     * @return
     */
    public String connect() {
        AccessToken accessToken = accessTokenThreadLocal.get();
        if(accessToken==null){
            accessToken = doOAuth2Login();
            accessTokenThreadLocal.set(accessToken);
        }
        return accessToken.getAccess_token();
    }
}
