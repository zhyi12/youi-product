<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据列管理">
	
	<youi:subpage height="150" src="/page/${_pagePath}/dataTableColumnEdit.html?id={id}" subpageId="dataTableColumn_edit" caption="修改数据列" type="dialog"/>
	<youi:subpage height="150" src="/page/${_pagePath}/dataTableColumnEdit.html" subpageId="dataTableColumn_add" caption="添加数据列" type="dialog"/>
	
	<youi:grid id="grid_dataTableColumn" src="/esb/web/dataTableColumnManager/getPagerDataTableColumns.json" 
		removeSrc="/esb/web/dataTableColumnManager/removeDataTableColumn.json" idKeys="id"
		showNum="true" showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="dataResourceId"  caption="i18n.dataTableColumn.dataResourceId"/>
			<youi:fieldText property="columnName"  caption="i18n.dataTableColumn.columnName"/>
			<youi:fieldText property="tableName"  caption="i18n.dataTableColumn.tableName"/>
			<youi:fieldText property="dataItemId"  caption="i18n.dataTableColumn.dataItemId"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" property="dataResourceId"  caption="i18n.dataTableColumn.dataResourceId"/>
		<youi:gridCol width="15%" property="columnName"  caption="i18n.dataTableColumn.columnName"/>
		<youi:gridCol width="15%" property="tableName"  caption="i18n.dataTableColumn.tableName"/>
		<youi:gridCol width="15%" property="dataItemId"  caption="i18n.dataTableColumn.dataItemId"/>

		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改数据列弹出页 -->
	<youi:func name="grid_dataTableColumn_edit" params="dom,options,record">
		$elem('subpage_dataTableColumn_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增数据列弹出页 -->
	<youi:func name="grid_dataTableColumn_add" params="dom,options">
		$elem('subpage_dataTableColumn_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑数据列的subpage内容变化回调函数 -->
	<youi:func name="subpage_dataTableColumn_edit_change" params="record">
		$elem('grid_dataTableColumn',pageId).grid('refresh');
	</youi:func>
	<!-- 新增数据列的subpage内容变化回调函数 -->
	<youi:func name="subpage_dataTableColumn_add_change" params="record">
		$elem('grid_dataTableColumn',pageId).grid('refresh');
	</youi:func>
	
</youi:page>