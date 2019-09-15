package org.youi.dataquery.query.service;

import org.youi.dataquery.engine.model.QueryOrder;
import org.youi.dataquery.query.entity.DataQuery;
import org.youi.dataquery.query.entity.QueryParam;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.DomainCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.OrderCollection;
import org.youi.framework.esb.annotation.ServiceParam;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhouyi on 2019/9/14.
 */
public interface DataQueryService {

    /**
     * 分页查询明细数据
     * @param pager
     * @param orders
     * @param queryId
     * @param params
     * @return
     */
    @EsbServiceMapping
    PagerRecords queryRowDataByPager(
            Pager pager,
            @OrderCollection Collection<Order> orders,
            @ServiceParam(name="id") String queryId,
            @DomainCollection(name = "params",domainClazz = QueryParam.class) List<QueryParam> params);

    @EsbServiceMapping
    DataQuery parseDataQuery(DataQuery dataQuery);
}
