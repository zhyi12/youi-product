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

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youi.framework.core.dataobj.tree.HtmlTreeNode;
import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.ConditionCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.OrderCollection;
import org.youi.framework.esb.annotation.ServiceParam;

import org.youi.framework.util.StringUtils;
import org.youi.metadata.conceptdesign.Constant;
import org.youi.metadata.conceptdesign.entity.ConceptDiagram;
import org.youi.metadata.conceptdesign.mongo.ConceptDiagramDao;
import org.youi.metadata.conceptdesign.service.ConceptDiagramManager;
import org.youi.tools.flow.FlowRender;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("conceptDiagramManager")
public class ConceptDiagramManagerImpl implements ConceptDiagramManager{

    @Autowired(required = false)
	private ConceptDiagramDao conceptDiagramDao;

    private FlowRender flowRender = new FlowRender();

    /**
     * setter
     * @param conceptDiagramDao
     */
    public void setConceptDiagramDao(ConceptDiagramDao conceptDiagramDao) {
        this.conceptDiagramDao = conceptDiagramDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8001060201",caption="列表概念模型图")
    @Override
    public List<ConceptDiagram> getConceptDiagrams(
    	@ConditionCollection(domainClazz=ConceptDiagram.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return conceptDiagramDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8001060202",caption="主键查询概念模型图")
    @Override
    public ConceptDiagram getConceptDiagram(@ServiceParam(name="id") String id) {
    	return conceptDiagramDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8001060203",caption="分页查询概念模型图")
    @Override
	public PagerRecords getPagerConceptDiagrams(Pager pager,//分页条件
			@ConditionCollection(domainClazz=ConceptDiagram.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = conceptDiagramDao.complexFindByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8001060204",caption="保存概念模型图")
    @Override
    public ConceptDiagram saveConceptDiagram(ConceptDiagram o){
//    	String conceptDiagramId = o.getConceptDiagramId();
//    	boolean isUpdate = StringUtils.isNotEmpty(conceptDiagramId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
    	return conceptDiagramDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8001060205",caption="主键删除概念模型图")
    @Override
    public void removeConceptDiagram(@ServiceParam(name="id") String id){
    	conceptDiagramDao.remove(id);
    }

    @EsbServiceMapping(trancode="8001060206",caption="获取模块的概念模型树")
    public List<TreeNode> getConceptDiagramTree(@ServiceParam(name="moduleId") String moduleId){
        List<TreeNode> treeNodes = new ArrayList<>();
        //数据项节点
        HtmlTreeNode dataItemNode = buildItemTreeNode(moduleId,Constant.CONCEPT_ITEM_DATA_ITEMS,"数据项");
        //实体节点
        HtmlTreeNode entitiesNode = buildItemTreeNode(moduleId,Constant.CONCEPT_ITEM_ENTITIES,"实体");
        //关联关系节点
        HtmlTreeNode relationshipsNode = buildItemTreeNode(moduleId,Constant.CONCEPT_ITEM_RELATIONSHIPS,"关联关系");

        treeNodes.add(dataItemNode);
        treeNodes.add(entitiesNode);
        treeNodes.add(relationshipsNode);
        return treeNodes;
    }

    @EsbServiceMapping(trancode="8001060207",caption="获取模块的概念模型树")
    public void saveModuleDiagramContent(@ServiceParam(name="moduleId") String moduleId,
                            @ServiceParam(name="content") String content){
        ConceptDiagram conceptDiagram = new ConceptDiagram();
        conceptDiagram.buildKey(moduleId);
        //TODO xml校验
        conceptDiagram.setContent(content);
        conceptDiagramDao.save(conceptDiagram);
    }

    @EsbServiceMapping(trancode="8001060208",caption="获取模块的概念模型xml")
    public String getModuleDiagramContent(@ServiceParam(name="moduleId") String moduleId){
        ConceptDiagram conceptDiagram = new ConceptDiagram();
        conceptDiagram.buildKey(moduleId);
        conceptDiagram = conceptDiagramDao.get(conceptDiagram.getId());
        if(conceptDiagram!=null){
            return flowRender.renderHtml(conceptDiagram.getContent());
        }
        return "";
    }

    /**
     *
     * @param moduleId
     * @param group
     * @param text
     * @return
     */
    private HtmlTreeNode buildItemTreeNode(String moduleId,String group,String text){
        HtmlTreeNode itemTreeNode = new HtmlTreeNode(group+moduleId,text);
        itemTreeNode.setGroup(group);
        itemTreeNode.setIcon(group);
        return itemTreeNode;
    }
}
