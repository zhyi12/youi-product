<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="机构数据编辑" autoLoadData="${param.id!=null}"
	dataSrc="/someServices/services/AgencyDataManager/getAgencyData.json?id=${param.id}">
	<youi:form id="form_AgencyData" panel="false" submit="NOT" reset="NOT" action="/someServices/services/AgencyDataManager/saveAgencyData.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.AgencyData.id"/>
			<youi:fieldText property="dataIds"  caption="i18n.AgencyData.dataIds"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_AgencyData',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_AgencyData_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存机构数据成功.');
		$elem('form_AgencyData',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>