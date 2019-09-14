<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据列编辑" autoLoadData="${param.id!=null}"
	dataSrc="/metadataServices/services/dataTableColumnManager/getDataTableColumn.json?id=${param.id}">
	<youi:form id="form_dataTableColumn" panel="false" submit="NOT" reset="NOT" action="/metadataServices/services/dataTableColumnManager/saveDataTableColumn.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.dataTableColumn.id"/>
			<youi:fieldText property="dataResourceId"  caption="数据资源"/>
			<youi:fieldText property="tableName"  caption="表名"/>
			<youi:fieldText property="columnName"  caption="列名"/>
			<youi:fieldText property="columnCaption"  caption="中文名称"/>
			<youi:fieldText property="dataItemId"  caption="数据项"/>
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