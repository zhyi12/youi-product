<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="思维导图编辑" autoLoadData="${param.dataDemandId!=null}"
	dataSrc="/someServices/services/DataDemandMindMapManager/getDataDemandMindMap.json?dataDemandId=${param.dataDemandId}">
	<youi:form id="form_DataDemandMindMap" panel="false" submit="NOT" reset="NOT" action="/someServices/services/DataDemandMindMapManager/saveDataDemandMindMap.json"
		idKeys="dataDemandId">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="dataDemandId"  caption="i18n.DataDemandMindMap.dataDemandId"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_DataDemandMindMap',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_DataDemandMindMap_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存思维导图成功.');
		$elem('form_DataDemandMindMap',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>