/*
 * Copyright 2018-2024 the original author or authors.
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
package org.youi.scheduler.common.model;

import org.youi.framework.core.dataobj.Domain;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class ServiceJobExecution implements Domain {

    private static final long serialVersionUID = -8058282700925258156L;

    private String serviceTaskCode;//任务标识

    private String fireTime;//启动时间

    private String prevFireTime;//上次执行时间

    private String nextFireTime;//下次执行时间

    public String getServiceTaskCode() {
        return serviceTaskCode;
    }

    public ServiceJobExecution setServiceTaskCode(String serviceTaskCode) {
        this.serviceTaskCode = serviceTaskCode;
        return this;
    }

    public String getFireTime() {
        return fireTime;
    }

    public ServiceJobExecution setFireTime(String fireTime) {
        this.fireTime = fireTime;
        return this;
    }

    public String getPrevFireTime() {
        return prevFireTime;
    }

    public ServiceJobExecution setPrevFireTime(String prevFireTime) {
        this.prevFireTime = prevFireTime;
        return this;
    }

    public String getNextFireTime() {
        return nextFireTime;
    }

    public ServiceJobExecution setNextFireTime(String nextFireTime) {
        this.nextFireTime = nextFireTime;
        return this;
    }
}
