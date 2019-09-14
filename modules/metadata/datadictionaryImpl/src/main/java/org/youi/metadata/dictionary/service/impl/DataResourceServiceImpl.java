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
import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.esb.annotation.EsbServiceMapping;
import org.youi.framework.esb.annotation.ServiceParam;
import org.youi.metadata.dictionary.entity.DataResource;
import org.youi.metadata.dictionary.entity.DataTable;
import org.youi.metadata.dictionary.entity.DataTableColumn;
import org.youi.metadata.dictionary.service.DataResourceManager;
import org.youi.metadata.dictionary.service.DataResourceService;
import org.youi.metadata.dictionary.service.DataTableColumnManager;
import org.youi.metadata.dictionary.service.DataTableManager;
import org.youi.metadata.dictionary.util.DataResourceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
@Service("dataResourceService")
public class DataResourceServiceImpl implements DataResourceService {

    @Autowired
    private DataResourceManager dataResourceManager;

    @Autowired
    private DataTableManager dataTableManager;

    @Autowired
    private DataTableColumnManager dataTableColumnManager;

    @Override
    @EsbServiceMapping
    public List<TreeNode> getUserDataResourceTree(
            @ServiceParam(name = "roleIds") String loginRoleIds,
            @ServiceParam(name = "loginName") String loginUserId,
            @ServiceParam(name = "agencyId") String loginAgencyId) {

        String[] userDataResourceIds = {};//当前登录用户可见的数据资源

        List<DataResource> dataResources;
        List<DataTable> dataTables;
        //获取全部的数据资源
        if(ArrayUtils.isNotEmpty(userDataResourceIds)){
            dataResources = dataResourceManager.findByDataResourceIdIn(userDataResourceIds);
            dataTables = dataTableManager.findByDataResourceIdIn(userDataResourceIds);
        }else {
            dataResources = dataResourceManager.getDataResources(null,null);
            dataTables = dataTableManager.getDataTables(null,null);
        }
        //获取数据资源报表
        Map<String,List<DataTable>> dataTableMap = buildDataTableMap(dataTables);

        List<TreeNode> treeNodes = new ArrayList<>();
        if(!CollectionUtils.isEmpty(dataResources)){
            dataResources.forEach(dataResource -> {
                TreeNode treeNode = DataResourceUtils.buildDataSourceTreeNode(dataResource);
                if(dataTableMap.containsKey(dataResource.getId())){
                    DataResourceUtils.addDataTableNodes(dataResource,treeNode,dataTableMap.get(dataResource.getId()));
                }
                treeNodes.add(treeNode);
            });
        }
        return treeNodes;
    }

    @EsbServiceMapping
    public List<TreeNode> getTableColumnTreeNodes(@ServiceParam(name = "id") String dataTableId){
        DataTable dataTable= dataTableManager.getDataTable(dataTableId);
        List<DataTableColumn> dataTableColumns = dataTableColumnManager.findByDataResourceIdAndTableName(dataTable.getDataResourceId(),dataTable.getTableName());
        return DataResourceUtils.buildDataTableColumnTreeNodes(dataTableColumns);
    }

    /**
     *
     * @param dataTables
     * @return
     */
    private Map<String,List<DataTable>> buildDataTableMap(List<DataTable> dataTables) {
        Map<String,List<DataTable>> dataTableMap = new HashMap<>();
        if(!CollectionUtils.isEmpty(dataTables)){
            dataTables.forEach(dataTable -> {
                if(!dataTableMap.containsKey(dataTable.getDataResourceId())){
                    dataTableMap.put(dataTable.getDataResourceId(),new ArrayList<>());
                }
                dataTableMap.get(dataTable.getDataResourceId()).add(dataTable);
            });
        }
        return dataTableMap;
    }
}
