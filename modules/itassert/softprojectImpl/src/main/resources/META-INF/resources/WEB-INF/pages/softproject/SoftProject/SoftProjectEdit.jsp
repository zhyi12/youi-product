<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="软件项目编辑" autoLoadData="${param.id!=null}"
	dataSrc="/someServices/services/SoftProjectManager/getSoftProject.json?id=${param.id}">
	<youi:form id="form_SoftProject" panel="false" submit="NOT" reset="NOT" action="/someServices/services/SoftProjectManager/saveSoftProject.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.SoftProject.id"/>
			<youi:fieldText property="enableDate"  caption="i18n.SoftProject.enableDate"/>
			<youi:fieldText property="projectCaption"  caption="i18n.SoftProject.projectCaption"/>
			<youi:fieldText property="projectCode"  caption="i18n.SoftProject.projectCode"/>
			<youi:fieldText property="agencyId"  caption="i18n.SoftProject.agencyId"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_SoftProject',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_SoftProject_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存软件项目成功.');
		$elem('form_SoftProject',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>