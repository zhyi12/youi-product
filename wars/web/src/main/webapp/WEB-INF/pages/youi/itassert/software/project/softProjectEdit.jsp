<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="业务系统编辑" autoLoadData="${param.id!=null}"
	dataSrc="/baseServices/services/softProjectManager/getSoftProject.json?id=${param.id}">
	<youi:form id="form_softProject" panel="false" submit="NOT" reset="NOT" action="/baseServices/services/softProjectManager/saveSoftProject.json"
		idKeys="id">
		<youi:fieldLayout columns="1" labelWidths="100">
			<youi:fieldHidden property="id"  caption="i18n.SoftProject.id"/>
			<youi:fieldText notNull="true" property="projectCaption"  caption="系统名称"/>
			<youi:fieldText notNull="true" property="projectCode"  caption="系统编码"/>
			<youi:fieldText property="agencyId"  caption="所属机构"/>
			<youi:fieldText property="projectOwner"  caption="系统负责人"/>
			<youi:fieldText property="businessOwner"  caption="业务负责人"/>
			<youi:fieldText property="supportOperator"  caption="业务支持人"/>
			<youi:fieldCalendar  property="enableDate"  caption="上线日期"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_softProject',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_softProject_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存业务系统成功.');
		$elem('form_softProject',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>