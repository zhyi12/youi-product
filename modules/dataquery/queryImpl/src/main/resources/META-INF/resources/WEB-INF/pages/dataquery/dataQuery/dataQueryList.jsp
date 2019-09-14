<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据查询管理">
	
	<youi:subpage height="150" src="/page/${_pagePath}/dataQueryEdit.html?id={id}" subpageId="dataQuery_edit" caption="修改数据查询" type="dialog"/>
	<youi:subpage height="150" src="/page/${_pagePath}/dataQueryEdit.html" subpageId="dataQuery_add" caption="添加数据查询" type="dialog"/>
	
	<youi:grid id="grid_dataQuery" src="/esb/web/dataQueryManager/getPagerDataQuerys.json" 
		removeSrc="/esb/web/dataQueryManager/removeDataQuery.json" idKeys="id"
		showNum="true" showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="name"  caption="i18n.dataQuery.name"/>
			<youi:fieldText property="caption"  caption="i18n.dataQuery.caption"/>
			<youi:fieldText property="sqlExpression"  caption="i18n.dataQuery.sqlExpression"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" property="name"  caption="i18n.dataQuery.name"/>
		<youi:gridCol width="15%" property="caption"  caption="i18n.dataQuery.caption"/>

		<youi:gridCol width="15%" property="sqlExpression"  caption="i18n.dataQuery.sqlExpression"/>
		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改数据查询弹出页 -->
	<youi:func name="grid_dataQuery_edit" params="dom,options,record">
		$elem('subpage_dataQuery_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增数据查询弹出页 -->
	<youi:func name="grid_dataQuery_add" params="dom,options">
		$elem('subpage_dataQuery_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑数据查询的subpage内容变化回调函数 -->
	<youi:func name="subpage_dataQuery_edit_change" params="record">
		$elem('grid_dataQuery',pageId).grid('refresh');
	</youi:func>
	<!-- 新增数据查询的subpage内容变化回调函数 -->
	<youi:func name="subpage_dataQuery_add_change" params="record">
		$elem('grid_dataQuery',pageId).grid('refresh');
	</youi:func>
	
</youi:page>