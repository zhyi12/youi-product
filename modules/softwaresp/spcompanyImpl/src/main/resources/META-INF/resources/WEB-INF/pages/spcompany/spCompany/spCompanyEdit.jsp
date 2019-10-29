<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="软件开发商编辑" autoLoadData="${param.id!=null}"
	dataSrc="/esb/web/spCompanyManager/getSpCompany.json?id=${param.id}">
	<youi:form id="form_spCompany" panel="false" submit="NOT" reset="NOT" action="/esb/web/spCompanyManager/saveSpCompany.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.spCompany.id"/>
			<youi:fieldText property="agencyId"  caption="i18n.spCompany.agencyId"/>
			<youi:fieldText property="companyCaption"  caption="i18n.spCompany.companyCaption"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_spCompany',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_spCompany_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存软件开发商成功.');
		$elem('form_spCompany',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>