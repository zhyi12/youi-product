<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="思维导图管理">
	
	<youi:subpage height="150" src="page/${_pagePath}/DataDemandMindMapEdit.html?dataDemandId={dataDemandId}" subpageId="DataDemandMindMap_edit" caption="修改思维导图" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/DataDemandMindMapEdit.html" subpageId="DataDemandMindMap_add" caption="新增思维导图" type="dialog"/>

    <%-- 工具栏 --%>
    <youi:toolbar refWidgetId="grid_DataDemandMindMap">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="save"/>
    </youi:toolbar>
    <%-- 列表数据 --%>
	<youi:grid id="grid_DataDemandMindMap" src="/someServices/services/DataDemandMindMapManager/getPagerDataDemandMindMaps.json"
		removeSrc="/someServices/services/DataDemandMindMapManager/removeDataDemandMindMap.json" idKeys="dataDemandId"
		showCheckbox="true">
		<youi:fieldLayout prefix="query">
		</youi:fieldLayout>


		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改思维导图弹出页 -->
	<youi:func name="grid_DataDemandMindMap_edit" params="dom,options,record">
		$elem('subpage_DataDemandMindMap_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增思维导图弹出页 -->
	<youi:func name="grid_DataDemandMindMap_add" params="dom,options">
		$elem('subpage_DataDemandMindMap_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑思维导图的subpage内容变化回调函数 -->
	<youi:func name="subpage_DataDemandMindMap_edit_change" params="record">
		$elem('grid_DataDemandMindMap',pageId).grid('refresh');
	</youi:func>
	<!-- 新增思维导图的subpage内容变化回调函数 -->
	<youi:func name="subpage_DataDemandMindMap_add_change" params="record">
		$elem('grid_DataDemandMindMap',pageId).grid('refresh');
	</youi:func>
	
</youi:page>