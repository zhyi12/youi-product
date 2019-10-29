<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="开发人员管理">
	
	<youi:subpage height="150" src="/page/${_pagePath}/spUserEdit.html?id={id}" subpageId="spUser_edit" caption="修改开发人员" type="dialog"/>
	<youi:subpage height="150" src="/page/${_pagePath}/spUserEdit.html" subpageId="spUser_add" caption="添加开发人员" type="dialog"/>
	
	<youi:grid id="grid_spUser" src="/esb/web/spUserManager/getPagerSpUsers.json" 
		removeSrc="/esb/web/spUserManager/removeSpUser.json" idKeys="id"
		showNum="true" showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="agencyId"  caption="i18n.spUser.agencyId"/>
			<youi:fieldText property="userCaption"  caption="i18n.spUser.userCaption"/>
			<youi:fieldText property="username"  caption="i18n.spUser.username"/>
			<youi:fieldText property="password"  caption="i18n.spUser.password"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" property="agencyId"  caption="i18n.spUser.agencyId"/>
		<youi:gridCol width="15%" property="userCaption"  caption="i18n.spUser.userCaption"/>
		<youi:gridCol width="15%" property="username"  caption="i18n.spUser.username"/>
		<youi:gridCol width="15%" property="password"  caption="i18n.spUser.password"/>

		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改开发人员弹出页 -->
	<youi:func name="grid_spUser_edit" params="dom,options,record">
		$elem('subpage_spUser_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增开发人员弹出页 -->
	<youi:func name="grid_spUser_add" params="dom,options">
		$elem('subpage_spUser_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑开发人员的subpage内容变化回调函数 -->
	<youi:func name="subpage_spUser_edit_change" params="record">
		$elem('grid_spUser',pageId).grid('refresh');
	</youi:func>
	<!-- 新增开发人员的subpage内容变化回调函数 -->
	<youi:func name="subpage_spUser_add_change" params="record">
		$elem('grid_spUser',pageId).grid('refresh');
	</youi:func>
	
</youi:page>