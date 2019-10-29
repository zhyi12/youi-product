<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="用户数据管理">
	
	<youi:subpage height="150" src="page/${_pagePath}/UserDataEdit.html?id={id}" subpageId="UserData_edit" caption="修改用户数据" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/UserDataEdit.html" subpageId="UserData_add" caption="新增用户数据" type="dialog"/>

    <%-- 工具栏 --%>
    <youi:toolbar refWidgetId="grid_UserData">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="save"/>
    </youi:toolbar>
    <%-- 列表数据 --%>
	<youi:grid id="grid_UserData" src="/someServices/services/UserDataManager/getPagerUserDatas.json"
		removeSrc="/someServices/services/UserDataManager/removeUserData.json" idKeys="id"
		showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="dataIds"  caption="i18n.UserData.dataIds"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" property="dataIds"  caption="i18n.UserData.dataIds"/>

		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改用户数据弹出页 -->
	<youi:func name="grid_UserData_edit" params="dom,options,record">
		$elem('subpage_UserData_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增用户数据弹出页 -->
	<youi:func name="grid_UserData_add" params="dom,options">
		$elem('subpage_UserData_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑用户数据的subpage内容变化回调函数 -->
	<youi:func name="subpage_UserData_edit_change" params="record">
		$elem('grid_UserData',pageId).grid('refresh');
	</youi:func>
	<!-- 新增用户数据的subpage内容变化回调函数 -->
	<youi:func name="subpage_UserData_add_change" params="record">
		$elem('grid_UserData',pageId).grid('refresh');
	</youi:func>
	
</youi:page>