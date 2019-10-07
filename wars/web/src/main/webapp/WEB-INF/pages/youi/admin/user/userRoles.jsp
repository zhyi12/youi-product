<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="绑定角色">

	<youi:form id="form_userRoles" reset="NOT" submit="NOT" panel="false"
			   action="/uaa/services/userConfigManager/saveUserRoles.json">
		<youi:fieldLayout columns="1">
			<youi:fieldLabel property="username" caption="用户名" defaultValue="${param.username}"/>
			<youi:fieldTree styleClass="overflow" property="roleId" popup="false"
							code="id" show="text" caption="用户角色"
							src="/uaa/services/userConfigManager/getUserRolesTree.json?username=${param.username}"
							check="true" simple="false"/>
		</youi:fieldLayout>
	</youi:form>

	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>

	<youi:func name="form_userRoles_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存用户角色成功.');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>