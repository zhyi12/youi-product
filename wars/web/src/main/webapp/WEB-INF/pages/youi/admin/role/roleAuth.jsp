<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="角色授权" dataSrc="/uaa/services/roleManager/getRoleMenus.json?roleId=${param.roleId}">
	<%--  	--%>
	<youi:toolbar refWidgetId="form_role_auth">
		<youi:toolbarItem name="submit" caption="保存" tooltips="" icon="save"/>
		<youi:toolbarItem name="formReset" caption="重置" tooltips="" icon="refresh"/>
	</youi:toolbar>
	<%-- 授权表单 --%>
	<youi:form id="form_role_auth" panel="false" idKeys="roleId"  submit="NOT" reset="NOT"
		confirmMessage="确认授权？" styleClass="border-show"
		action="/uaa/services/roleManager/saveRoleMenus.json">
		<youi:fieldLayout styleClass="col-sm-12 border-bottom">
			<youi:fieldHidden property="roleType"/>
			<youi:fieldLabel property="roleId"  caption="角色名"/>
			<youi:fieldLabel property="roleCaption"  caption="角色描述"/>
		</youi:fieldLayout>

		<youi:fieldLayout styleClass="hideLabel padding-top top-border" labelWidths="1" columns="1">
			<youi:fieldTree styleClass="overflow" property="menuId" popup="false" height="420" valueMode="array"
				code="menuId" caption="" rootText="系统菜单"
				src="/page/getMenuTree.json"
				check="true" simple="false"/>
		</youi:fieldLayout>
	</youi:form>
	<%-- 初始化 --%>
	<youi:func name="init" params="results">
		if(results&&results.records){
			$elem('form_role_auth',pageId).form('fillRecord',{menuId:results.records});
		}
		//调整高度
		var contentHeight = $('#P_'+pageId).height() - 160;
		$elem('field_menuId',pageId).height(contentHeight);
	</youi:func>

</youi:page>