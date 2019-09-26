<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="${param.title}服务参数管理">
	
	<youi:subpage height="150" src="page/${_pagePath}/serverPropertyEdit.html?id={id}" subpageId="serverProperty_edit" caption="修改服务参数" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/serverPropertyEdit.html" subpageId="serverProperty_add" caption="添加服务参数" type="dialog"/>

	<youi:toolbar id="toolbar" refWidgetId="grid_serverProperty">
		<youi:toolbarItem name="refresh" caption="查询" icon="search" tooltips=""/>
		<youi:toolbarItem name="add" caption="新增" icon="plus" tooltips=""/>
		<youi:toolbarItem name="import" caption="导入" icon="" tooltips=""/>
	</youi:toolbar>
	<youi:grid id="grid_serverProperty" src="/configServices/services/serverPropertyManager/getPagerServerProperties.json"
		removeSrc="/configServices/services/serverPropertyManager/removeServerProperty.json" idKeys="id"
		showNum="true" showCheckbox="true" fixedFooter="true" reset="NOT" query="NOT">
		<youi:fieldLayout prefix="query">
			<youi:if test="${param.serverId==null}">
				<youi:fieldText property="serverId"  caption="serverId"/>
			</youi:if>
			<youi:if test="${param.serverId!=null}">
				<youi:fieldLabel property="serverId"  caption="serverId"  defaultValue="${param.serverId}"/>
			</youi:if>
			<youi:fieldText property="profile"  caption="profile"/>
			<youi:fieldText property="propName"  caption="参数名"/>
			<youi:fieldText property="propValue"  caption="参数值"/>
		</youi:fieldLayout>

		<youi:gridCol width="35%" property="propName"  caption="参数名"/>
		<youi:gridCol width="25%" property="propValue"  caption="参数值"/>

		<youi:gridCol width="15%" property="profile"  caption="profile"/>
		<youi:gridCol width="15%" property="serverId"  caption="serverId"/>
		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>

	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改服务参数弹出页 -->
	<youi:func name="grid_serverProperty_edit" params="dom,options,record">
		$elem('subpage_serverProperty_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增服务参数弹出页 -->
	<youi:func name="grid_serverProperty_add" params="dom,options">
		$elem('subpage_serverProperty_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑服务参数的subpage内容变化回调函数 -->
	<youi:func name="subpage_serverProperty_edit_change" params="record">
		$elem('grid_serverProperty',pageId).grid('refresh');
	</youi:func>
	<!-- 新增服务参数的subpage内容变化回调函数 -->
	<youi:func name="subpage_serverProperty_add_change" params="record">
		$elem('grid_serverProperty',pageId).grid('refresh');
	</youi:func>
	
</youi:page>