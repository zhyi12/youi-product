/*
 * YOUI框架
 * Copyright 2018 the original author or authors.
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
package org.youi.metadata.demand.service.impl;

import java.util.List;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youi.framework.core.dataobj.Record;
import org.youi.framework.core.dataobj.tree.HtmlTreeNode;
import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.core.dataobj.tree.TreeUtils;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.ConditionCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.OrderCollection;
import org.youi.framework.esb.annotation.ServiceParam;

import org.youi.metadata.demand.entity.DataDemand;
import org.youi.metadata.demand.mongo.DataDemandDao;
import org.youi.metadata.demand.service.DataDemandManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("dataDemandManager")
public class DataDemandManagerImpl implements DataDemandManager{

    @Autowired(required = false)
	private DataDemandDao dataDemandDao;

    /**
     * setter
     * @param dataDemandDao
     */
    public void setDataDemandDao(DataDemandDao dataDemandDao) {
        this.dataDemandDao = dataDemandDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8001070201",caption="列表数据需求")
    @Override
    public List<DataDemand> getDataDemands(
    	@ConditionCollection(domainClazz=DataDemand.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return dataDemandDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8001070202",caption="主键查询数据需求")
    @Override
    public DataDemand getDataDemand(@ServiceParam(name="id") String id) {
    	return dataDemandDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8001070203",caption="分页查询数据需求")
    @Override
	public PagerRecords getPagerDataDemands(Pager pager,//分页条件
			@ConditionCollection(domainClazz=DataDemand.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = dataDemandDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8001070204",caption="保存数据需求")
    @Override
    public DataDemand saveDataDemand(DataDemand o){
//    	String dataDemandId = o.getDataDemandId();
//    	boolean isUpdate = StringUtils.isNotEmpty(dataDemandId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return dataDemandDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8001070205",caption="主键删除数据需求")
    @Override
    public void removeDataDemand(@ServiceParam(name="id") String id){
    	dataDemandDao.remove(id);
    }

    /**
     *
     * @param realmId
     * @return
     */
    @EsbServiceMapping(trancode="8001070206",caption="获取域下的需求集合")
    public List<TreeNode> getRealmDataDemandTree(@ServiceParam(name="realmId") String realmId){
        return TreeUtils.listToHtmlTree(dataDemandDao.findByRealmId(realmId),realmId,"",treeNode -> {
            Record datas = new Record();
            datas.put("id",treeNode.getId());
            //重新设置id
            //treeNode.setId("N"+realmId+treeNode.getId());
            ((HtmlTreeNode)treeNode).setDatas(datas);
        }).getChildren();
    }

}
