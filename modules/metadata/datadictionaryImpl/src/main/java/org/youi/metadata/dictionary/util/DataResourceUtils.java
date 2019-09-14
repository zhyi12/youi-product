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
package org.youi.metadata.dictionary.util;

import org.springframework.util.CollectionUtils;
import org.youi.dataquery.engine.DataQueryConstants;
import org.youi.framework.core.dataobj.Record;
import org.youi.framework.core.dataobj.tree.HtmlTreeNode;
import org.youi.framework.core.dataobj.tree.TreeNode;
import org.youi.framework.util.StringUtils;
import org.youi.metadata.dictionary.entity.DataResource;
import org.youi.metadata.dictionary.entity.DataTable;
import org.youi.metadata.dictionary.entity.DataTableColumn;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyi
 * @see
 * @since 2.0.0
 */
public final class DataResourceUtils {

    /**
     * 私有构造函数
     */
    private DataResourceUtils(){
        //ignore
    }

    public static TreeNode buildDataSourceTreeNode(DataResource dataResource) {
        HtmlTreeNode treeNode = new HtmlTreeNode("D_"+dataResource.getId(),dataResource.getCaption());
        Record record = new Record();
        record.put("catalog",dataResource.getCatalog());
        record.put("schema",dataResource.getSchema());
        record.put("id",dataResource.getId());
        treeNode.setGroup("data-resource");
        treeNode.setDatas(record);
        return treeNode;
    }

    public static void addDataTableNodes(DataResource dataResource,TreeNode treeNode, List<DataTable> dataTables) {
        if(!CollectionUtils.isEmpty(dataTables)){
            dataTables.forEach(dataTable -> {
                treeNode.addChild(buildDataTableTreeNode(dataResource,dataTable));
            });
        }
    }

    /**
     *
     * @param dataResource
     * @param dataTable
     * @return
     */
    private static TreeNode buildDataTableTreeNode(DataResource dataResource,DataTable dataTable) {
        HtmlTreeNode treeNode = new HtmlTreeNode("T_"+dataTable.getId(), StringUtils.findNotEmpty(dataTable.getTableCaption(),dataTable.getTableName()));
        Record record = new Record();
        record.put("catalog",dataResource.getCatalog());
        record.put("schema",dataResource.getSchema());
        record.put("table-name",dataTable.getTableName());
        record.put("id",dataTable.getId());
        treeNode.setDatas(record);
        treeNode.setGroup("data-"+DataQueryConstants.DATA_RESOURCE_TABLE);
        treeNode.setSrc("/metadataServices/services/dataResourceService/getTableColumnTreeNodes.json?id="+dataTable.getId());
        return treeNode;
    }

    /**
     *
     * @param dataTableColumns
     * @return
     */
    public static List<TreeNode> buildDataTableColumnTreeNodes(List<DataTableColumn> dataTableColumns) {
        List<TreeNode> treeNodes = new ArrayList<>();
        if(!CollectionUtils.isEmpty(dataTableColumns)){
            dataTableColumns.forEach(dataTableColumn -> {
                treeNodes.add(buildDataTableColumnTreeNode(dataTableColumn));
            });
        }
        return treeNodes;
    }

    /**
     * 构建数据列树节点
     * @param dataTableColumn
     * @return
     */
    public static TreeNode buildDataTableColumnTreeNode(DataTableColumn dataTableColumn) {
        HtmlTreeNode treeNode = new HtmlTreeNode(dataTableColumn.getId(),dataTableColumn.getColumnName());
        treeNode.setGroup(DataQueryConstants.DATA_RESOURCE_TABLE_COLUMN);
        Record record = new Record();
        record.put("table-name",dataTableColumn.getTableName());
        record.put("column-name",dataTableColumn.getColumnName());
        treeNode.setDatas(record);
        return treeNode;
    }
}
