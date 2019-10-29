<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据需求编辑" autoLoadData="${param.id!=null}"
	dataSrc="/someServices/services/DataDemandManager/getDataDemand.json?id=${param.id}">
	<youi:form id="form_DataDemand" panel="false" submit="NOT" reset="NOT" action="/someServices/services/DataDemandManager/saveDataDemand.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.DataDemand.id"/>
			<youi:fieldText property="demandCaption"  caption="i18n.DataDemand.demandCaption"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_DataDemand',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_DataDemand_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存数据需求成功.');
		$elem('form_DataDemand',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>