package org.youi.metadata.project.service;

import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.ConditionCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.OrderCollection;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.metadata.project.entity.MetaProject;

import java.util.Collection;

/**
 * Created by zhouyi on 2019/9/1.
 */
public interface MetaProjectManager {

    /**
     * 主键查询元数据项目
     * @param id
     * @return
     */
    @EsbServiceMapping
    MetaProject getMetaProject(@ServiceParam(name = "projectId") String id);

    /**
     * 分页查询元数据项目
     * @param loginAgencyId 登录机构
     * @param loginAreaId 登录机构所在的行政区划
     * @param businessKey 登录用户的业务参数（比如专业|方案组）
     * @param pager 分页参数
     * @param conditions 过滤条件
     * @param orders 排序条件
     * @return
     */
    @EsbServiceMapping
    PagerRecords getPagerMetaProject(
            @ServiceParam(name = "loginAgencyId",pubProperty = "agencyId") String loginAgencyId,
            @ServiceParam(name = "loginAreaId",pubProperty = "areaId") String loginAreaId,
            @ServiceParam(name = "businessKey",pubProperty = "businessKey") String businessKey,
            Pager pager,
            @ConditionCollection(domainClazz = MetaProject.class) Collection<Condition> conditions,
            @OrderCollection Collection<Order> orders);

    /**
     * 保存元数据项目
     */
    @EsbServiceMapping
    MetaProject saveMetaProject(MetaProject metaProject);

    /**
     * 删除元数据项目
     * @param id
     */
    @EsbServiceMapping
    void removeMetaProject(@ServiceParam(name = "projectId") String id);
}
