<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据表编辑" autoLoadData="${param.id!=null}"
	dataSrc="/metadataServices/services/dataTableManager/getDataTable.json?id=${param.id}">
	<youi:form id="form_dataTable" panel="false" submit="NOT" reset="NOT" action="/metadataServices/services/dataTableManager/saveDataTable.json"
		idKeys="id">
		<youi:fieldLayout labelWidths="100,100">
			<youi:fieldHidden property="id"  caption="i18n.dataTable.id"/>

			<youi:fieldText notNull="true" property="catalog"  caption="catalog"/>
			<youi:fieldText notNull="true" property="schema"  caption="schema"/>
			<youi:fieldText notNull="true" property="tableName"  caption="表名"/>
			<youi:fieldText notNull="true" property="entityCode"  caption="实体编号"/>
			<youi:fieldText notNull="true" column="2" property="tableCaption"  caption="中文表名"/>
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