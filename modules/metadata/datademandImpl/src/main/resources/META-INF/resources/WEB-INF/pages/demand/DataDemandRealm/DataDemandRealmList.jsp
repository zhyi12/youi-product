<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据需求域管理">
	
	<youi:subpage height="150" src="page/${_pagePath}/DataDemandRealmEdit.html?id={id}" subpageId="DataDemandRealm_edit" caption="修改数据需求域" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/DataDemandRealmEdit.html" subpageId="DataDemandRealm_add" caption="新增数据需求域" type="dialog"/>

    <%-- 工具栏 --%>
    <youi:toolbar refWidgetId="grid_DataDemandRealm">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="save"/>
    </youi:toolbar>
    <%-- 列表数据 --%>
	<youi:grid id="grid_DataDemandRealm" src="/someServices/services/DataDemandRealmManager/getPagerDataDemandRealms.json"
		removeSrc="/someServices/services/DataDemandRealmManager/removeDataDemandRealm.json" idKeys="id"
		showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="parentId"  caption="i18n.DataDemandRealm.parentId"/>
			<youi:fieldText property="realmCaption"  caption="i18n.DataDemandRealm.realmCaption"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" property="parentId"  caption="i18n.DataDemandRealm.parentId"/>

		<youi:gridCol width="15%" property="realmCaption"  caption="i18n.DataDemandRealm.realmCaption"/>
		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改数据需求域弹出页 -->
	<youi:func name="grid_DataDemandRealm_edit" params="dom,options,record">
		$elem('subpage_DataDemandRealm_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增数据需求域弹出页 -->
	<youi:func name="grid_DataDemandRealm_add" params="dom,options">
		$elem('subpage_DataDemandRealm_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑数据需求域的subpage内容变化回调函数 -->
	<youi:func name="subpage_DataDemandRealm_edit_change" params="record">
		$elem('grid_DataDemandRealm',pageId).grid('refresh');
	</youi:func>
	<!-- 新增数据需求域的subpage内容变化回调函数 -->
	<youi:func name="subpage_DataDemandRealm_add_change" params="record">
		$elem('grid_DataDemandRealm',pageId).grid('refresh');
	</youi:func>
	
</youi:page>