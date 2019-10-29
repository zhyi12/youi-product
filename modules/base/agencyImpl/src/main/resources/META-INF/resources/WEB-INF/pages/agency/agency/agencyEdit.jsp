<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="机构编辑" autoLoadData="${param.id!=null}"
	dataSrc="/esb/web/agencyManager/getAgency.json?id=${param.id}">
	<youi:form id="form_agency" panel="false" submit="NOT" reset="NOT" action="/esb/web/agencyManager/saveAgency.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.agency.id"/>
			<youi:fieldText property="caption"  caption="i18n.agency.caption"/>
			<youi:fieldText property="code"  caption="i18n.agency.code"/>
			<youi:fieldText property="areaId"  caption="i18n.agency.areaId"/>
			<youi:fieldText property="parentId"  caption="i18n.agency.parentId"/>
			<youi:fieldText property="num"  caption="i18n.agency.num"/>
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