<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="${param.title}-代码项管理">
	
	<youi:subpage height="150" src="page/${_pagePath}/codeitemEdit.html?codeitemId={codeitemId}"
		subpageId="codeitem_edit" caption="修改代码项" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/codeitemEdit.html?_timestamp={_timestamp}&codemapId=${param.codemapId}"
	subpageId="codeitem_add" caption="添加代码项" type="dialog"></youi:subpage>

	<youi:grid id="grid_codeitem" idKeys="codeitemId" caption="代码项列表"  styleClass="grid-query-inline"
				src="/baseServices/services/codeitemManager/getPagerCodeitems.json?codemapId=${param.codemapId}" panel="false"
				removeSrc="/baseServices/services/codeitemManager/removeCodeitem.json" 
				showNum="true" showCheckbox="false">
		<youi:fieldLayout>
			<youi:fieldText property="itemValue" caption="代码值"/>
			<youi:fieldText property="itemCaption" caption="代码描述"/>
		</youi:fieldLayout>

		<youi:gridCol width="20%" property="itemValue"  caption="代码值"/>
		<youi:gridCol width="30%" property="itemCaption"  caption="代码描述"/>
		<youi:gridCol width="30%" property="codemap.caption"  caption="所属代码集"/>
		
		<youi:gridCol width="20%" type="button" property="button" caption="操作">
			<youi:button name="edit" icon="rename" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="delete-slender" caption="删除"/>
		</youi:gridCol>

		<youi:button name="add" caption="添加" icon="plus"/>
	</youi:grid>
	
	<!-- Grid新增按钮动作：打开新增代码项弹出页 -->
	<youi:func name="grid_codeitem_add" params="dom,options">
		$elem('subpage_codeitem_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	
	<!-- Grid按钮动作：打开新增代码项弹出页 -->
	<youi:func name="grid_codeitem_edit" params="dom,options,record">
		$elem('subpage_codeitem_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	
	<youi:func name="subpage_codeitem_edit_change" params="record">
		$elem('grid_codeitem',pageId).grid('refresh');
	</youi:func>
	
	<youi:func name="subpage_codeitem_add_change" params="record">
		$elem('grid_codeitem',pageId).grid('refresh');
	</youi:func>
</youi:page>