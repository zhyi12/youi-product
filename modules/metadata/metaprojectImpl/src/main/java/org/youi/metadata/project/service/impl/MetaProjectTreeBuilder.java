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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.youi.framework.core.dataobj.Domain;
import org.youi.framework.core.dataobj.Record;
import org.youi.framework.core.dataobj.tree.HtmlTreeNode;
import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.core.dataobj.tree.TreeNodeConfig;
import org.youi.framework.core.dataobj.tree.TreeUtils;
import org.youi.framework.util.StringUtils;
import org.youi.metadata.common.IMetaParentFinderAdapter;
import org.youi.metadata.project.entity.MetaObjectNode;

import java.util.*;

/**
 *
 * 元数据树构建器
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Component
public class MetaProjectTreeBuilder implements ApplicationContextAware{

    private List<IMetaParentFinderAdapter> metaParentFinderAdapters;

    public void setMetaParentFinderAdapters(List<IMetaParentFinderAdapter> metaParentFinderAdapters) {
        this.metaParentFinderAdapters = metaParentFinderAdapters;
    }

    /**
     * 针对数量较少metaObjectNodes的节点创建树型结构（用户分层创建树节点，可以多层一起创建）
     * @param topMetaObjectName
     * @param metaObjectNodes
     * @return
     */
    public List<TreeNode> buildTreeNodes(String topMetaObjectName, List<MetaObjectNode> metaObjectNodes){
        //树节点集合
        Map<String,TreeNode> treeNodeMap = new LinkedHashMap<>();
        for(MetaObjectNode metaObjectNode:metaObjectNodes){
            treeNodeMap.put(metaObjectNode.getId(),buildTreeNode(metaObjectNode));
        }

        List<TreeNode> treeNodes = new ArrayList<>();//

        for(MetaObjectNode metaObjectNode:metaObjectNodes){
            TreeNode treeNode = treeNodeMap.get(metaObjectNode.getId());
            if(topMetaObjectName.equals(metaObjectNode.getMetaObjectName())){
                treeNodes.add(treeNode);//添加到顶级节点到输出数组
            }else{
                //查找直接父节点
                TreeNode parentNode = findDirectParentNode(metaObjectNode,treeNodeMap);
                if(parentNode!=null){
                    parentNode.addChild(treeNode);
                }
            }
        }
        //增加辅助节点
        return processTreeNodes(topMetaObjectName,treeNodes);
    }

    /**
     *
     * @param metaObjectName
     * @param treeNodes
     * @return
     */
    private List<TreeNode> processTreeNodes(String metaObjectName,List<TreeNode> treeNodes) {
        treeNodes.forEach(treeNode -> {
            Domain domain = treeNode.getDomain();
            if(domain instanceof MetaObjectNode){
                if(hasChild(treeNode)){
                    List<TreeNode> children = new ArrayList<>(treeNode.getChildren());
                    //更新子节点
                    ((HtmlTreeNode)treeNode).setChildren(processTreeNodes(((MetaObjectNode) domain).getMetaObjectName(),children));
                }
            }
        });
        //处理nodeFolderPath
        return processNodeFolderPath(metaObjectName,treeNodes);
    }

    /**
     * 处理文件夹
     * @param treeNodes
     * @return
     */
    private List<TreeNode> processNodeFolderPath(String metaObjectName,List<TreeNode> treeNodes) {
        Map<String,List<TreeNode>> folderPaths= new LinkedHashMap();
        List<TreeNode> processedTreeNodes = new ArrayList<>();
        treeNodes.forEach(treeNode -> {
            Domain domain = treeNode.getDomain();
            if(domain instanceof MetaObjectNode){
                String nodeFolderPath = ((MetaObjectNode) domain).getNodeFolderPath();
                if(StringUtils.isNotEmpty(nodeFolderPath)){
                    if(!folderPaths.containsKey(nodeFolderPath)){
                        folderPaths.put(nodeFolderPath,new ArrayList<>());
                    }
                    folderPaths.get(nodeFolderPath).add(treeNode);
                    return;
                }
            }
            processedTreeNodes.add(treeNode);
        });

        if(!CollectionUtils.isEmpty(folderPaths)){
            //文件夹节点树
            processedTreeNodes.addAll(buildFolderNodes(folderPaths,metaObjectName));
        }

        return processedTreeNodes;
    }

    /**
     * 生成文件夹节点
     * @param folderPaths
     * @param metaObjectName
     * @return
     */
    private Collection<? extends TreeNode> buildFolderNodes(Map<String, List<TreeNode>> folderPaths, String metaObjectName) {
        return TreeUtils.buildPathTree(new ArrayList<>(folderPaths.keySet()), null, new TreeNodeConfig() {
            @Override
            public void render(TreeNode treeNode) {
                String treeId = treeNode.getId();
                treeNode.setGroup("folder folder-"+metaObjectName);
                treeNode.setExpanded(true);
                if(folderPaths.containsKey(treeId)){
                    ((HtmlTreeNode)treeNode).setChildren(folderPaths.get(treeId));
                }
                treeNode.setId("F_"+treeNode.getText().hashCode());
            }
        });
    }

    /**
     *
     * @param treeNode
     * @return
     */
    private boolean hasChild(TreeNode treeNode) {
        return !CollectionUtils.isEmpty(treeNode.getChildren());
    }

    /**
     * 寻找父节点
     * @param metaObjectNode
     * @param treeNodeMap
     * @return
     */
    private TreeNode findDirectParentNode(MetaObjectNode metaObjectNode, Map<String, TreeNode> treeNodeMap) {
        Record metaObjectParents = metaObjectNode.getMetaObjectParents();
        //
        String parentMetaObjectName = findParentMetaObjectName(metaObjectNode.getMetaObjectName());
        //
        if(StringUtils.isNotEmpty(parentMetaObjectName) &&
                metaObjectParents!=null && metaObjectParents.containsKey(parentMetaObjectName)){
            return treeNodeMap.get(metaObjectParents.get(parentMetaObjectName));
        }
        return null;
    }

    /**
     * 获取父元数据对象名
     * @param metaObjectName
     * @return
     */
    private String findParentMetaObjectName(String metaObjectName){
        if(!CollectionUtils.isEmpty(metaParentFinderAdapters)){
            for(IMetaParentFinderAdapter metaParentFinderAdapter: metaParentFinderAdapters){
                if(metaParentFinderAdapter.supports(metaObjectName)){
                    return metaParentFinderAdapter.findParentMetaObjectName();
                }
            }
        }
        return null;
    }

    /**
     * 构建元数据树的元数据对象节点
     * @param metaObjectNode
     * @return
     */
    private TreeNode buildTreeNode(MetaObjectNode metaObjectNode) {
        HtmlTreeNode htmlTreeNode = new HtmlTreeNode("M_"+metaObjectNode.getId(),metaObjectNode.getText());
        htmlTreeNode.setGroup("metaObject "+metaObjectNode.getMetaObjectName());
        htmlTreeNode.setCode(metaObjectNode.getRefMetaObjectId());
        htmlTreeNode.setDomain(metaObjectNode);

        Record datas = new Record();
        datas.put("id",metaObjectNode.getId());//设置data-属性
        datas.put("project-id",metaObjectNode.getProjectId());//设置data-属性
        datas.put("ref-meta-object-id",metaObjectNode.getRefMetaObjectId());//设置data-属性
        datas.put("meta-object-name",metaObjectNode.getMetaObjectName());//设置data-属性
        htmlTreeNode.setDatas(datas);
        return htmlTreeNode;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.initMetaParentFinderAdapters(applicationContext);
    }

    /**
     *
     * @param context
     */
    private void initMetaParentFinderAdapters(ApplicationContext context) {
        if (metaParentFinderAdapters == null) {
            Map<String, IMetaParentFinderAdapter> adapterBeans =
                    BeanFactoryUtils.beansOfTypeIncludingAncestors(context, IMetaParentFinderAdapter.class, true, false);
            if (adapterBeans != null) {
                metaParentFinderAdapters = new ArrayList<>(adapterBeans.values());
                metaParentFinderAdapters.sort(new OrderComparator());
            }
        }

    }
}
