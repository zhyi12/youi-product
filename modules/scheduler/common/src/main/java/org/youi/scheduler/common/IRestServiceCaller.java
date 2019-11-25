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
package org.youi.scheduler.common;

import org.springframework.util.MultiValueMap;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public interface IRestServiceCaller {

    /**
     * 服务调用
     * @param serverName  服务名
     * @param servicesName 交易组名称
     * @param serviceName 交易名称
     * @param params 任务参数
     */
    void callService(String serverName, String servicesName, String serviceName, MultiValueMap params);
}
