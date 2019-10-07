<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="角色授权" dataSrc="/uaa/services/roleManager/getRoleMenus.json?roleId=${param.roleId}">
	<youi:toolbar refWidgetId="form_role_auth">
		<youi:toolbarItem name="goback" caption="返回" tooltips="" icon="reply"/>
		<youi:toolbarItem name="submit" caption="提交" tooltips="" icon="submit"/>
		<youi:toolbarItem name="formReset" caption="重置" tooltips="" icon="reset"/>
	</youi:toolbar>

	<youi:form id="form_role_auth" panel="false" idKeys="roleId"  submit="NOT" reset="NOT"
		confirmMessage="确认授权？" styleClass="border-show"
		action="/uaa/services/roleManager/saveRoleMenus.json">
		<youi:fieldLayout styleClass="col-sm-12">
			<youi:fieldHidden property="roleType"/>
			<youi:fieldLabel property="roleId"  caption="角色名"/>
			<youi:fieldLabel property="roleCaption"  caption="角色描述"/>
		</youi:fieldLayout>
		
		<div style="padding:5px 5px 5px 20px;border-top:1px solid #ddd;" class="col-sm-12">
			<youi:fieldLayout styleClass="hideLabel" labelWidths="1" columns="1">
				<youi:fieldTree styleClass="overflow" property="menuId" popup="false" height="390" valueMode="array"
					code="menuId" caption="" rootText="系统菜单"
					src="/page/getMenuTree.json"
					check="true" simple="false"/>
			</youi:fieldLayout>
		</div>

	</youi:form>
	
	<youi:func name="init" params="results">
		if(results&&results.records){
			$elem('form_role_auth',pageId).form('fillRecord',{menuId:results.records});
		}
	</youi:func>
</youi:page>