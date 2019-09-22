<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="代码项编辑" autoLoadData="${param.codeitemId!=null}" dataSrc="/baseServices/services/codeitemManager/getCodeitem.json?codeitemId=${param.codeitemId}">
	<youi:form id="form_codeitem" panel="false" submit="NOT" reset="NOT" action="/baseServices/services/codeitemManager/saveCodeitem.json"
		idKeys="itemId">
		<youi:fieldLayout columns="1" labelWidths="100">
			<youi:fieldHidden property="codemapId" defaultValue="${param.codemapId}" caption="代码集ID"/>
			<youi:fieldHidden property="codeitemId" caption="代码项ID"/>
			<youi:fieldText property="itemValue" caption="代码项编码"/>
			<youi:fieldText property="itemCaption" caption="代码项描述"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_codeitem',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_codeitem_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存代码项成功.');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>