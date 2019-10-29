<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="软件项目管理">
	
	<youi:subpage height="150" src="page/${_pagePath}/SoftProjectEdit.html?id={id}" subpageId="SoftProject_edit" caption="修改软件项目" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/SoftProjectEdit.html" subpageId="SoftProject_add" caption="新增软件项目" type="dialog"/>

    <%-- 工具栏 --%>
    <youi:toolbar refWidgetId="grid_SoftProject">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="save"/>
    </youi:toolbar>
    <%-- 列表数据 --%>
	<youi:grid id="grid_SoftProject" src="/someServices/services/SoftProjectManager/getPagerSoftProjects.json"
		removeSrc="/someServices/services/SoftProjectManager/removeSoftProject.json" idKeys="id"
		showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="enableDate"  caption="i18n.SoftProject.enableDate"/>
			<youi:fieldText property="projectCaption"  caption="i18n.SoftProject.projectCaption"/>
			<youi:fieldText property="projectCode"  caption="i18n.SoftProject.projectCode"/>
			<youi:fieldText property="agencyId"  caption="i18n.SoftProject.agencyId"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" property="enableDate"  caption="i18n.SoftProject.enableDate"/>
		<youi:gridCol width="15%" property="projectCaption"  caption="i18n.SoftProject.projectCaption"/>
		<youi:gridCol width="15%" property="projectCode"  caption="i18n.SoftProject.projectCode"/>

		<youi:gridCol width="15%" property="agencyId"  caption="i18n.SoftProject.agencyId"/>
		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改软件项目弹出页 -->
	<youi:func name="grid_SoftProject_edit" params="dom,options,record">
		$elem('subpage_SoftProject_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增软件项目弹出页 -->
	<youi:func name="grid_SoftProject_add" params="dom,options">
		$elem('subpage_SoftProject_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑软件项目的subpage内容变化回调函数 -->
	<youi:func name="subpage_SoftProject_edit_change" params="record">
		$elem('grid_SoftProject',pageId).grid('refresh');
	</youi:func>
	<!-- 新增软件项目的subpage内容变化回调函数 -->
	<youi:func name="subpage_SoftProject_add_change" params="record">
		$elem('grid_SoftProject',pageId).grid('refresh');
	</youi:func>
	
</youi:page>