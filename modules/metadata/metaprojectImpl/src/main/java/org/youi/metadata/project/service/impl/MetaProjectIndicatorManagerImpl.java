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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.Record;
import org.youi.framework.core.dataobj.tree.HtmlTreeNode;
import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.core.dataobj.tree.TreeUtils;
import org.youi.framework.esb.annotation.DomainCollection;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.framework.mongo.MaxValue;
import org.youi.metadata.project.entity.MetaProjectIndicator;
import org.youi.metadata.project.mongo.MetaProjectIndicatorDao;
import org.youi.metadata.project.service.MetaProjectIndicatorManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("metaProjectIndicatorManager")
public class MetaProjectIndicatorManagerImpl implements MetaProjectIndicatorManager {

    @Autowired(required = false)
    private MetaProjectIndicatorDao metaProjectIndicatorDao;

    private final static String ID_PREFIX = "I";//指标ID的前缀
    private final static int ID_LENGTH = 12;//指标编码的长度
    private final Pattern ID_PATTERN = Pattern.compile(ID_PREFIX+"[0-9]{"+(ID_LENGTH-1)+"}");

    @Override
    @EsbServiceMapping(trancode = "8001020201",caption = "主键查询指标")
    public MetaProjectIndicator getMetaProjectIndicator(@ServiceParam(name = "id") String indicatorId) {
        return metaProjectIndicatorDao.get(indicatorId);
    }

    @Override
    @EsbServiceMapping(trancode = "8001020204",caption = "保存指标")
    public MetaProjectIndicator saveMetaProjectIndicator(MetaProjectIndicator metaProjectIndicator) {
        String id = metaProjectIndicator.getId();
        //更新指标
        boolean isUpdate = StringUtils.isNotEmpty(id) && metaProjectIndicatorDao.exists(id);
        //TODO 指标校验
        if(isUpdate){
            //设置更新时间
            metaProjectIndicator.setUpdateTime(System.currentTimeMillis());
        }else{
            metaProjectIndicator.setId(genId());//生成并设置ID
            metaProjectIndicator.setCreateTime(System.currentTimeMillis());//创建时间
        }
        return metaProjectIndicatorDao.save(metaProjectIndicator);
    }

    @Override
    @EsbServiceMapping(trancode = "8001020205",caption = "主键删除指标")
    public void removeMetaProjectIndicator(@ServiceParam(name = "id") String indicatorId) {
        metaProjectIndicatorDao.remove(indicatorId);
    }

    @Override
    @EsbServiceMapping(trancode = "8001020206",caption = "批量新增指标")
    public synchronized List<TreeNode> addTreeIndicators(
            @ServiceParam(name = "projectId") String projectId,
            @ServiceParam(name = "parentId") String parentId,
            @DomainCollection(name = "indicators", domainClazz = MetaProjectIndicator.class)
                    List<MetaProjectIndicator> metaProjectIndicators) {
        Map<String,String> treeIdMap = new HashMap<>();
        Integer idSequence = findNextIdSequence();//获取当前最大的ID序号
        //生成ID、设置projectId
        for(MetaProjectIndicator metaProjectIndicator:metaProjectIndicators){
            String id = buildId(idSequence++);
            treeIdMap.put(metaProjectIndicator.getId(),id);//设置匹配规则
            metaProjectIndicator.setId(id);
            metaProjectIndicator.setMetaProjectId(projectId);
        }
        //替换父节点ID
        metaProjectIndicators.forEach(metaProjectIndicator -> {
            if(StringUtils.isNotEmpty(metaProjectIndicator.getParentId())
                    && treeIdMap.containsKey(metaProjectIndicator.getParentId())){
                metaProjectIndicator.setParentId(treeIdMap.get(metaProjectIndicator.getParentId()));
            }else{
                metaProjectIndicator.setParentId(parentId);//
            }
        });
        metaProjectIndicatorDao.saveAll(metaProjectIndicators);
        return buildTree(metaProjectIndicators);
    }

    @Override
    @EsbServiceMapping(trancode = "8001020207",caption = "获取项目的指标树")
    public List<TreeNode> getProjectIndicatorTree(@ServiceParam(name = "metaProjectId") String metaProjectId) {
        return buildTree(metaProjectIndicatorDao.findByMetaProjectId(metaProjectId));
    }

    /**
     * 构建指标树
     * @param metaProjectIndicators
     * @return
     */
    private List<TreeNode> buildTree(List<MetaProjectIndicator> metaProjectIndicators){
        return TreeUtils.listToHtmlTree(metaProjectIndicators,"","",(treeNode -> {
            Domain domain = treeNode.getDomain();
            if(domain instanceof MetaProjectIndicator){
                Record datas = new Record();
                datas.put("id",((MetaProjectIndicator) domain).getId());
                datas.put("project-id",((MetaProjectIndicator) domain).getMetaProjectId());
                ((HtmlTreeNode)treeNode).setDatas(datas);
            }
        })).getChildren();
    }

    /**
     * 获取最大的指标ID序号
     * @return
     */
    private synchronized Integer findNextIdSequence(){
        MaxValue maxValue = metaProjectIndicatorDao.getPropertyMaxValue("id", MaxValue.class);
        //
        if(maxValue==null){
            return 1;
        }
        //其他格式编码的ID
        if(!ID_PATTERN.matcher(maxValue.getValue().toString()).find()){
            return 1;
        }
        return Integer.parseInt(maxValue.getValue().toString().substring(ID_PREFIX.length()))+1;
    }

    /**
     * 生成指标ID
     * @return
     */
    private synchronized String genId(){
        Integer curSequence = findNextIdSequence();
        return genId(curSequence);
    }

    /**
     * 生成指标ID
     * 如果已经存在，则序号加一
     * @param curSequence
     * @return
     */
    private synchronized String genId(Integer curSequence){
        String id = buildId(curSequence);
        //验证数据库是否存在
        if(metaProjectIndicatorDao.exists(id)){
            return genId(curSequence++);
        }
        return id;
    }

    /**
     *
     * 构建ID
     * @param curSequence
     * @return
     */
    private synchronized String buildId(Integer curSequence){
        return ID_PREFIX+ StringUtils.leftPad(curSequence.toString(),ID_LENGTH-ID_PREFIX.length(),"0");
    }
}
