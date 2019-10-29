<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="用户数据编辑" autoLoadData="${param.id!=null}"
	dataSrc="/someServices/services/UserDataManager/getUserData.json?id=${param.id}">
	<youi:form id="form_UserData" panel="false" submit="NOT" reset="NOT" action="/someServices/services/UserDataManager/saveUserData.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.UserData.id"/>
			<youi:fieldText property="dataIds"  caption="i18n.UserData.dataIds"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_UserData',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_UserData_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存用户数据成功.');
		$elem('form_UserData',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>