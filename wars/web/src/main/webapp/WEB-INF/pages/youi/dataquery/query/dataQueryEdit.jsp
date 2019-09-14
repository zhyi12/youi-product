<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据查询" autoLoadData="${param.id!=null}"
	dataSrc="/metadataServices/services/dataQueryManager/getDataQuery.json?id=${param.id}">
	<youi:form id="form_dataQuery" panel="false" submit="NOT" reset="NOT" action="/metadataServices/services/dataQueryManager/saveDataQuery.json"
		idKeys="id" styleClass="no-padding">
		<youi:fieldLayout columns="2" labelWidths="100,100">
			<youi:fieldHidden property="id"  caption="i18n.dataQuery.id"/>
			<youi:fieldText notNull="true" property="name"  caption="查询名称"/>
			<youi:fieldText notNull="true" property="caption"  caption="中文描述"/>
			<youi:fieldText property="sqlExpression" column="2" caption="sql"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>

	<%-- 页面初始化 --%>
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_dataQuery',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	<%--  --%>
	<youi:func name="form_dataQuery_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存数据查询成功.');
		$elem('form_dataQuery',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>

</youi:page>