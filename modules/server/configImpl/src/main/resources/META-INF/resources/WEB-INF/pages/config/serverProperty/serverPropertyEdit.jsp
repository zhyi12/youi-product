<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="服务参数编辑" autoLoadData="${param.id!=null}"
	dataSrc="/esb/web/serverPropertyManager/getServerProperty.json?id=${param.id}">
	<youi:form id="form_serverProperty" panel="false" submit="NOT" reset="NOT" action="/esb/web/serverPropertyManager/saveServerProperty.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.serverProperty.id"/>
			<youi:fieldText property="propName"  caption="i18n.serverProperty.propName"/>
			<youi:fieldText property="propValue"  caption="i18n.serverProperty.propValue"/>
			<youi:fieldText property="profile"  caption="i18n.serverProperty.profile"/>
			<youi:fieldText property="serverId"  caption="i18n.serverProperty.serverId"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_serverProperty',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_serverProperty_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存服务参数成功.');
		$elem('form_serverProperty',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>