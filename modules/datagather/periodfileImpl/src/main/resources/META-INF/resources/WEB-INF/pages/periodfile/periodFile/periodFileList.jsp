<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="固定期文件管理">
	
	<youi:subpage height="150" src="page/${_pagePath}/periodFileEdit.html?id={id}" subpageId="periodFile_edit" caption="修改固定期文件" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/periodFileEdit.html" subpageId="periodFile_add" caption="新增固定期文件" type="dialog"/>

    <%-- 工具栏 --%>
    <youi:toolbar refWidgetId="grid_periodFile">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="save"/>
    </youi:toolbar>
    <%-- 列表数据 --%>
	<youi:grid id="grid_periodFile" src="/someServices/services/periodFileManager/getPagerPeriodFiles.json"
		removeSrc="/someServices/services/periodFileManager/removePeriodFile.json" idKeys="id"
		showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="createTime"  caption="i18n.periodFile.createTime"/>
			<youi:fieldText property="startTime"  caption="i18n.periodFile.startTime"/>
			<youi:fieldText property="periodId"  caption="i18n.periodFile.periodId"/>
			<youi:fieldText property="filePath"  caption="i18n.periodFile.filePath"/>
			<youi:fieldText property="surveyTaskId"  caption="i18n.periodFile.surveyTaskId"/>
			<youi:fieldText property="uploadTime"  caption="i18n.periodFile.uploadTime"/>
			<youi:fieldText property="agencyId"  caption="i18n.periodFile.agencyId"/>
			<youi:fieldText property="reportCode"  caption="i18n.periodFile.reportCode"/>
			<youi:fieldText property="memo"  caption="i18n.periodFile.memo"/>
			<youi:fieldText property="areaId"  caption="i18n.periodFile.areaId"/>
			<youi:fieldText property="endTime"  caption="i18n.periodFile.endTime"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" property="createTime"  caption="i18n.periodFile.createTime"/>
		<youi:gridCol width="15%" property="startTime"  caption="i18n.periodFile.startTime"/>
		<youi:gridCol width="15%" property="periodId"  caption="i18n.periodFile.periodId"/>
		<youi:gridCol width="15%" property="filePath"  caption="i18n.periodFile.filePath"/>

		<youi:gridCol width="15%" property="surveyTaskId"  caption="i18n.periodFile.surveyTaskId"/>
		<youi:gridCol width="15%" property="uploadTime"  caption="i18n.periodFile.uploadTime"/>
		<youi:gridCol width="15%" property="agencyId"  caption="i18n.periodFile.agencyId"/>
		<youi:gridCol width="15%" property="reportCode"  caption="i18n.periodFile.reportCode"/>
		<youi:gridCol width="15%" property="memo"  caption="i18n.periodFile.memo"/>
		<youi:gridCol width="15%" property="areaId"  caption="i18n.periodFile.areaId"/>
		<youi:gridCol width="15%" property="endTime"  caption="i18n.periodFile.endTime"/>
		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改固定期文件弹出页 -->
	<youi:func name="grid_periodFile_edit" params="dom,options,record">
		$elem('subpage_periodFile_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增固定期文件弹出页 -->
	<youi:func name="grid_periodFile_add" params="dom,options">
		$elem('subpage_periodFile_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑固定期文件的subpage内容变化回调函数 -->
	<youi:func name="subpage_periodFile_edit_change" params="record">
		$elem('grid_periodFile',pageId).grid('refresh');
	</youi:func>
	<!-- 新增固定期文件的subpage内容变化回调函数 -->
	<youi:func name="subpage_periodFile_add_change" params="record">
		$elem('grid_periodFile',pageId).grid('refresh');
	</youi:func>
	
</youi:page>