<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="工作数据管理">
	
	<youi:subpage height="150" src="page/${_pagePath}/workingDataEdit.html?id={id}" subpageId="workingData_edit" caption="修改工作数据" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/workingDataEdit.html" subpageId="workingData_add" caption="新增工作数据" type="dialog"/>

    <%-- 工具栏 --%>
    <youi:toolbar refWidgetId="grid_workingData">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="save"/>
    </youi:toolbar>
    <%-- 列表数据 --%>
	<youi:grid id="grid_workingData" src="/someServices/services/workingDataManager/getPagerWorkingDatas.json"
		removeSrc="/someServices/services/workingDataManager/removeWorkingData.json" idKeys="id"
		showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="periodId"  caption="i18n.workingData.periodId"/>
			<youi:fieldText property="createTime"  caption="i18n.workingData.createTime"/>
			<youi:fieldText property="updateTime"  caption="i18n.workingData.updateTime"/>
			<youi:fieldText property="respondentId"  caption="i18n.workingData.respondentId"/>
			<youi:fieldText property="areaId"  caption="i18n.workingData.areaId"/>
		</youi:fieldLayout>


		<youi:gridCol width="15%" property="periodId"  caption="i18n.workingData.periodId"/>
		<youi:gridCol width="15%" property="createTime"  caption="i18n.workingData.createTime"/>
		<youi:gridCol width="15%" property="updateTime"  caption="i18n.workingData.updateTime"/>
		<youi:gridCol width="15%" property="respondentId"  caption="i18n.workingData.respondentId"/>
		<youi:gridCol width="15%" property="areaId"  caption="i18n.workingData.areaId"/>
		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改工作数据弹出页 -->
	<youi:func name="grid_workingData_edit" params="dom,options,record">
		$elem('subpage_workingData_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增工作数据弹出页 -->
	<youi:func name="grid_workingData_add" params="dom,options">
		$elem('subpage_workingData_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑工作数据的subpage内容变化回调函数 -->
	<youi:func name="subpage_workingData_edit_change" params="record">
		$elem('grid_workingData',pageId).grid('refresh');
	</youi:func>
	<!-- 新增工作数据的subpage内容变化回调函数 -->
	<youi:func name="subpage_workingData_add_change" params="record">
		$elem('grid_workingData',pageId).grid('refresh');
	</youi:func>
	
</youi:page>