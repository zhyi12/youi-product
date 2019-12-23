<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="工作数据编辑" autoLoadData="${param.id!=null}"
	dataSrc="/someServices/services/workingDataManager/getWorkingData.json?id=${param.id}">
	<youi:form id="form_workingData" panel="false" submit="NOT" reset="NOT" action="/someServices/services/workingDataManager/saveWorkingData.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.workingData.id"/>
			<youi:fieldText property="periodId"  caption="i18n.workingData.periodId"/>
			<youi:fieldText property="createTime"  caption="i18n.workingData.createTime"/>
			<youi:fieldText property="updateTime"  caption="i18n.workingData.updateTime"/>
			<youi:fieldText property="respondentId"  caption="i18n.workingData.respondentId"/>
			<youi:fieldText property="areaId"  caption="i18n.workingData.areaId"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_workingData',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_workingData_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存工作数据成功.');
		$elem('form_workingData',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>