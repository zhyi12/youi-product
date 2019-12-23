<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="交叉表数据管理">
	
	<youi:subpage height="150" src="page/${_pagePath}/workingCrossDataEdit.html?id={id}" subpageId="workingCrossData_edit" caption="修改交叉表数据" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/workingCrossDataEdit.html" subpageId="workingCrossData_add" caption="新增交叉表数据" type="dialog"/>

    <%-- 工具栏 --%>
    <youi:toolbar refWidgetId="grid_workingCrossData">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="save"/>
    </youi:toolbar>
    <%-- 列表数据 --%>
	<youi:grid id="grid_workingCrossData" src="/someServices/services/workingCrossDataManager/getPagerWorkingCrossDatas.json"
		removeSrc="/someServices/services/workingCrossDataManager/removeWorkingCrossData.json" idKeys="id"
		showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="createTime"  caption="i18n.workingCrossData.createTime"/>
			<youi:fieldText property="areaId"  caption="i18n.workingCrossData.areaId"/>
			<youi:fieldText property="updateTime"  caption="i18n.workingCrossData.updateTime"/>
			<youi:fieldText property="periodId"  caption="i18n.workingCrossData.periodId"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" property="createTime"  caption="i18n.workingCrossData.createTime"/>

		<youi:gridCol width="15%" property="areaId"  caption="i18n.workingCrossData.areaId"/>
		<youi:gridCol width="15%" property="updateTime"  caption="i18n.workingCrossData.updateTime"/>
		<youi:gridCol width="15%" property="periodId"  caption="i18n.workingCrossData.periodId"/>
		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改交叉表数据弹出页 -->
	<youi:func name="grid_workingCrossData_edit" params="dom,options,record">
		$elem('subpage_workingCrossData_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增交叉表数据弹出页 -->
	<youi:func name="grid_workingCrossData_add" params="dom,options">
		$elem('subpage_workingCrossData_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑交叉表数据的subpage内容变化回调函数 -->
	<youi:func name="subpage_workingCrossData_edit_change" params="record">
		$elem('grid_workingCrossData',pageId).grid('refresh');
	</youi:func>
	<!-- 新增交叉表数据的subpage内容变化回调函数 -->
	<youi:func name="subpage_workingCrossData_add_change" params="record">
		$elem('grid_workingCrossData',pageId).grid('refresh');
	</youi:func>
	
</youi:page>