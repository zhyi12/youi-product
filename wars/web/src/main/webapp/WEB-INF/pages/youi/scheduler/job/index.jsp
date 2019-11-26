<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="定时任务">

	<youi:ajaxUrl name="pauseUrl" src="/schedulerServices/services/schedulerService/pause.json?schedName={schedName}&triggerGroup={triggerGroup}&triggerName={triggerName}"/>
	<youi:ajaxUrl name="resumeUrl" src="/schedulerServices/services/schedulerService/resume.json?schedName={schedName}&triggerGroup={triggerGroup}&triggerName={triggerName}"/>

	<youi:subpage src="page/${_pagePath}/jobedit.html?schedName={schedName}&triggerGroup={triggerGroup}&triggerName={triggerName}"
				  subpageId="job_edit" caption="编辑定时任务" type="dialog"></youi:subpage>

	<youi:subpage src="page/${_pagePath}/jobedit.html?_timestamp={timestamp}"
				  subpageId="job_add" caption="新增定时任务" type="dialog"></youi:subpage>

	<youi:toolbar refWidgetId="grid_job">
		<youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
		<youi:toolbarItem name="add" caption="新增" tooltips="" icon="plus"/>
	</youi:toolbar>

	<youi:grid styleClass="col-sm-12 no-padding" id="grid_job" reset="NOT" query="NOT" idKeys="schedName,cronTrigger.triggerGroup,cronTrigger.triggerName"
			   src="/schedulerServices/services/schedulerService/getPagerSchedulerJobs.json">
		<youi:gridCol width="10%" caption="服务名" property="cronTrigger.triggerGroup"></youi:gridCol>
		<youi:gridCol width="10%" caption="任务标识" nowrap="false" property="cronTrigger.triggerName"></youi:gridCol>
		<youi:gridCol width="10%" caption="交易组名" property="jobDetails.jobGroup"></youi:gridCol>
		<youi:gridCol width="10%" caption="交易名" property="jobDetails.jobName"></youi:gridCol>

		<youi:gridCol width="15%" caption="任务描述" nowrap="nowrap" property="jobDetails.description"></youi:gridCol>
		<youi:gridCol width="10%" caption="首次启动时间" property="startTime" type="date" format="millis" textFormat="yyyy-MM-dd HH:mm:ss"></youi:gridCol>
		<youi:gridCol width="10%" caption="上次执行时间" property="prevFireTime" type="date" format="millis" textFormat="yyyy-MM-dd HH:mm:ss"></youi:gridCol>
		<youi:gridCol width="10%" caption="下次执行时间" property="nextFireTime" type="date" format="millis" textFormat="yyyy-MM-dd HH:mm:ss"></youi:gridCol>
		<youi:gridCol width="10%" caption="任务状态" property="triggerState" renderer="renderer_triggerState"></youi:gridCol>

		<youi:gridCol width="10%" type="button" property="button" caption="操作" align="center">
			<youi:button name="edit" icon="edit" caption="编辑"></youi:button>
			<youi:button name="remove" icon="remove" caption="删除"></youi:button>
		</youi:gridCol>
	</youi:grid>

	<youi:func name="renderer_triggerState" params="col,record">
		var htmls = [];
		if(record.triggerState=='PAUSED'){
			htmls.push('已暂停,'+'<a title="恢复" class="youi-icon resume icon-play-circle" data-command="gridCommand" data-name="resume">恢复</a>');
		}else{
			htmls.push('运行中,'+'<a title="暂停" class="youi-icon pause icon-pause" data-command="gridCommand" data-name="pause">暂停</a>');
		}
		return htmls.join('');
	</youi:func>
	
	<youi:func name="grid_job_add">
		$elem('subpage_job_add',pageId).subpage('open',{},'',{});
	</youi:func>

	<youi:func name="grid_job_edit" params="dom,commandOptions,record">
		$elem('subpage_job_edit',pageId).subpage('open',{},'',{schedName:record.schedName,
		triggerGroup:record.cronTrigger.triggerGroup,triggerName:record.cronTrigger.triggerName});
	</youi:func>
	<%-- 表格内按钮回调：暂停任务 --%>
	<youi:func name="grid_job_pause" params="dom,commandOptions,record">
		$.youi.messageUtils.confirm('确认暂停任务?',function(){
			$.youi.pageUtils.doPageFunc('doPauseJob',pageId,null,record);
		});
	</youi:func>
	<%-- 表格内按钮回调：恢复任务 --%>
	<youi:func name="grid_job_resume" params="dom,commandOptions,record">
		$.youi.messageUtils.confirm('确认恢复任务?',function(){
			$.youi.pageUtils.doPageFunc('doResumeJob',pageId,null,record);
		});
	</youi:func>

	<%-- 执行暂停任务 --%>
	<youi:func name="doPauseJob" urls="pauseUrl" params="record">
		var paramRecord = {schedName:record.schedName,triggerGroup:record.cronTrigger.triggerGroup,triggerName:record.cronTrigger.triggerName};
		var url = $.youi.recordUtils.replaceByRecord(funcUrls.pauseUrl,paramRecord);

		$.youi.ajaxUtils.ajax({
			url:url,
			success:function(result){
				$elem('grid_job',pageId).grid('refresh');//刷新表格
			}
		});
	</youi:func>

	<%-- 执行恢复任务 --%>
	<youi:func name="doResumeJob" urls="resumeUrl" params="record">
		var paramRecord = {schedName:record.schedName,triggerGroup:record.cronTrigger.triggerGroup,triggerName:record.cronTrigger.triggerName};
		var url = $.youi.recordUtils.replaceByRecord(funcUrls.resumeUrl,paramRecord);

		$.youi.ajaxUtils.ajax({
			url:url,
			success:function(result){
				$elem('grid_job',pageId).grid('refresh');//刷新表格
			}
		});
	</youi:func>


	<!-- 编辑数据资源的subpage内容变化回调函数 -->
	<youi:func name="subpage_job_add_change" params="record">
		$elem('grid_job',pageId).grid('refresh');//刷新表格
	</youi:func>

	<youi:func name="subpage_job_edit_change" params="record">
		$elem('grid_job',pageId).grid('refresh');//刷新表格
	</youi:func>
</youi:page>