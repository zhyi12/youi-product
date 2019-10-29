<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="机构数据管理">
	
	<youi:subpage height="150" src="page/${_pagePath}/AgencyDataEdit.html?id={id}" subpageId="AgencyData_edit" caption="修改机构数据" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/AgencyDataEdit.html" subpageId="AgencyData_add" caption="新增机构数据" type="dialog"/>

    <%-- 工具栏 --%>
    <youi:toolbar refWidgetId="grid_AgencyData">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="save"/>
    </youi:toolbar>
    <%-- 列表数据 --%>
	<youi:grid id="grid_AgencyData" src="/someServices/services/AgencyDataManager/getPagerAgencyDatas.json"
		removeSrc="/someServices/services/AgencyDataManager/removeAgencyData.json" idKeys="id"
		showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="dataIds"  caption="i18n.AgencyData.dataIds"/>
		</youi:fieldLayout>


		<youi:gridCol width="15%" property="dataIds"  caption="i18n.AgencyData.dataIds"/>
		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改机构数据弹出页 -->
	<youi:func name="grid_AgencyData_edit" params="dom,options,record">
		$elem('subpage_AgencyData_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增机构数据弹出页 -->
	<youi:func name="grid_AgencyData_add" params="dom,options">
		$elem('subpage_AgencyData_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑机构数据的subpage内容变化回调函数 -->
	<youi:func name="subpage_AgencyData_edit_change" params="record">
		$elem('grid_AgencyData',pageId).grid('refresh');
	</youi:func>
	<!-- 新增机构数据的subpage内容变化回调函数 -->
	<youi:func name="subpage_AgencyData_add_change" params="record">
		$elem('grid_AgencyData',pageId).grid('refresh');
	</youi:func>
	
</youi:page>