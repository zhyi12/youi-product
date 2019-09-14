<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据查询编辑" autoLoadData="${param.id!=null}"
	dataSrc="/esb/web/dataQueryManager/getDataQuery.json?id=${param.id}">
	<youi:form id="form_dataQuery" panel="false" submit="NOT" reset="NOT" action="/esb/web/dataQueryManager/saveDataQuery.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.dataQuery.id"/>
			<youi:fieldText property="name"  caption="i18n.dataQuery.name"/>
			<youi:fieldText property="caption"  caption="i18n.dataQuery.caption"/>
			<youi:fieldText property="sqlExpression"  caption="i18n.dataQuery.sqlExpression"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_dataQuery',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_dataQuery_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存数据查询成功.');
		$elem('form_dataQuery',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>