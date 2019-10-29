<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="机构管理">
	
	<youi:subpage height="150" src="/page/${_pagePath}/agencyEdit.html?id={id}" subpageId="agency_edit" caption="修改机构" type="dialog"/>
	<youi:subpage height="150" src="/page/${_pagePath}/agencyEdit.html" subpageId="agency_add" caption="添加机构" type="dialog"/>
	
	<youi:grid id="grid_agency" src="/esb/web/agencyManager/getPagerAgencys.json" 
		removeSrc="/esb/web/agencyManager/removeAgency.json" idKeys="id"
		showNum="true" showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="caption"  caption="i18n.agency.caption"/>
			<youi:fieldText property="code"  caption="i18n.agency.code"/>
			<youi:fieldText property="areaId"  caption="i18n.agency.areaId"/>
			<youi:fieldText property="parentId"  caption="i18n.agency.parentId"/>
			<youi:fieldText property="num"  caption="i18n.agency.num"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" property="caption"  caption="i18n.agency.caption"/>
		<youi:gridCol width="15%" property="code"  caption="i18n.agency.code"/>
		<youi:gridCol width="15%" property="areaId"  caption="i18n.agency.areaId"/>
		<youi:gridCol width="15%" property="parentId"  caption="i18n.agency.parentId"/>
		<youi:gridCol width="15%" property="num"  caption="i18n.agency.num"/>

		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改机构弹出页 -->
	<youi:func name="grid_agency_edit" params="dom,options,record">
		$elem('subpage_agency_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增机构弹出页 -->
	<youi:func name="grid_agency_add" params="dom,options">
		$elem('subpage_agency_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑机构的subpage内容变化回调函数 -->
	<youi:func name="subpage_agency_edit_change" params="record">
		$elem('grid_agency',pageId).grid('refresh');
	</youi:func>
	<!-- 新增机构的subpage内容变化回调函数 -->
	<youi:func name="subpage_agency_add_change" params="record">
		$elem('grid_agency',pageId).grid('refresh');
	</youi:func>
	
</youi:page>