<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据需求域编辑" autoLoadData="${param.id!=null}"
	dataSrc="/someServices/services/DataDemandRealmManager/getDataDemandRealm.json?id=${param.id}">
	<youi:form id="form_DataDemandRealm" panel="false" submit="NOT" reset="NOT" action="/someServices/services/DataDemandRealmManager/saveDataDemandRealm.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.DataDemandRealm.id"/>
			<youi:fieldText property="parentId"  caption="i18n.DataDemandRealm.parentId"/>
			<youi:fieldText property="realmCaption"  caption="i18n.DataDemandRealm.realmCaption"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_DataDemandRealm',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_DataDemandRealm_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存数据需求域成功.');
		$elem('form_DataDemandRealm',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>