<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="计量单位编辑" autoLoadData="${param.id!=null}"
	dataSrc="/someServices/services/measureUnitManager/getMeasureUnit.json?id=${param.id}">
	<youi:form id="form_measureUnit" panel="false" submit="NOT" reset="NOT" action="/someServices/services/measureUnitManager/saveMeasureUnit.json"
		idKeys="id">
		<youi:fieldLayout columns="1">
			<youi:fieldHidden property="id"  caption="i18n.measureUnit.id"/>
			<youi:fieldText property="measurement"  caption="i18n.measureUnit.measurement"/>
			<youi:fieldText property="unitCaption"  caption="i18n.measureUnit.unitCaption"/>
			<youi:fieldText property="rate"  caption="i18n.measureUnit.rate"/>
			<youi:fieldText property="baseUnitId"  caption="i18n.measureUnit.baseUnitId"/>
			<youi:fieldText property="description"  caption="i18n.measureUnit.description"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_measureUnit',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_measureUnit_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存计量单位成功.');
		$elem('form_measureUnit',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>