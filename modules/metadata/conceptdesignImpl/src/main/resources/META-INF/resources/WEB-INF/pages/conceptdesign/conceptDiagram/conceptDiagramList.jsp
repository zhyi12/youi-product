<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="概念模型图管理">
	
	<youi:subpage height="150" src="/page/${_pagePath}/conceptDiagramEdit.html?id={id}" subpageId="conceptDiagram_edit" caption="修改概念模型图" type="dialog"/>
	<youi:subpage height="150" src="/page/${_pagePath}/conceptDiagramEdit.html" subpageId="conceptDiagram_add" caption="添加概念模型图" type="dialog"/>
	
	<youi:grid id="grid_conceptDiagram" src="/esb/web/conceptDiagramManager/getPagerConceptDiagrams.json" 
		removeSrc="/esb/web/conceptDiagramManager/removeConceptDiagram.json" idKeys="id"
		showNum="true" showCheckbox="true">
		<youi:fieldLayout prefix="query">
		</youi:fieldLayout>


		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改概念模型图弹出页 -->
	<youi:func name="grid_conceptDiagram_edit" params="dom,options,record">
		$elem('subpage_conceptDiagram_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增概念模型图弹出页 -->
	<youi:func name="grid_conceptDiagram_add" params="dom,options">
		$elem('subpage_conceptDiagram_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑概念模型图的subpage内容变化回调函数 -->
	<youi:func name="subpage_conceptDiagram_edit_change" params="record">
		$elem('grid_conceptDiagram',pageId).grid('refresh');
	</youi:func>
	<!-- 新增概念模型图的subpage内容变化回调函数 -->
	<youi:func name="subpage_conceptDiagram_add_change" params="record">
		$elem('grid_conceptDiagram',pageId).grid('refresh');
	</youi:func>
	
</youi:page>