/*
 * YOUI框架
 * Copyright 2016 the original author or authors.
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
package org.youi.dataquery.exporter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.integration.expression.ValueExpression;
import org.springframework.integration.http.inbound.CrossOrigin;
import org.springframework.integration.http.inbound.HttpRequestHandlingMessagingGateway;
import org.springframework.integration.http.inbound.RequestMapping;
import org.springframework.util.MultiValueMap;
import org.youi.framework.context.annotation.Module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Configuration("dataquery.exporter.config")
@Module(name = "dataquery.exporter",caption = "查询接口")
public class ModuleConfig {

    @Autowired(required = false)
    private CrossOrigin crossOrigin;

    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();



    /**
     *
     * @return
     */
    @Bean
    public RequestMapping dataQueryRequestMapping(){
        RequestMapping requestMapping = new RequestMapping();
        requestMapping.setPathPatterns(Constant.API_DATA_QUERY_PATTERN);
        requestMapping.setMethods(HttpMethod.POST,HttpMethod.GET);
        return requestMapping;
    }

    /**
     *
     * @return
     */
    @Bean
    public HttpRequestHandlingMessagingGateway dataApiGateway() {
        HttpRequestHandlingMessagingGateway httpGateway = new HttpRequestHandlingMessagingGateway();

        httpGateway.setRequestChannelName("start");
        httpGateway.setRequestMapping(dataQueryRequestMapping());
        httpGateway.setConvertExceptions(true);
        httpGateway.setErrorChannelName("exceptionChannel");

        httpGateway.setReplyTimeout(30000);
        httpGateway.setRequestTimeout(30000);
        httpGateway.setRequestPayloadTypeClass(MultiValueMap.class);

        httpGateway.setCrossOrigin(crossOrigin);//cross策略

        Map<String, Expression> headerExpressions = new HashMap<>();

        headerExpressions.put("servicesName", EXPRESSION_PARSER.parseExpression("'dataQueryService'"));
        headerExpressions.put("serviceName", EXPRESSION_PARSER.parseExpression("'query'"));
        headerExpressions.put(Constant.PARAM_QUERY_NAME, EXPRESSION_PARSER.parseExpression("#pathVariables.queryName"));

        headerExpressions.put("Content-Type", new ValueExpression<>("application/json"));
        headerExpressions.put("Content-Encoding", new ValueExpression<>("UTF-8"));

        httpGateway.setHeaderExpressions(headerExpressions);

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(jsonMessageConverter());
        messageConverters.add(dataQueryHttpMessageConverter());
        httpGateway.setMessageConverters(messageConverters);
        return httpGateway;
    }

    @Bean
    public MappingJackson2HttpMessageConverter jsonMessageConverter(){
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        //Registering Hibernate5Module to support lazy objects
        mapper.registerModule(new Hibernate5Module());
        messageConverter.setObjectMapper(mapper);
        return messageConverter;
    }


    @Bean
    public DataQueryHttpMessageConverter dataQueryHttpMessageConverter(){
        return new DataQueryHttpMessageConverter();
    }

}
