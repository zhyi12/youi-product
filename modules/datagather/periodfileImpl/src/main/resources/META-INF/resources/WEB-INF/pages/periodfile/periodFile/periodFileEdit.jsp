<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="固定期文件编辑" autoLoadData="${param.id!=null}"
	dataSrc="/someServices/services/periodFileManager/getPeriodFile.json?id=${param.id}">
	<youi:form id="form_periodFile" panel="false" submit="NOT" reset="NOT" action="/someServices/services/periodFileManager/savePeriodFile.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.periodFile.id"/>
			<youi:fieldText property="createTime"  caption="i18n.periodFile.createTime"/>
			<youi:fieldText property="startTime"  caption="i18n.periodFile.startTime"/>
			<youi:fieldText property="periodId"  caption="i18n.periodFile.periodId"/>
			<youi:fieldText property="filePath"  caption="i18n.periodFile.filePath"/>
			<youi:fieldText property="surveyTaskId"  caption="i18n.periodFile.surveyTaskId"/>
			<youi:fieldText property="uploadTime"  caption="i18n.periodFile.uploadTime"/>
			<youi:fieldText property="agencyId"  caption="i18n.periodFile.agencyId"/>
			<youi:fieldText property="reportCode"  caption="i18n.periodFile.reportCode"/>
			<youi:fieldText property="memo"  caption="i18n.periodFile.memo"/>
			<youi:fieldText property="areaId"  caption="i18n.periodFile.areaId"/>
			<youi:fieldText property="endTime"  caption="i18n.periodFile.endTime"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_periodFile',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_periodFile_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存固定期文件成功.');
		$elem('form_periodFile',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>