<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="概念模型图编辑" autoLoadData="${param.id!=null}"
	dataSrc="/esb/web/conceptDiagramManager/getConceptDiagram.json?id=${param.id}">
	<youi:form id="form_conceptDiagram" panel="false" submit="NOT" reset="NOT" action="/esb/web/conceptDiagramManager/saveConceptDiagram.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.conceptDiagram.id"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_conceptDiagram',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_conceptDiagram_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存概念模型图成功.');
		$elem('form_conceptDiagram',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>