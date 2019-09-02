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
package org.youi.metadata.project.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.ConditionCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.OrderCollection;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.metadata.project.entity.MetaProject;
import org.youi.metadata.project.mongo.MetaProjectDao;
import org.youi.metadata.project.service.MetaProjectManager;

import java.util.Collection;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("metaProjectManager")
public class MetaProjectManagerImpl implements MetaProjectManager {

    @Autowired
    private MetaProjectDao metaProjectDao;

    @Override
    @EsbServiceMapping
    public MetaProject getMetaProject(@ServiceParam(name = "projectId") String id) {
        return metaProjectDao.get(id);
    }

    @Override
    @EsbServiceMapping
    public PagerRecords getPagerMetaProject(
            @ServiceParam(name = "loginAgencyId", pubProperty = "agencyId") String loginAgencyId,
            @ServiceParam(name = "loginAreaId", pubProperty = "areaId") String loginAreaId,
            @ServiceParam(name = "businessKey", pubProperty = "businessKey") String businessKey,
            Pager pager,
            @ConditionCollection(domainClazz = MetaProject.class) Collection<Condition> conditions,
            @OrderCollection Collection<Order> orders) {

        //TODO 按机构、行政区划、专业（businessKey） 过滤数据

        return metaProjectDao.complexFindByPager(pager,conditions,orders);
    }

    @Override
    @EsbServiceMapping
    public MetaProject saveMetaProject(MetaProject metaProject) {
        return metaProjectDao.save(metaProject);
    }

    @Override
    @EsbServiceMapping
    public void removeMetaProject(@ServiceParam(name = "projectId") String id) {
        metaProjectDao.remove(id);
    }
}
