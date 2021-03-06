<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="用户数据权限组编辑" autoLoadData="${param.id!=null}"
	dataSrc="/someServices/services/UserDataGroupManager/getUserDataGroup.json?id=${param.id}">
	<youi:form id="form_UserDataGroup" panel="false" submit="NOT" reset="NOT" action="/someServices/services/UserDataGroupManager/saveUserDataGroup.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.UserDataGroup.id"/>
			<youi:fieldText property="userId"  caption="i18n.UserDataGroup.userId"/>
			<youi:fieldText property="userType"  caption="i18n.UserDataGroup.userType"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_UserDataGroup',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_UserDataGroup_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存用户数据权限组成功.');
		$elem('form_UserDataGroup',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>