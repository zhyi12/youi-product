<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="角色管理">
	
	<youi:subpage height="150" src="page/${_pagePath}/roleEdit.html?roleId={roleId}" subpageId="role_edit" caption="修改角色" type="dialog"></youi:subpage>
	<youi:subpage height="150" src="page/${_pagePath}/roleEdit.html?_timestamp={timestamp}" subpageId="role_add" caption="添加角色" type="dialog"></youi:subpage>
	
	<youi:subpage src="page/${_pagePath}/roleAuth.html?roleId={roleId}" subpageId="role_auth" caption="角色授权"
		type="secondPage"></youi:subpage>
	
	<youi:grid id="grid_role" src="/uaa/services/roleManager/getPagerRoles.json"
		removeSrc="/uaa/services/roleManager/removeRole.json" idKeys="roleId" styleClass="grid-query-inline"
		showNum="true" showCheckbox="true">
		
		<youi:fieldLayout>
			<youi:fieldText property="roleId" operator="LIKE" caption="角色名"/>
			<youi:fieldText property="roleCaption" operator="LIKE" caption="角色描述"/>
		</youi:fieldLayout>

		<youi:gridCol orderBy="desc" width="30%" caption="角色名" property="roleId"/>
		<youi:gridCol orderBy="desc" width="45%" caption="角色描述" property="roleCaption"/>
		<youi:gridCol orderBy="desc" width="15%" caption="角色类型" property="roleType"/>

		<youi:gridCol align="center" width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" icon="rename" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="auth" icon="hand-right" caption="授权"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="delete-slender" caption="删除"/>
			
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	<!-- Grid编辑按钮动作：打开修改角色弹出页 -->
	<youi:func name="grid_role_edit" params="dom,options,record">
		$elem('subpage_role_edit',pageId).subpage('open',{},'['+record.roleCaption+']',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增角色弹出页 -->
	<youi:func name="grid_role_add" params="dom,options">
		$elem('subpage_role_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	
	<!-- 打开授权页面 -->
	<youi:func name="grid_role_auth" params="dom,options,record">
		$elem('subpage_role_auth',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	
	<!-- 编辑角色的subpage内容变化回调函数 -->
	<youi:func name="subpage_role_edit_change" params="record">
		$elem('grid_role',pageId).grid('refresh');
	</youi:func>
	<!-- 新增角色的subpage内容变化回调函数 -->
	<youi:func name="subpage_role_add_change" params="record">
		$elem('grid_role',pageId).grid('refresh');
	</youi:func>
	
</youi:page>