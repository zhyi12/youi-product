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
package org.youi.scheduler.admin.service.impl;

import org.youi.framework.core.exception.BusException;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.scheduler.common.ISchedulerManager;
import org.youi.scheduler.common.model.ISchedulerJob;

import java.util.Collection;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public class EmptySchedulerManager implements ISchedulerManager {
    @Override
    public void saveSchedulerJob(ISchedulerJob schedulerJob) {

    }

    @Override
    public ISchedulerJob get(String schedName, String triggerGroup, String triggerName) {
        return null;
    }

    @Override
    public void pause(String triggerGroup, String triggerName) {

    }

    @Override
    public void resume(String triggerGroup, String triggerName) {

    }

    @Override
    public void remove(String schedName, String triggerGroup, String triggerName) {

    }

    @Override
    public PagerRecords getPagerSchedulerJobs(Pager pager, Collection collection, Collection collection2) throws BusException {
        return null;
    }
}
