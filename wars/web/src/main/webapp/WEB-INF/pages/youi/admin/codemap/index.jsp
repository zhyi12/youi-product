<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="代码集管理">

	<youi:subpage height="150" src="page/${_pagePath}/codemapEdit.html?codemapId={codemapId}" subpageId="codemap_edit" caption="修改代码集" type="dialog"></youi:subpage>
	<youi:subpage height="150" src="page/${_pagePath}/codemapEdit.html?_timestamp={_timestamp}" subpageId="codemap_add" caption="添加代码集" type="dialog"></youi:subpage>
	
	<youi:subpage src="page/${_pagePath}/codeitemList.html?codemapId={codemapId}&title={caption}" subpageId="codemap_editItems"
		 caption="" type="secondPage"></youi:subpage>
	
	<!-- grid-代码集列表-->
	<youi:grid id="grid_codemap" idKeys="codemapId" caption="代码集列表" styleClass="grid-query-inline"
				src="/baseServices/services/codemapManager/getPagerCodemaps.json"
				showCheckbox="true" panel="false"
				removeSrc="/baseServices/services/codemapManager/removeCodemap.json">
		<youi:fieldLayout labelWidths="100,100">
			<youi:fieldText operator="LIKE" property="code"  caption="代码集编码"/>
			<youi:fieldText property="caption"  caption="代码集描述"/>
		</youi:fieldLayout>
		
		<youi:gridCol property="code" width="15%" caption="代码集编码"/>
		<youi:gridCol property="caption" width="65%" caption="代码集描述"/>
		
		<youi:gridCol width="15%" type="button" property="button" caption="操作">
			<youi:button name="edit" icon="rename" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="editItems" icon="daimaxiang" caption="代码项"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="delete-slender" caption="删除"/>
		</youi:gridCol>
		
		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid新增按钮动作：打开新增代码集弹出页 -->
	<youi:func name="grid_codemap_add" params="dom,options">
		$elem('subpage_codemap_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	
	<!-- Grid按钮动作：打开新增代码集弹出页 -->
	<youi:func name="grid_codemap_edit" params="dom,options,record">
		$elem('subpage_codemap_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	
	<!-- Grid按钮动作：打开新增代码集弹出页 -->
	<youi:func name="grid_codemap_editItems" params="dom,options,record">
		$elem('subpage_codemap_editItems',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	
	<youi:func name="subpage_codemap_edit_change" params="record">
		$elem('grid_codemap',pageId).grid('refresh');
	</youi:func>
	
	<youi:func name="subpage_codemap_add_change" params="record">
		$elem('grid_codemap',pageId).grid('refresh');
	</youi:func>
</youi:page>