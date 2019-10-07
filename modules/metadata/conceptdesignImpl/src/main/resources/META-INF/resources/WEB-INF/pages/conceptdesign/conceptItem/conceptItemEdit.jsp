<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="模型项编辑" autoLoadData="${param.id!=null}"
	dataSrc="/esb/web/conceptItemManager/getConceptItem.json?id=${param.id}">
	<youi:form id="form_conceptItem" panel="false" submit="NOT" reset="NOT" action="/esb/web/conceptItemManager/saveConceptItem.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.conceptItem.id"/>
			<youi:fieldText property="name"  caption="i18n.conceptItem.name"/>
			<youi:fieldText property="group"  caption="i18n.conceptItem.group"/>
			<youi:fieldText property="caption"  caption="i18n.conceptItem.caption"/>
			<youi:fieldText property="parentId"  caption="i18n.conceptItem.parentId"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_conceptItem',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_conceptItem_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存模型项成功.');
		$elem('form_conceptItem',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>