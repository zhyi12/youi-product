<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="机构编辑" autoLoadData="${param.id!=null}"
	dataSrc="/baseServices/services/agencyManager/getAgency.json?id=${param.id}">
	<youi:form id="form_agency" panel="false" submit="NOT" reset="NOT" action="/baseServices/services/agencyManager/saveAgency.json"
		idKeys="id">
		<youi:fieldLayout columns="1" labelWidths="100">
			<youi:fieldHidden property="id"  caption="i18n.agency.id"/>
			<youi:fieldText notNull="true" property="caption"  caption="机构名称"/>
			<youi:fieldText property="areaId"  caption="行政区划"/>
			<youi:fieldText property="parentId"  caption="父节点"/>
			<youi:fieldText property="num"  caption="序号"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_agency',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_agency_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存机构成功.');
		$elem('form_agency',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>