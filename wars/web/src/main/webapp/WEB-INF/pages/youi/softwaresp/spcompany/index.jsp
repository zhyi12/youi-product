<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="软件开发商">
	
	<youi:subpage height="150" src="page/${_pagePath}/spCompanyEdit.html?id={id}" subpageId="spCompany_edit" caption="修改软件开发商" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/spCompanyEdit.html" subpageId="spCompany_add" caption="添加软件开发商" type="dialog"/>

	<youi:toolbar refWidgetId="grid_spCompany">
		<youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
		<youi:toolbarItem name="add" caption="新增" tooltips="" icon="save"/>
	</youi:toolbar>
	<youi:grid id="grid_spCompany" src="/softwarespServices/services/spCompanyManager/getPagerSpCompanys.json" 
		removeSrc="/softwarespServices/services/spCompanyManager/removeSpCompany.json" idKeys="id"
		showCheckbox="true" query="NOT" reset="NOT">
		<youi:fieldLayout prefix="query" labelWidths="120,120">
			<youi:fieldText property="agencyId"  caption="开发商编码"/>
			<youi:fieldText property="companyCaption"  caption="开发商名称"/>
		</youi:fieldLayout>

		<youi:gridCol width="20%" property="agencyId"  caption="开发商编码"/>
		<youi:gridCol width="40%" property="companyCaption"  caption="开发商名称"/>
		<youi:gridCol width="30%" property="shortCaption"  caption="开发商简称"/>

		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改软件开发商弹出页 -->
	<youi:func name="grid_spCompany_edit" params="dom,options,record">
		$elem('subpage_spCompany_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增软件开发商弹出页 -->
	<youi:func name="grid_spCompany_add" params="dom,options">
		$elem('subpage_spCompany_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑软件开发商的subpage内容变化回调函数 -->
	<youi:func name="subpage_spCompany_edit_change" params="record">
		$elem('grid_spCompany',pageId).grid('refresh');
	</youi:func>
	<!-- 新增软件开发商的subpage内容变化回调函数 -->
	<youi:func name="subpage_spCompany_add_change" params="record">
		$elem('grid_spCompany',pageId).grid('refresh');
	</youi:func>
	
</youi:page>