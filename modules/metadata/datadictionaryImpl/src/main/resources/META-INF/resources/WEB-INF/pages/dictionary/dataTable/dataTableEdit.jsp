<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据表编辑" autoLoadData="${param.id!=null}"
	dataSrc="/esb/web/dataTableManager/getDataTable.json?id=${param.id}">
	<youi:form id="form_dataTable" panel="false" submit="NOT" reset="NOT" action="/esb/web/dataTableManager/saveDataTable.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.dataTable.id"/>
			<youi:fieldText property="tableName"  caption="i18n.dataTable.tableName"/>
			<youi:fieldText property="dataResourceId"  caption="i18n.dataTable.dataResourceId"/>
			<youi:fieldText property="tableCaption"  caption="i18n.dataTable.tableCaption"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_dataTable',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_dataTable_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存数据表成功.');
		$elem('form_dataTable',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>