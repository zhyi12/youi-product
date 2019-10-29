<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="" autoLoadData="${param.id!=null}"  pageId="${param.dialogId}"
	dataSrc="/metadataServices/services/dataDemandManager/getDataDemand.json?id=${param.id}">
	<youi:form id="form" panel="false" submit="NOT" reset="NOT" action="/metadataServices/services/dataDemandManager/saveDataDemand.json"
		idKeys="id">
		<youi:fieldLayout columns="1" labelWidths="100">
			<youi:fieldHidden property="id"  caption="i18n.DataDemand.id"/>
			<youi:fieldHidden property="realmId"  caption="realmId"/>
			<youi:fieldText notNull="true" property="demandCaption"  caption="需求名称"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>

	<%-- init 触发主页面subpage的组件的initPageData事件，并设置页面异步加载完成后的回调函数 --%>
	<youi:func name="init">
		$('#'+pageId).trigger('initPageData',{callback:'P_'+pageId+'_initPageData'});
	</youi:func>

	<%-- 页面加载完成，默认的form表单数据设置后的操作 --%>
	<youi:func name="initPageData" params="record">

	</youi:func>
</youi:page>