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
package org.youi.metadata.dictionary.service.impl;

import java.util.*;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.youi.framework.core.dataobj.cube.Item;
import org.youi.framework.core.orm.Condition;
import org.youi.framework.core.orm.Order;
import org.youi.framework.core.orm.Pager;
import org.youi.framework.core.orm.PagerRecords;
import org.youi.framework.esb.annotation.*;

import org.youi.metadata.dictionary.entity.DataResource;
import org.youi.metadata.dictionary.entity.DataTable;
import org.youi.metadata.dictionary.entity.DataTableColumn;
import org.youi.metadata.dictionary.mongo.DataResourceDao;
import org.youi.metadata.dictionary.mongo.DataTableColumnDao;
import org.youi.metadata.dictionary.mongo.DataTableDao;
import org.youi.metadata.dictionary.service.DataTableManager;
import org.youi.metadata.dictionary.service.IDataDictionaryFinder;
import org.youi.metadata.dictionary.vo.DataTableVO;

import javax.xml.crypto.Data;

/**
 * <p>系统描述: </p>
 * <p>功能描述 :</p>
 * @author 代码生成器
 * @since 1.0.0
 */
@Service("dataTableManager")
public class DataTableManagerImpl implements DataTableManager{

    @Autowired(required = false)
	private DataTableDao dataTableDao;

    @Autowired(required = false)
    private DataTableColumnDao dataTableColumnDao;

    @Autowired(required = false)
    private DataResourceDao dataResourceDao;

    @Autowired
    private IDataDictionaryFinder dataDictionaryFinder;

    @Autowired
    private DataTableSqlBuilder dataTableSqlBuilder;

    /**
     * setter
     * @param dataTableDao
     */
    public void setDataTableDao(DataTableDao dataTableDao) {
        this.dataTableDao = dataTableDao;
    }

     /**
      * 条件查询列表
      */
    @EsbServiceMapping(trancode="8001030301",caption="列表数据表")
    @Override
    public List<DataTable> getDataTables(
    	@ConditionCollection(domainClazz=DataTable.class) Collection<Condition> conditions,
    	@OrderCollection Collection<Order> orders){
    	return dataTableDao.commonQuery(conditions, orders);
    }
    /**
     * 根据主键查询
     */
    @EsbServiceMapping(trancode="8001030302",caption="主键查询数据表")
    @Override
    public DataTable getDataTable(@ServiceParam(name="id") String id) {
    	return dataTableDao.get(id);
    }
	
	@EsbServiceMapping(trancode="8001030303",caption="分页查询数据表",
            dataAccesses = {@DataAccess(name = "datasource",property = "catalog")})
    @Override
	public PagerRecords getPagerDataTables(Pager pager,//分页条件
			@ConditionCollection(domainClazz=DataTable.class) Collection<Condition> conditions,//查询条件
			@OrderCollection Collection<Order> orders) {
		PagerRecords pagerRecords = dataTableDao.complexFindByPager(pager, conditions, orders);
		return pagerRecords;
	}
    /**
     * 保存对象
     */
    @EsbServiceMapping(trancode="8001030304",caption="保存数据表")
    @Override
    public DataTable saveDataTable(DataTable o){
//    	String dataTableId = o.getDataTableId();
//    	boolean isUpdate = StringUtils.isNotEmpty(dataTableId);
//    	if(isUpdate){//修改
//    	
//    	}else{//新增
//    		
//    	}
        o.setDataResourceId(o.getCatalog()+"_"+o.getSchema());
    	return dataTableDao.save(o);
    }

    /**
     * 删除对象
     */
    @EsbServiceMapping(trancode="8001030305",caption="主键删除数据表")
    @Override
    public void removeDataTable(@ServiceParam(name="id") String id){
    	dataTableDao.remove(id);
    }

    /**
     *  从数据源同步数据库表
     * @param catalog
     * @param schema
     * @param tableNames
     */
    @EsbServiceMapping
    public void syncDataTables(
            @ServiceParam(name="catalog") String catalog,
            @ServiceParam(name="schema") String schema,
            @ServiceParam(name="tableNames") String tableNames[]){

        //数据库表集合
        List<Item> tables = dataDictionaryFinder.findTables(catalog,schema);

        //需要同步的数据表
        Map<String,Item> syncTables = new HashMap<>();
        tables.forEach(item -> {
            if(ArrayUtils.indexOf(tableNames,item.getId())!=-1){
                syncTables.put(item.getId(),item);
            }
        });

        DataResource dataResource = dataResourceDao.findByCatalogAndSchema(catalog,schema);

        if(dataResource!=null){
            List<DataTable> dataTables = dataTableDao.findByDataResourceId(dataResource.getId());
            dataTables.forEach(dataTable -> {
                if(syncTables.containsKey(dataTable.getTableName())){
                    syncTables.remove(dataTable.getTableName());//从同步集合中删除
                }
            });
            doSyncDataTables(dataResource,syncTables);
        }
    }

    public List<DataTable> findByDataResourceIdIn(String[] dataResourceIds){
        return dataTableDao.findByDataResourceIdIn(dataResourceIds);
    }

    /**
     * 执行表同步
     * @param syncTables
     */
    private void doSyncDataTables(DataResource dataResource,Map<String, Item> syncTables) {
        if(!CollectionUtils.isEmpty(syncTables)){
            List<DataTable> dataTables = new ArrayList<>();
            List<DataTableColumn> dataTableColumns = new ArrayList<>();
            syncTables.forEach((tableName,item)->{
                DataTable dataTable = new DataTable();
                dataTable.setDataResourceId(dataResource.getId());
                dataTable.setCatalog(dataResource.getCatalog());
                dataTable.setSchema(dataResource.getSchema());
                dataTable.setTableName(tableName);
                dataTable.setTableCaption(tableName);
                dataTables.add(dataTable);
                //
            });
            //批量保存数据表
            List<DataTable> savedDataTables = dataTableDao.saveAll(dataTables);
            savedDataTables.forEach(dataTable -> {
                dataTableColumns.addAll(buildDataTableColumns(dataResource,dataTable));
            });
            //批量保存数据列
            dataTableColumnDao.saveAll(dataTableColumns);
        }
    }

    private Collection<? extends DataTableColumn> buildDataTableColumns(DataResource dataResource, DataTable dataTable) {
        List<DataTableColumn> dataTableColumns = new ArrayList<>();
        List<Item> columnItems = dataDictionaryFinder.findTableColumns(dataResource.getCatalog(),dataResource.getSchema(),dataTable.getTableName());
        columnItems.forEach(columnItem->{
            DataTableColumn dataTableColumn = new DataTableColumn();
            dataTableColumn.setColumnName(columnItem.getId());
            dataTableColumn.setTableName(dataTable.getTableName());
            dataTableColumn.setDataResourceId(dataResource.getId());
            dataTableColumns.add(dataTableColumn);
        });
        return dataTableColumns;
    }
}
