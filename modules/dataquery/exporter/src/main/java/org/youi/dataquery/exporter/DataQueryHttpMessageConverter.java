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
package org.youi.dataquery.exporter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Map;

import static org.youi.dataquery.exporter.Constant.PARAM_QUERY_NAME;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class DataQueryHttpMessageConverter extends FormHttpMessageConverter {


    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    /**
     * 增加PARAM_QUERY_NAME参数
     * @param clazz
     * @param inputMessage
     * @return
     * @throws IOException
     * @throws HttpMessageNotReadableException
     */
    @Override
    public MultiValueMap<String, String> read(@Nullable Class<? extends MultiValueMap<String, ?>> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        MultiValueMap<String, String> multiValueMap = super.read(clazz, inputMessage);
        if(inputMessage instanceof ServletServerHttpRequest){
            String requestURI = ((ServletServerHttpRequest) inputMessage).getServletRequest().getRequestURI();
            Map<String,String> pathParams = antPathMatcher.extractUriTemplateVariables(Constant.API_DATA_QUERY_PATTERN,requestURI);
            if(pathParams.containsKey(PARAM_QUERY_NAME)){
                multiValueMap.add(PARAM_QUERY_NAME,pathParams.get(PARAM_QUERY_NAME));//设置queryName
            }
        }
        return multiValueMap;
    }
}
