<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="时间属性管理">
	
	<youi:subpage height="150" src="page/${_pagePath}/metaAttrEdit.html?id={id}" subpageId="metaAttr_edit" caption="修改时间属性" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/metaAttrEdit.html" subpageId="metaAttr_add" caption="新增时间属性" type="dialog"/>

    <%-- 工具栏 --%>
    <youi:toolbar refWidgetId="grid_metaAttr">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="save"/>
    </youi:toolbar>
    <%-- 列表数据 --%>
	<youi:grid id="grid_metaAttr" src="/metadataServices/services/metaAttrManager/getPagerMetaAttrs.json"
		removeSrc="/metadataServices/services/metaAttrManager/removeMetaAttr.json" idKeys="id" reset="NOT" query="NOT"
		showCheckbox="true">
		<youi:fieldLayout prefix="query" columns="1">
			<youi:fieldText property="text"  caption="属性名称"/>
		</youi:fieldLayout>

		<youi:gridCol width="10%" property="id"  caption="属性编码"/>
		<youi:gridCol width="35%" property="text"  caption="属性名称"/>
		<youi:gridCol width="45%" property="expression"  caption="时间表达式"/>
		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改时间属性弹出页 -->
	<youi:func name="grid_metaAttr_edit" params="dom,options,record">
		$elem('subpage_metaAttr_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增时间属性弹出页 -->
	<youi:func name="grid_metaAttr_add" params="dom,options">
		$elem('subpage_metaAttr_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑时间属性的subpage内容变化回调函数 -->
	<youi:func name="subpage_metaAttr_edit_change" params="record">
		$elem('grid_metaAttr',pageId).grid('refresh');
	</youi:func>
	<!-- 新增时间属性的subpage内容变化回调函数 -->
	<youi:func name="subpage_metaAttr_add_change" params="record">
		$elem('grid_metaAttr',pageId).grid('refresh');
	</youi:func>
	
</youi:page>