<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="业务系统">
	
	<youi:subpage height="150" src="page/${_pagePath}/softProjectEdit.html?id={id}" subpageId="softProject_edit" caption="修改软件系统" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/softProjectEdit.html" subpageId="softProject_add" caption="新增软件系统" type="dialog"/>

    <%-- 工具栏 --%>
    <youi:toolbar refWidgetId="grid_softProject">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="plus"/>
    </youi:toolbar>
    <%-- 列表数据 --%>
	<youi:grid id="grid_softProject" src="/baseServices/services/softProjectManager/getPagerSoftProjects.json"
		removeSrc="/baseServices/services/softProjectManager/removeSoftProject.json" idKeys="id"
		showCheckbox="true" showNum="true" reset="NOT" query="NOT">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="projectCaption"  caption="系统名称"/>
			<youi:fieldText property="projectCode"  caption="系统编码"/>
			<youi:fieldText property="agencyId" column="2" caption="所属机构"/>
		</youi:fieldLayout>
		<youi:gridCol width="10%" property="agencyId"  caption="所属机构" orderBy="desc"/>
		<youi:gridCol width="15%" property="projectCaption"  caption="系统名称" orderBy="desc"/>
		<youi:gridCol width="15%" property="projectCode"  caption="系统编码" orderBy="desc"/>
		<youi:gridCol width="15%" property="projectOwner"  caption="系统负责人" orderBy="desc"/>
		<youi:gridCol width="15%" property="businessOwner"  caption="业务负责人"/>
		<youi:gridCol width="15%" property="supportOperator"  caption="业务支持人"/>
		<youi:gridCol width="15%" property="enableDate"  caption="上线日期" orderBy="desc"/>

		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改软件系统弹出页 -->
	<youi:func name="grid_softProject_edit" params="dom,options,record">
		$elem('subpage_softProject_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增软件系统弹出页 -->
	<youi:func name="grid_softProject_add" params="dom,options">
		$elem('subpage_softProject_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑软件系统的subpage内容变化回调函数 -->
	<youi:func name="subpage_softProject_edit_change" params="record">
		$elem('grid_softProject',pageId).grid('refresh');
	</youi:func>
	<!-- 新增软件系统的subpage内容变化回调函数 -->
	<youi:func name="subpage_softProject_add_change" params="record">
		$elem('grid_softProject',pageId).grid('refresh');
	</youi:func>
	
</youi:page>