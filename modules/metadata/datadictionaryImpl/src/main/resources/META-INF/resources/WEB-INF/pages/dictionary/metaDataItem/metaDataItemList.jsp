<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据项管理">
	
	<youi:subpage height="150" src="/page/${_pagePath}/metaDataItemEdit.html?id={id}" subpageId="metaDataItem_edit" caption="修改数据项" type="dialog"/>
	<youi:subpage height="150" src="/page/${_pagePath}/metaDataItemEdit.html" subpageId="metaDataItem_add" caption="添加数据项" type="dialog"/>
	
	<youi:grid id="grid_metaDataItem" src="/esb/web/metaDataItemManager/getPagerMetaDataItems.json" 
		removeSrc="/esb/web/metaDataItemManager/removeMetaDataItem.json" idKeys="id"
		showNum="true" showCheckbox="true">
		<youi:fieldLayout prefix="query">
		</youi:fieldLayout>


		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
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