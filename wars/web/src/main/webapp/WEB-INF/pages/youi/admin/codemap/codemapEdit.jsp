<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="代码集编辑" autoLoadData="${param.codemapId!=null}" dataSrc="/baseServices/services/codemapManager/getCodemap.json?codemapId=${param.codemapId}">
	<youi:form id="form_codemap" panel="false" submit="NOT" reset="NOT" action="/baseServices/services/codemapManager/saveCodemap.json"
		idKeys="codemapId">
		<youi:fieldLayout columns="1" labelWidths="100">
			<youi:fieldHidden property="codemapId" caption="代码集ID"/>
			<youi:fieldText property="code" caption="代码集编码"/>
			<youi:fieldText property="caption" caption="代码集描述"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_codemap',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_codemap_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存代码集成功.');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>