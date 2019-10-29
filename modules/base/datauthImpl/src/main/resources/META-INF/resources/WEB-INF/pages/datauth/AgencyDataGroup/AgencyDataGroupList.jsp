<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="机构数据权限组管理">
	
	<youi:subpage height="150" src="page/${_pagePath}/AgencyDataGroupEdit.html?id={id}" subpageId="AgencyDataGroup_edit" caption="修改机构数据权限组" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/AgencyDataGroupEdit.html" subpageId="AgencyDataGroup_add" caption="新增机构数据权限组" type="dialog"/>

    <%-- 工具栏 --%>
    <youi:toolbar refWidgetId="grid_AgencyDataGroup">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="save"/>
    </youi:toolbar>
    <%-- 列表数据 --%>
	<youi:grid id="grid_AgencyDataGroup" src="/someServices/services/AgencyDataGroupManager/getPagerAgencyDataGroups.json"
		removeSrc="/someServices/services/AgencyDataGroupManager/removeAgencyDataGroup.json" idKeys="id"
		showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="agencyId"  caption="i18n.AgencyDataGroup.agencyId"/>
			<youi:fieldText property="agencyType"  caption="i18n.AgencyDataGroup.agencyType"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" property="agencyId"  caption="i18n.AgencyDataGroup.agencyId"/>
		<youi:gridCol width="15%" property="agencyType"  caption="i18n.AgencyDataGroup.agencyType"/>

		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改机构数据权限组弹出页 -->
	<youi:func name="grid_AgencyDataGroup_edit" params="dom,options,record">
		$elem('subpage_AgencyDataGroup_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增机构数据权限组弹出页 -->
	<youi:func name="grid_AgencyDataGroup_add" params="dom,options">
		$elem('subpage_AgencyDataGroup_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑机构数据权限组的subpage内容变化回调函数 -->
	<youi:func name="subpage_AgencyDataGroup_edit_change" params="record">
		$elem('grid_AgencyDataGroup',pageId).grid('refresh');
	</youi:func>
	<!-- 新增机构数据权限组的subpage内容变化回调函数 -->
	<youi:func name="subpage_AgencyDataGroup_add_change" params="record">
		$elem('grid_AgencyDataGroup',pageId).grid('refresh');
	</youi:func>
	
</youi:page>