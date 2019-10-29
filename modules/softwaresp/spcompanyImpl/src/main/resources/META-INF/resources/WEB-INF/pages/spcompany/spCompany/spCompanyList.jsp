<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="软件开发商管理">
	
	<youi:subpage height="150" src="/page/${_pagePath}/spCompanyEdit.html?id={id}" subpageId="spCompany_edit" caption="修改软件开发商" type="dialog"/>
	<youi:subpage height="150" src="/page/${_pagePath}/spCompanyEdit.html" subpageId="spCompany_add" caption="添加软件开发商" type="dialog"/>
	
	<youi:grid id="grid_spCompany" src="/esb/web/spCompanyManager/getPagerSpCompanys.json" 
		removeSrc="/esb/web/spCompanyManager/removeSpCompany.json" idKeys="id"
		showNum="true" showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="agencyId"  caption="i18n.spCompany.agencyId"/>
			<youi:fieldText property="companyCaption"  caption="i18n.spCompany.companyCaption"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" property="agencyId"  caption="i18n.spCompany.agencyId"/>
		<youi:gridCol width="15%" property="companyCaption"  caption="i18n.spCompany.companyCaption"/>

		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
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