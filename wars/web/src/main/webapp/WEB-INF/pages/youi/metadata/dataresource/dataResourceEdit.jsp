<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据资源编辑" autoLoadData="${param.id!=null}"
	dataSrc="/metadataServices/services/dataResourceManager/getDataResource.json?id=${param.id}">
	<youi:form id="form_dataResource" panel="false" submit="NOT" reset="NOT"
			   action="/metadataServices/services/dataResourceManager/saveDataResource.json"
		idKeys="id" >
		<youi:fieldLayout columns="1" labelWidths="100">
			<youi:fieldHidden property="id"  caption="i18n.dataResource.id"/>
			<youi:fieldText notNull="true" property="caption"  caption="资源名称"/>
			<youi:fieldText notNull="true" property="catalog" defaultValue="${param.catalog}" caption="catalog"/>
			<youi:fieldText notNull="true" property="schema" defaultValue="${param.schema}"  caption="schema"/>
			<youi:fieldText property="status"  caption="数据状态"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_dataResource',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_dataResource_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存数据资源成功.');
		$elem('form_dataResource',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>