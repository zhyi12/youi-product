<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据项">
	
	<youi:subpage height="150" src="page/${_pagePath}/metaDataItemEdit.html?id={id}" subpageId="metaDataItem_edit" caption="修改数据项" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/metaDataItemEdit.html" subpageId="metaDataItem_add" caption="添加数据项" type="dialog"/>


	<youi:toolbar refWidgetId="grid_metaDataItem">
		<youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
		<youi:toolbarItem name="add" caption="新增" tooltips="" icon="plus"/>
		<youi:toolbarItem name="import" caption="导入" tooltips="" icon="import"/>
		<youi:toolbarItem name="export" caption="导出" tooltips="" icon="export"/>
	</youi:toolbar>

	<youi:grid id="grid_metaDataItem" src="/metadataServices/services/metaDataItemManager/getPagerMetaDataItems.json"
		removeSrc="/metadataServices/services/metaDataItemManager/removeMetaDataItem.json" idKeys="id"
		showNum="true" showCheckbox="true" reset="NOT" query="NOT" fixedFooter="true">

		<youi:gridCol width="25%" property="text" caption="项名称"/>
		<youi:gridCol width="15%" property="name" caption="英文名称"/>
		<youi:gridCol width="15%" property="columnName" caption="标准代码"/>

		<youi:gridCol width="15%" property="dataType" caption="项类型"/>
		<youi:gridCol width="15%" property="dataLength" caption="项长度"/>
		<youi:gridCol width="15%" property="dataFormat" caption="项格式"/>

		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改数据项弹出页 -->
	<youi:func name="grid_metaDataItem_edit" params="dom,options,record">
		$elem('subpage_metaDataItem_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增数据项弹出页 -->
	<youi:func name="grid_metaDataItem_add" params="dom,options">
		$elem('subpage_metaDataItem_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑数据项的subpage内容变化回调函数 -->
	<youi:func name="subpage_metaDataItem_edit_change" params="record">
		$elem('grid_metaDataItem',pageId).grid('refresh');
	</youi:func>
	<!-- 新增数据项的subpage内容变化回调函数 -->
	<youi:func name="subpage_metaDataItem_add_change" params="record">
		$elem('grid_metaDataItem',pageId).grid('refresh');
	</youi:func>
	
</youi:page>