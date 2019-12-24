<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="时间属性编辑" autoLoadData="${param.id!=null}"
	dataSrc="/metadataServices/services/metaAttrManager/getMetaAttr.json?id=${param.id}">
	<youi:form id="form_metaAttr" panel="false" submit="NOT" reset="NOT" action="/metadataServices/services/metaAttrManager/saveMetaAttr.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldText property="id"  caption="属性编码"/>
			<youi:fieldText property="text"  caption="属性名称"/>
			<youi:fieldText property="expression"  caption="表达式"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_metaAttr',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_metaAttr_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存时间属性成功.');
		$elem('form_metaAttr',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>