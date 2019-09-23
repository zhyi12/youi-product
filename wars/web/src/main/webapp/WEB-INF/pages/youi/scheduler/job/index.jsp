<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="定时任务">
	
	<youi:subpage src="page/${_pagePath}/jobedit.html" subpageId="job_edit" caption="edit" type="dialog"></youi:subpage>

	<youi:grid styleClass="col-sm-12" id="grid_job" src="/schedulerServices/services/schedulerService/getPagerSchedulerJobs.json">
		<youi:gridCol caption="schedName" property="schedName"></youi:gridCol>
		<youi:gridCol caption="triggerName" property="triggerName"></youi:gridCol>
	</youi:grid>

	<youi:form id="form_job" action="/schedulerServices/services/schedulerService/saveSchedulerJob.json" styleClass="col-sm-12"  reset="NOT"
			   caption='<span data-command="pageCommand" data-name="edit" class="youi-icon icon-edit"></span>定时任务'>
		<youi:fieldLayout labelWidths="100">
			<youi:fieldText property="schedName" caption="调度任务"/>
			<youi:fieldText property="cronTrigger.triggerName" caption="触发器"/>
			<youi:fieldText property="cronTrigger.triggerGroup" caption="触发器组"/>

			<youi:fieldText property="serverName" defaultValue="metadataServices" caption="服务"/>
			<youi:fieldText property="jobDetails.jobGroup" defaultValue="dataDictionaryClean" caption="交易组"/>
			<youi:fieldText property="jobDetails.jobName" defaultValue="clean" caption="交易名"/>

			<youi:fieldText property="jobDetails.description" caption="任务描述"/>
			<youi:fieldText property="description" caption="调度描述"/>

			<youi:fieldCalendar property="startTime"  caption="开始时间"/>
			<youi:fieldCalendar property="endTime" caption="结束时间"/>
			<youi:fieldCustom column="2" property="cronTrigger.cronExpression" custom="fieldCron" caption="调度周期"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:func name="edit">
		$elem('subpage_job_edit',pageId).subpage('open',{},'',{});
	</youi:func>
</youi:page>