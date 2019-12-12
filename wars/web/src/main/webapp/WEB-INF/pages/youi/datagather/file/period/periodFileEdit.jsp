<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="固定期文件编辑" autoLoadData="${param.id!=null}"
	dataSrc="/dataFileServices/services/periodFileManager/getPeriodFile.json?id=${param.id}">
	<youi:form id="form_periodFile" panel="false" submit="NOT" reset="NOT" action="/dataFileServices/services/periodFileManager/savePeriodFile.json"
		idKeys="id">
		<youi:fieldLayout columns="1" labelWidths="140">
			<youi:fieldHidden property="id"  caption="i18n.periodFile.id"/>
			<youi:fieldHidden property="createTime"  caption="创建时间"/>
			<youi:fieldCustom notNull="true" custom="fieldPlupload"
							  customOptions="{limits:1,mineTypeCaption:'',mineTypeExtensions:'xls,xlsx,csv,dbf,zip'}"
							  property="filePath" caption="选择文件"/>
			<youi:fieldText property="startTime"  caption="计划开始时间"/>
			<youi:fieldText property="endTime"  caption="计划结束时间"/>
			<youi:fieldText property="periodId"  caption="报告期"/>
			<youi:fieldText property="surveyTaskId"  caption="调查方案"/>
			<youi:fieldText property="uploadTime"  caption="上传时间"/>
			<youi:fieldText property="agencyId"  caption="机构"/>
			<youi:fieldText property="reportCode"  caption="报表"/>
			<youi:fieldText property="memo"  caption="备注"/>
			<youi:fieldText property="areaId"  caption="行政区划"/>

		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_periodFile',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_periodFile_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存固定期文件成功.');
		$elem('form_periodFile',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>