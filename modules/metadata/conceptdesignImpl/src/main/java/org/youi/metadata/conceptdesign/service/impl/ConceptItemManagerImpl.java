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
package org.youi.metadata.conceptdesign.service.impl;

import java.util.List;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youi.framework.core.dataobj.Domain;
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

import org.youi.metadata.conceptdesign.Constant;
import org.youi.metadata.conceptdesign.entity.ConceptItem;
import org.youi.metadata.conceptdesign.mongo.ConceptItemDao;
import org.youi.metadata.conceptdesign.service.ConceptItemManager;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("conceptItemManager")
public class ConceptItemManagerImpl implements ConceptItemManager{

    @Autowired(required = false)
	private ConceptItemDao conceptItemDao;

    /**
     * setter
     * @param conceptItemDao
     */
    public void setConceptItemDao(ConceptItemDao conceptItemDao) {
        this.conceptItemDao = conceptItemDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8001060101",caption="列表模型项")
    @Override
    public List<ConceptItem> getConceptItems(
    	@ConditionCollection(domainClazz=ConceptItem.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return conceptItemDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8001060102",caption="主键查询模型项")
    @Override
    public ConceptItem getConceptItem(@ServiceParam(name="id") String id) {
    	return conceptItemDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8001060103",caption="分页查询模型项")
    @Override
	public PagerRecords getPagerConceptItems(Pager pager,//分页条件
			@ConditionCollection(domainClazz=ConceptItem.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = conceptItemDao.findByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8001060104",caption="保存模型项")
    @Override
    public ConceptItem saveConceptItem(ConceptItem o){
//    	String conceptItemId = o.getConceptItemId();
//    	boolean isUpdate = StringUtils.isNotEmpty(conceptItemId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return conceptItemDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8001060105",caption="主键删除模型项")
    @Override
    public void removeConceptItem(@ServiceParam(name="id") String id){
    	conceptItemDao.remove(id);
    }

    @Override
    @EsbServiceMapping(trancode="8001060106",caption="获取数据资源对应的模型项")
    public List<TreeNode> getConceptItemTree(@ServiceParam(name="dataResourceId") String dataResourceId){
        List<ConceptItem>  conceptItems = conceptItemDao.getByDataResourceId(dataResourceId);
        return TreeUtils.listToHtmlTree(conceptItems, "", "",
                treeNode -> processConceptItemTreeNode(treeNode)).getChildren();
    }

    @EsbServiceMapping(trancode="8001060107",caption="保存子系统项")
    public ConceptItem saveSubSystemItem(ConceptItem o){
        o.setGroup(Constant.CONCEPT_ITEM_SUB_SYSTEM);
        return conceptItemDao.save(o);
    }

    @EsbServiceMapping(trancode="8001060108",caption="保存模块项")
    public ConceptItem saveModuleItem(ConceptItem o, @ServiceParam(name = "subSystemId") String subSystemId){
        o.setGroup(Constant.CONCEPT_ITEM_MODULE);
        o.setParentId(subSystemId);//
        return conceptItemDao.save(o);
    }

    /**
     *
     * @param treeNode
     */
    private void processConceptItemTreeNode(TreeNode treeNode) {
        Domain domain = treeNode.getDomain();
        if(domain instanceof ConceptItem && treeNode instanceof HtmlTreeNode){
            //设置icon
            ((HtmlTreeNode) treeNode).setIcon(((ConceptItem) domain).getGroup());
            Record datas = new Record();
            datas.put("id",((ConceptItem) domain).getId());
            datas.put("data-resource-id",((ConceptItem) domain).getDataResourceId());
            ((HtmlTreeNode) treeNode).setDatas(datas);
        }
    }
}
