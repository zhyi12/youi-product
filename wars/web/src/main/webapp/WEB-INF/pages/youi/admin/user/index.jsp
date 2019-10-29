<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="开发人员管理">
	
	<youi:subpage height="260" src="page/${_pagePath}/userEdit.html?userId={userId}" subpageId="user_edit" type="dialog" caption="修改用户"></youi:subpage>
	<youi:subpage height="260" src="page/${_pagePath}/userEdit.html" subpageId="user_add" caption="添加用户" type="dialog"></youi:subpage>
	 
	<youi:grid id="grid_user" src="/uaa/services/spUserManager/getPagerSpUsers.json"
		removeSrc="/uaa/services/spUserManager/removeSpUser.json" idKeys="userId" styleClass="grid-query-inline"
		showNum="true" showCheckbox="true">

		<youi:fieldLayout>
			<youi:fieldText property="username" caption="登录名"/>
			<youi:fieldText property="userCaption" caption="用户姓名"/>
		</youi:fieldLayout>
		
		<youi:gridCol width="30%" caption="登录名" property="username"/>
		<youi:gridCol width="50%" caption="用户姓名" property="userCaption"/>
		
		<youi:gridCol width="20%" type="button" property="button" caption="操作" align="center">
			<youi:button name="edit" caption="角色授权"></youi:button>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"></youi:button>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	<!-- Grid编辑按钮动作：打开修改用户弹出页 -->
	<youi:func name="grid_user_edit" params="dom,options,record">
		$elem('subpage_user_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增用户弹出页 -->
	<youi:func name="grid_user_add" params="dom,options">
		$elem('subpage_user_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑用户的subpage内容变化回调函数 -->
	<youi:func name="subpage_user_edit_change" params="record">
		$elem('grid_user',pageId).grid('refresh');
	</youi:func>
	<!-- 新增用户的subpage内容变化回调函数 -->
	<youi:func name="subpage_user_add_change" params="record">
		$elem('grid_user',pageId).grid('refresh');
	</youi:func>
	
</youi:page>