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
package org.youi.datauth.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.youi.datauth.entity.AgencyData;
import org.youi.datauth.vo.AgencyDataVO;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@FeignClient(name = "base-services")
public interface DataAuthServiceClient {

    @RequestMapping(value = "/services/dataAuthService/getLoginAgencyDataIds.json", method = RequestMethod.GET)
    AgencyData getLoginAgencyDataIds(@RequestParam(name = "authDataType") String authDataType);
}
