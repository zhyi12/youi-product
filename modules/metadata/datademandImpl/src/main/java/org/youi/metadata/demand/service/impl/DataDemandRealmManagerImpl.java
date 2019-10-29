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

import org.youi.metadata.demand.entity.DataDemandRealm;
import org.youi.metadata.demand.mongo.DataDemandRealmDao;
import org.youi.metadata.demand.service.DataDemandRealmManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("dataDemandRealmManager")
public class DataDemandRealmManagerImpl implements DataDemandRealmManager{

    @Autowired(required = false)
	private DataDemandRealmDao dataDemandRealmDao;

    /**
     * setter
     * @param dataDemandRealmDao
     */
    public void setDataDemandRealmDao(DataDemandRealmDao dataDemandRealmDao) {
        this.dataDemandRealmDao = dataDemandRealmDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8001070101",caption="列表数据需求域")
    @Override
    public List<DataDemandRealm> getDataDemandRealms(
    	@ConditionCollection(domainClazz=DataDemandRealm.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return dataDemandRealmDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8001070102",caption="主键查询数据需求域")
    @Override
    public DataDemandRealm getDataDemandRealm(@ServiceParam(name="id") String id) {
    	return dataDemandRealmDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8001070103",caption="分页查询数据需求域")
    @Override
	public PagerRecords getPagerDataDemandRealms(Pager pager,//分页条件
			@ConditionCollection(domainClazz=DataDemandRealm.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = dataDemandRealmDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8001070104",caption="保存数据需求域")
    @Override
    public DataDemandRealm saveDataDemandRealm(DataDemandRealm o){
//    	String dataDemandRealmId = o.getDataDemandRealmId();
//    	boolean isUpdate = StringUtils.isNotEmpty(dataDemandRealmId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return dataDemandRealmDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8001070105",caption="主键删除数据需求域")
    @Override
    public void removeDataDemandRealm(@ServiceParam(name="id") String id){
    	dataDemandRealmDao.remove(id);
    }

    @EsbServiceMapping(trancode="8001070106",caption="数据需求域树结构数据")
    public List<TreeNode> getDataDemandRealmTree(){
        List<DataDemandRealm> dataDemandRealms = this.getDataDemandRealms(null,null);
        //
        return TreeUtils.listToHtmlTree(dataDemandRealms,"","",treeNode -> {
            if(treeNode.getDomain() instanceof DataDemandRealm){
                Record datas = new Record();
                datas.put("id",treeNode.getId());

                ((HtmlTreeNode)treeNode).setDatas(datas);
            }
        }).getChildren();
    }
}
