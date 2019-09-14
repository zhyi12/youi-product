<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据列编辑" autoLoadData="${param.id!=null}"
	dataSrc="/esb/web/dataTableColumnManager/getDataTableColumn.json?id=${param.id}">
	<youi:form id="form_dataTableColumn" panel="false" submit="NOT" reset="NOT" action="/esb/web/dataTableColumnManager/saveDataTableColumn.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.dataTableColumn.id"/>
			<youi:fieldText property="dataResourceId"  caption="i18n.dataTableColumn.dataResourceId"/>
			<youi:fieldText property="columnName"  caption="i18n.dataTableColumn.columnName"/>
			<youi:fieldText property="tableName"  caption="i18n.dataTableColumn.tableName"/>
			<youi:fieldText property="dataItemId"  caption="i18n.dataTableColumn.dataItemId"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_dataTableColumn',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_dataTableColumn_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存数据列成功.');
		$elem('form_dataTableColumn',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>