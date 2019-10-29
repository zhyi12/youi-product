<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="软件开发商编辑" autoLoadData="${param.id!=null}"
	dataSrc="/softwarespServices/services/spCompanyManager/getSpCompany.json?id=${param.id}">
	<youi:form id="form_spCompany" panel="false" submit="NOT" reset="NOT" action="/softwarespServices/services/spCompanyManager/saveSpCompany.json"
		idKeys="id">
		<youi:fieldLayout columns="1" labelWidths="120">
			<youi:fieldHidden property="id"  caption="i18n.spCompany.id"/>
			<youi:fieldText notNull="true" property="agencyId"  caption="开发商编码"/>
			<youi:fieldText notNull="true" property="companyCaption"  caption="开发商名称"/>
			<youi:fieldText notNull="true" property="shortCaption"  caption="开发商简称"/>
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