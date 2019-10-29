<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="用户数据权限组管理">
	
	<youi:subpage height="150" src="page/${_pagePath}/UserDataGroupEdit.html?id={id}" subpageId="UserDataGroup_edit" caption="修改用户数据权限组" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/UserDataGroupEdit.html" subpageId="UserDataGroup_add" caption="新增用户数据权限组" type="dialog"/>

    <%-- 工具栏 --%>
    <youi:toolbar refWidgetId="grid_UserDataGroup">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="save"/>
    </youi:toolbar>
    <%-- 列表数据 --%>
	<youi:grid id="grid_UserDataGroup" src="/someServices/services/UserDataGroupManager/getPagerUserDataGroups.json"
		removeSrc="/someServices/services/UserDataGroupManager/removeUserDataGroup.json" idKeys="id"
		showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="userId"  caption="i18n.UserDataGroup.userId"/>
			<youi:fieldText property="userType"  caption="i18n.UserDataGroup.userType"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" property="userId"  caption="i18n.UserDataGroup.userId"/>
		<youi:gridCol width="15%" property="userType"  caption="i18n.UserDataGroup.userType"/>

		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改用户数据权限组弹出页 -->
	<youi:func name="grid_UserDataGroup_edit" params="dom,options,record">
		$elem('subpage_UserDataGroup_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增用户数据权限组弹出页 -->
	<youi:func name="grid_UserDataGroup_add" params="dom,options">
		$elem('subpage_UserDataGroup_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑用户数据权限组的subpage内容变化回调函数 -->
	<youi:func name="subpage_UserDataGroup_edit_change" params="record">
		$elem('grid_UserDataGroup',pageId).grid('refresh');
	</youi:func>
	<!-- 新增用户数据权限组的subpage内容变化回调函数 -->
	<youi:func name="subpage_UserDataGroup_add_change" params="record">
		$elem('grid_UserDataGroup',pageId).grid('refresh');
	</youi:func>
	
</youi:page>