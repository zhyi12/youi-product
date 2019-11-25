<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="编辑任务" autoLoadData="${param.triggerName!=null}"
		   dataSrc="/schedulerServices/services/schedulerService/getSchedulerJob.json?schedName=${param.schedName}&triggerGroup=${param.triggerGroup}&triggerName=${param.triggerName}">

	<youi:form id="form_job" panel="false" idKeys="schedName,cronTrigger.triggerName,cronTrigger.triggerGroup"
			   action="/schedulerServices/services/schedulerService/saveSchedulerJob.json" styleClass="col-sm-12"  reset="NOT" submit="NOT">
		<youi:fieldLayout labelWidths="120,120">
			<youi:fieldHidden property="schedName" caption="调度任务"/>
			<youi:fieldHidden styleClass="string" property="cronTrigger.triggerName" caption="触发器"/>

			<youi:fieldText notNull="true" column="2" property="cronTrigger.triggerGroup" caption="服务"/>
			<youi:fieldText notNull="true" property="jobDetails.jobGroup" caption="交易组"/>
			<youi:fieldText notNull="true" property="jobDetails.jobName" caption="交易名"/>

			<youi:fieldText column="2" property="jobDetails.description" caption="任务描述"/>

			<youi:fieldCalendar property="startTime"  caption="开始时间" format="millis" textFormat="yyyy-MM-dd HH:mm:ss"/>
			<youi:fieldCalendar property="endTime" caption="结束时间"/>
			<youi:fieldCustom notNull="true"  column="2" property="cronTrigger.cronExpression" custom="fieldCron" caption="调度周期"/>
		</youi:fieldLayout>
	</youi:form>

	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>

	<youi:func name="init" params="result">
		if(result && result.record){
			$elem('form_job',pageId).form('fillRecord',result.record);
		}
	</youi:func>

	<youi:func name="form_job_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存任务成功.');
		$elem('form_job',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>