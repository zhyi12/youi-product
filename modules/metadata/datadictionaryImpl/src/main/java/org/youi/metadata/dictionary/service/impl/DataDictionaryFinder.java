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
package org.youi.metadata.dictionary.service.impl;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.youi.dataquery.engine.DataQueryConstants;
import org.youi.dataquery.engine.service.IQueryService;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.core.dataobj.tree.HtmlTreeNode;
import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.esb.DataAccesses;
import org.youi.framework.esb.annotation.*;
import org.youi.framework.util.StringUtils;
import org.youi.metadata.dictionary.Constant;
import org.youi.metadata.dictionary.entity.DataResource;
import org.youi.metadata.dictionary.service.IDataDictionaryFinder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 数据字典查找器
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("dataDictionaryFinder")
public class DataDictionaryFinder implements IDataDictionaryFinder{

    @Autowired(required = false)
    private IQueryService queryService;

    private final static String RESOURCE_TREE_ID_SPLIT = "|";//资源树ID的分隔符

    @Override
    @EsbServiceMapping(trancode = "8001030501",caption = "查询资源数据库的catalog集合")
    public List<Item> findDataCatalogs() {
        return buildItems(queryService.queryCatalogs());
    }

    @Override
    @EsbServiceMapping(trancode = "8001030502",caption = "根据catalog获取schemas集合")
    public List<Item> findSchemas(@ServiceParam(name = "catalog") String catalog) {
        return buildItems(queryService.querySchemas(catalog));
    }

    @Override
    @EsbServiceMapping(trancode = "8001030503",caption = "根据catalog和schema获取table集合")
    public List<Item> findTables(
            @ServiceParam(name = "catalog") String catalog, @ServiceParam(name = "schema") String schema) {
        return buildItems(queryService.queryTables(catalog,schema));
    }

    @Override
    @EsbServiceMapping(trancode = "8001030504",caption = "根据catalog,schem,tableName 获取表的列集合")
    public List<Item> findTableColumns(
            @ServiceParam(name = "catalog") String catalog,
            @ServiceParam(name = "schema") String schema,
            @ServiceParam(name = "tableName") String tableName) {
        return queryService.queryTableColumns(catalog,schema,tableName);
    }

    @Override
    @EsbServiceMapping(trancode = "8001030504",caption = "分级获取数据资源树",
            dataAccesses = {@DataAccess(property = Constant.AUTH_DATA_PROP_DATASOURCE,name = "datasource")})
    public List<TreeNode> getDataSourceIteratorTree(
            @ServiceParam(name = "id")String parentId,
            @DataAccessParam(property = Constant.AUTH_DATA_PROP_DATASOURCE)DataAccesses catalogDataAccesses){
        if(StringUtils.isEmpty(parentId)){
            List<String> queryCatalogs = new ArrayList<>(queryService.queryCatalogs());
            if(catalogDataAccesses!=null && ArrayUtils.isNotEmpty(catalogDataAccesses.getDataIds())){
                //数据权限过滤
                queryCatalogs.removeIf(queryCatalog->ArrayUtils.indexOf(catalogDataAccesses.getDataIds(),queryCatalog)==-1);
            }
            return buildTreeNodes(buildItems(queryCatalogs),"",DataQueryConstants.DATA_RESOURCE_CATALOG);
        }
        String[] paths = parentId.split("\\"+RESOURCE_TREE_ID_SPLIT);
        if(paths.length==1){
            return buildTreeNodes(buildItems(queryService.querySchemas(paths[0])),
                    parentId+RESOURCE_TREE_ID_SPLIT,DataQueryConstants.DATA_RESOURCE_SCHEMA);
        }else if(paths.length==2){
            return buildTreeNodes(buildItems(queryService.queryTables(paths[0],paths[1])),
                    parentId+RESOURCE_TREE_ID_SPLIT,"data-"+DataQueryConstants.DATA_RESOURCE_TABLE);
        }else if(paths.length==3){
            return buildTreeNodes(queryService.queryTableColumns(paths[0],paths[1],paths[2]),
                    parentId+RESOURCE_TREE_ID_SPLIT,DataQueryConstants.DATA_RESOURCE_TABLE_COLUMN);
        }
        return new ArrayList<>();
    }

    /**
     * 构建树节点
     * @param names
     * @param prefix
     * @param group
     * @return
     */
    private List<TreeNode> buildTreeNodes(List<Item> names,String prefix,String group) {
        List<TreeNode> treeNodes = new ArrayList<>();
        if(!CollectionUtils.isEmpty(names)){
            names.forEach(name->{
                String id = prefix+name.getId();
                HtmlTreeNode htmlTreeNode = new HtmlTreeNode(id,StringUtils.findNotEmpty(name.getText(),name.getId()));
                htmlTreeNode.setGroup(group);
                htmlTreeNode.getDatas().put("id",name.getId());
                if(DataQueryConstants.DATA_RESOURCE_TABLE_COLUMN.equals(group)){
                    htmlTreeNode.setSrc("NOT");
                }
                treeNodes.add(htmlTreeNode);
            });
        }
        return treeNodes;
    }

    /**
     * TODO 智能匹配中文
     * @param names
     * @return
     */
    private List<Item> buildItems(List<String> names) {
        List<Item> items = new ArrayList<>();
        if(!CollectionUtils.isEmpty(names)){
            names.forEach(name->{
                items.add(new Item(name,name));
            });
        }
        return items;
    }

}
