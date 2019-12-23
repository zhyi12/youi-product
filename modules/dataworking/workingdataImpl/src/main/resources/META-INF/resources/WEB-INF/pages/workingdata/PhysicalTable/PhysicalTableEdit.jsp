<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="物理表编辑" autoLoadData="${param.id!=null}"
	dataSrc="/someServices/services/PhysicalTableManager/getPhysicalTable.json?id=${param.id}">
	<youi:form id="form_PhysicalTable" panel="false" submit="NOT" reset="NOT" action="/someServices/services/PhysicalTableManager/savePhysicalTable.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.PhysicalTable.id"/>
			<youi:fieldText property="caption"  caption="i18n.PhysicalTable.caption"/>
			<youi:fieldText property="reportCode"  caption="i18n.PhysicalTable.reportCode"/>
			<youi:fieldText property="createTime"  caption="i18n.PhysicalTable.createTime"/>
			<youi:fieldText property="physicalTableNum"  caption="i18n.PhysicalTable.physicalTableNum"/>
			<youi:fieldText property="updateTime"  caption="i18n.PhysicalTable.updateTime"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_PhysicalTable',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_PhysicalTable_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存物理表成功.');
		$elem('form_PhysicalTable',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>