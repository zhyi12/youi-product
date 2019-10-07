<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="模型项管理">
	
	<youi:subpage height="150" src="/page/${_pagePath}/conceptItemEdit.html?id={id}" subpageId="conceptItem_edit" caption="修改模型项" type="dialog"/>
	<youi:subpage height="150" src="/page/${_pagePath}/conceptItemEdit.html" subpageId="conceptItem_add" caption="添加模型项" type="dialog"/>
	
	<youi:grid id="grid_conceptItem" src="/esb/web/conceptItemManager/getPagerConceptItems.json" 
		removeSrc="/esb/web/conceptItemManager/removeConceptItem.json" idKeys="id"
		showNum="true" showCheckbox="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldText property="name"  caption="i18n.conceptItem.name"/>
			<youi:fieldText property="group"  caption="i18n.conceptItem.group"/>
			<youi:fieldText property="caption"  caption="i18n.conceptItem.caption"/>
			<youi:fieldText property="parentId"  caption="i18n.conceptItem.parentId"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" property="name"  caption="i18n.conceptItem.name"/>
		<youi:gridCol width="15%" property="group"  caption="i18n.conceptItem.group"/>
		<youi:gridCol width="15%" property="caption"  caption="i18n.conceptItem.caption"/>
		<youi:gridCol width="15%" property="parentId"  caption="i18n.conceptItem.parentId"/>

		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改模型项弹出页 -->
	<youi:func name="grid_conceptItem_edit" params="dom,options,record">
		$elem('subpage_conceptItem_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增模型项弹出页 -->
	<youi:func name="grid_conceptItem_add" params="dom,options">
		$elem('subpage_conceptItem_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑模型项的subpage内容变化回调函数 -->
	<youi:func name="subpage_conceptItem_edit_change" params="record">
		$elem('grid_conceptItem',pageId).grid('refresh');
	</youi:func>
	<!-- 新增模型项的subpage内容变化回调函数 -->
	<youi:func name="subpage_conceptItem_add_change" params="record">
		$elem('grid_conceptItem',pageId).grid('refresh');
	</youi:func>
	
</youi:page>