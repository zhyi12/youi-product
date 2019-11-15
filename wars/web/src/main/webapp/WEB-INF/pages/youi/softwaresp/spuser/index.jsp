<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="开发人员">

	<youi:ajaxUrl name="resetPasswordUrl" src="/uaa/services/spUserManager/resetPassword.json"/>
	
	<youi:subpage height="220" src="page/${_pagePath}/userEdit.html?id={id}" subpageId="user_edit" type="dialog" caption="修改用户"></youi:subpage>
	<youi:subpage height="220" src="page/${_pagePath}/userEdit.html" subpageId="user_add" caption="添加用户" type="dialog"></youi:subpage>
	<%-- --%>
	<youi:toolbar refWidgetId="grid_user">
		<youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
		<youi:toolbarItem name="add" caption="新增" tooltips="" icon="save"/>
	</youi:toolbar>
	<youi:grid id="grid_user" src="/uaa/services/spUserManager/getPagerSpUsers.json"
		removeSrc="/uaa/services/spUserManager/removeSpUser.json" idKeys="id" styleClass="grid-query-inline"
		showNum="true" showCheckbox="true">

		<youi:fieldLayout>
			<youi:fieldText property="username" caption="登录名"/>
			<youi:fieldText property="userCaption" caption="姓名"/>
		</youi:fieldLayout>
		
		<youi:gridCol width="25%" caption="所属开发商" property="agencyId"/>
		<youi:gridCol width="20%" caption="登录名" property="username"/>
		<youi:gridCol width="20%" caption="姓名" property="userCaption"/>
		<youi:gridCol width="15%" caption="手机号" property="phone"/>
		<youi:gridCol width="10%" caption="初始密码" property="initPassword"/>

		<youi:gridCol width="10%" type="button" property="button" caption="操作" align="center">
			<youi:button name="edit" caption="编辑"></youi:button>
			<youi:button name="resetPassword" caption="重置密码"></youi:button>
			<youi:button name="removeRecord" icon="remove" caption="删除"></youi:button>
		</youi:gridCol>


		<div id="111"> </div>

	</youi:grid>
	<!-- Grid编辑按钮动作：打开修改用户弹出页 -->
	<youi:func name="grid_user_edit" params="dom,options,record">
		$elem('subpage_user_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增用户弹出页 -->
	<youi:func name="grid_user_add" params="dom,options">
		$elem('subpage_user_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>

	<youi:func name="grid_user_resetPassword" params="dom,options,record" urls="resetPasswordUrl">
		$.youi.messageUtils.confirm('确认重置'+record.userCaption+'的密码',function(){
			$.youi.ajaxUtils.ajax({
				url:funcUrls.resetPasswordUrl,
				data:{id:record.id},
				success:function(result){
					$.youi.messageUtils.showMessage('密码重置成功.');
				}
			});
		});

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