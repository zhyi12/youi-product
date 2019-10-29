<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="开发人员编辑" autoLoadData="${param.id!=null}"
	dataSrc="/esb/web/spUserManager/getSpUser.json?id=${param.id}">
	<youi:form id="form_spUser" panel="false" submit="NOT" reset="NOT" action="/esb/web/spUserManager/saveSpUser.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.spUser.id"/>
			<youi:fieldText property="agencyId"  caption="i18n.spUser.agencyId"/>
			<youi:fieldText property="userCaption"  caption="i18n.spUser.userCaption"/>
			<youi:fieldText property="username"  caption="i18n.spUser.username"/>
			<youi:fieldText property="password"  caption="i18n.spUser.password"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_spUser',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_spUser_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存开发人员成功.');
		$elem('form_spUser',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>