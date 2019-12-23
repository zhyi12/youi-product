<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="物理表管理">
	
	<youi:subpage height="150" src="page/${_pagePath}/PhysicalTableEdit.html?id={id}" subpageId="PhysicalTable_edit" caption="修改物理表" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/PhysicalTableEdit.html" subpageId="PhysicalTable_add" caption="新增物理表" type="dialog"/>

    <%-- 工具栏 --%>
    <youi:toolbar refWidgetId="grid_PhysicalTable">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="save"/>
    </youi:toolbar>
    <%-- 列表数据 --%>
	<youi:grid id="grid_PhysicalTable" src="/someServices/services/PhysicalTableManager/getPagerPhysicalTables.json"
		removeSrc="/someServices/services/PhysicalTableManager/removePhysicalTable.json" idKeys="id"
		showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="caption"  caption="i18n.PhysicalTable.caption"/>
			<youi:fieldText property="reportCode"  caption="i18n.PhysicalTable.reportCode"/>
			<youi:fieldText property="createTime"  caption="i18n.PhysicalTable.createTime"/>
			<youi:fieldText property="physicalTableNum"  caption="i18n.PhysicalTable.physicalTableNum"/>
			<youi:fieldText property="updateTime"  caption="i18n.PhysicalTable.updateTime"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" property="caption"  caption="i18n.PhysicalTable.caption"/>

		<youi:gridCol width="15%" property="reportCode"  caption="i18n.PhysicalTable.reportCode"/>
		<youi:gridCol width="15%" property="createTime"  caption="i18n.PhysicalTable.createTime"/>
		<youi:gridCol width="15%" property="physicalTableNum"  caption="i18n.PhysicalTable.physicalTableNum"/>
		<youi:gridCol width="15%" property="updateTime"  caption="i18n.PhysicalTable.updateTime"/>
		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改物理表弹出页 -->
	<youi:func name="grid_PhysicalTable_edit" params="dom,options,record">
		$elem('subpage_PhysicalTable_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增物理表弹出页 -->
	<youi:func name="grid_PhysicalTable_add" params="dom,options">
		$elem('subpage_PhysicalTable_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑物理表的subpage内容变化回调函数 -->
	<youi:func name="subpage_PhysicalTable_edit_change" params="record">
		$elem('grid_PhysicalTable',pageId).grid('refresh');
	</youi:func>
	<!-- 新增物理表的subpage内容变化回调函数 -->
	<youi:func name="subpage_PhysicalTable_add_change" params="record">
		$elem('grid_PhysicalTable',pageId).grid('refresh');
	</youi:func>
	
</youi:page>