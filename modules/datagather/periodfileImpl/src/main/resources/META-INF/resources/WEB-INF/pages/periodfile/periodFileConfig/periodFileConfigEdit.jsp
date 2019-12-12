<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="固定期文件报表配置编辑" autoLoadData="${param.periodFileId!=null}"
	dataSrc="/someServices/services/periodFileConfigManager/getPeriodFileConfig.json?periodFileId=${param.periodFileId}">
	<youi:form id="form_periodFileConfig" panel="false" submit="NOT" reset="NOT" action="/someServices/services/periodFileConfigManager/savePeriodFileConfig.json"
		idKeys="periodFileId">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="periodFileId"  caption="i18n.periodFileConfig.periodFileId"/>
			<youi:fieldText property="periodId"  caption="i18n.periodFileConfig.periodId"/>
			<youi:fieldText property="areaId"  caption="i18n.periodFileConfig.areaId"/>
			<youi:fieldText property="createTime"  caption="i18n.periodFileConfig.createTime"/>
			<youi:fieldText property="reportCode"  caption="i18n.periodFileConfig.reportCode"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_periodFileConfig',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_periodFileConfig_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存固定期文件报表配置成功.');
		$elem('form_periodFileConfig',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>