<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="服务参数管理">
	
	<youi:subpage height="150" src="/page/${_pagePath}/serverPropertyEdit.html?id={id}" subpageId="serverProperty_edit" caption="修改服务参数" type="dialog"/>
	<youi:subpage height="150" src="/page/${_pagePath}/serverPropertyEdit.html" subpageId="serverProperty_add" caption="添加服务参数" type="dialog"/>
	
	<youi:grid id="grid_serverProperty" src="/esb/web/serverPropertyManager/getPagerServerPropertys.json" 
		removeSrc="/esb/web/serverPropertyManager/removeServerProperty.json" idKeys="id"
		showNum="true" showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="propName"  caption="i18n.serverProperty.propName"/>
			<youi:fieldText property="propValue"  caption="i18n.serverProperty.propValue"/>
			<youi:fieldText property="profile"  caption="i18n.serverProperty.profile"/>
			<youi:fieldText property="serverId"  caption="i18n.serverProperty.serverId"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" property="propName"  caption="i18n.serverProperty.propName"/>
		<youi:gridCol width="15%" property="propValue"  caption="i18n.serverProperty.propValue"/>

		<youi:gridCol width="15%" property="profile"  caption="i18n.serverProperty.profile"/>
		<youi:gridCol width="15%" property="serverId"  caption="i18n.serverProperty.serverId"/>
		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
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