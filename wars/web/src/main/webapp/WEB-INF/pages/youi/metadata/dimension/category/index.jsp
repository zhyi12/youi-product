<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="分组字典">
	
	<youi:subpage height="150" src="page/${_pagePath}/metaCategoryEdit.html?id={id}" subpageId="metaCategory_edit" caption="修改分类属性" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/metaCategoryEdit.html" subpageId="metaCategory_add" caption="新增分类属性" type="dialog"/>

    <%-- 工具栏 --%>
    <youi:toolbar refWidgetId="grid_metaCategory">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="save"/>
    </youi:toolbar>
    <%-- 列表数据 --%>
	<youi:grid id="grid_metaCategory" src="/metadataServices/services/metaCategoryManager/getPagerMetaCategorys.json"
		removeSrc="/metadataServices/services/metaCategoryManager/removeMetaCategory.json" idKeys="id"
		showCheckbox="true" reset="NOT" query="NOT">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="text"  caption="分组名称"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" property="id"  caption="分组编码"/>
		<youi:gridCol width="35%" property="text"  caption="分组名称"/>
		<youi:gridCol width="15%" property="expression"  caption="分组类型"/>
		<youi:gridCol width="15%" property="pre"  caption="所属专业"/>
		<youi:gridCol width="10%" property="activeTime"  caption="启用时间"/>
		<youi:gridCol width="10%" property="expireTime"  caption="到期时间"/>

		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改分类属性弹出页 -->
	<youi:func name="grid_metaCategory_edit" params="dom,options,record">
		$elem('subpage_metaCategory_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增分类属性弹出页 -->
	<youi:func name="grid_metaCategory_add" params="dom,options">
		$elem('subpage_metaCategory_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑分类属性的subpage内容变化回调函数 -->
	<youi:func name="subpage_metaCategory_edit_change" params="record">
		$elem('grid_metaCategory',pageId).grid('refresh');
	</youi:func>
	<!-- 新增分类属性的subpage内容变化回调函数 -->
	<youi:func name="subpage_metaCategory_add_change" params="record">
		$elem('grid_metaCategory',pageId).grid('refresh');
	</youi:func>
	
</youi:page>