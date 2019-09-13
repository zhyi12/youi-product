<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据资源管理">
	
	<youi:subpage height="150" src="/page/${_pagePath}/dataResourceEdit.html?id={id}" subpageId="dataResource_edit" caption="修改数据资源" type="dialog"/>
	<youi:subpage height="150" src="/page/${_pagePath}/dataResourceEdit.html" subpageId="dataResource_add" caption="添加数据资源" type="dialog"/>
	
	<youi:grid id="grid_dataResource" src="/esb/web/dataResourceManager/getPagerDataResources.json" 
		removeSrc="/esb/web/dataResourceManager/removeDataResource.json" idKeys="id"
		showNum="true" showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="caption"  caption="i18n.dataResource.caption"/>
			<youi:fieldText property="schema"  caption="i18n.dataResource.schema"/>
			<youi:fieldText property="catalog"  caption="i18n.dataResource.catalog"/>
			<youi:fieldText property="status"  caption="i18n.dataResource.status"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" property="caption"  caption="i18n.dataResource.caption"/>
		<youi:gridCol width="15%" property="schema"  caption="i18n.dataResource.schema"/>
		<youi:gridCol width="15%" property="catalog"  caption="i18n.dataResource.catalog"/>
		<youi:gridCol width="15%" property="status"  caption="i18n.dataResource.status"/>

		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改数据资源弹出页 -->
	<youi:func name="grid_dataResource_edit" params="dom,options,record">
		$elem('subpage_dataResource_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增数据资源弹出页 -->
	<youi:func name="grid_dataResource_add" params="dom,options">
		$elem('subpage_dataResource_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑数据资源的subpage内容变化回调函数 -->
	<youi:func name="subpage_dataResource_edit_change" params="record">
		$elem('grid_dataResource',pageId).grid('refresh');
	</youi:func>
	<!-- 新增数据资源的subpage内容变化回调函数 -->
	<youi:func name="subpage_dataResource_add_change" params="record">
		$elem('grid_dataResource',pageId).grid('refresh');
	</youi:func>
	
</youi:page>