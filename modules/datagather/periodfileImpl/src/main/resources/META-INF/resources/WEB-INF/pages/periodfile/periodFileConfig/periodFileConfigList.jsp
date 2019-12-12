<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="固定期文件报表配置管理">
	
	<youi:subpage height="150" src="page/${_pagePath}/periodFileConfigEdit.html?periodFileId={periodFileId}" subpageId="periodFileConfig_edit" caption="修改固定期文件报表配置" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/periodFileConfigEdit.html" subpageId="periodFileConfig_add" caption="新增固定期文件报表配置" type="dialog"/>

    <%-- 工具栏 --%>
    <youi:toolbar refWidgetId="grid_periodFileConfig">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="save"/>
    </youi:toolbar>
    <%-- 列表数据 --%>
	<youi:grid id="grid_periodFileConfig" src="/someServices/services/periodFileConfigManager/getPagerPeriodFileConfigs.json"
		removeSrc="/someServices/services/periodFileConfigManager/removePeriodFileConfig.json" idKeys="periodFileId"
		showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="periodId"  caption="i18n.periodFileConfig.periodId"/>
			<youi:fieldText property="areaId"  caption="i18n.periodFileConfig.areaId"/>
			<youi:fieldText property="createTime"  caption="i18n.periodFileConfig.createTime"/>
			<youi:fieldText property="reportCode"  caption="i18n.periodFileConfig.reportCode"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" property="periodId"  caption="i18n.periodFileConfig.periodId"/>
		<youi:gridCol width="15%" property="areaId"  caption="i18n.periodFileConfig.areaId"/>
		<youi:gridCol width="15%" property="createTime"  caption="i18n.periodFileConfig.createTime"/>

		<youi:gridCol width="15%" property="reportCode"  caption="i18n.periodFileConfig.reportCode"/>
		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改固定期文件报表配置弹出页 -->
	<youi:func name="grid_periodFileConfig_edit" params="dom,options,record">
		$elem('subpage_periodFileConfig_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增固定期文件报表配置弹出页 -->
	<youi:func name="grid_periodFileConfig_add" params="dom,options">
		$elem('subpage_periodFileConfig_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑固定期文件报表配置的subpage内容变化回调函数 -->
	<youi:func name="subpage_periodFileConfig_edit_change" params="record">
		$elem('grid_periodFileConfig',pageId).grid('refresh');
	</youi:func>
	<!-- 新增固定期文件报表配置的subpage内容变化回调函数 -->
	<youi:func name="subpage_periodFileConfig_add_change" params="record">
		$elem('grid_periodFileConfig',pageId).grid('refresh');
	</youi:func>
	
</youi:page>