<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="角色编辑" autoLoadData="${param.roleId!=null}" dataSrc="/uaa/services/roleManager/getRole.json?roleId=${param.roleId}">
	<youi:form id="form_role" panel="false" submit="NOT" reset="NOT" action="/uaa/services/roleManager/saveRole.json"
		idKeys="roleId">
		<youi:fieldLayout columns="1">
			<youi:fieldText property="roleId" caption="角色名"/>
			<youi:fieldText property="roleCaption" caption="角色描述"/>
			<youi:fieldText property="roleType" caption="角色类型"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_role',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_role_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存角色成功.');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>