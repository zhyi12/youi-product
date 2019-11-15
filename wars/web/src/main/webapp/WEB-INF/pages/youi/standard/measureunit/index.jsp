<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="计量单位">
	
	<youi:subpage height="150" src="page/${_pagePath}/measureUnitEdit.html?id={id}" subpageId="measureUnit_edit" caption="修改计量单位" type="dialog"/>
	<youi:subpage height="150" src="page/${_pagePath}/measureUnitEdit.html" subpageId="measureUnit_add" caption="新增计量单位" type="dialog"/>

	<youi:convert name="baseUnitConvert" show="unitCaption" code="id" usecache="false"
				  src="/metadataServices/services/measureUnitManager/getMeasureUnits.json?rate=1"/>

	<%-- 工具栏 --%>
    <youi:toolbar refWidgetId="grid_measureUnit">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="save"/>
    </youi:toolbar>
    <%-- 列表数据 --%>
	<youi:grid id="grid_measureUnit" src="/metadataServices/services/measureUnitManager/getPagerMeasureUnits.json"
		removeSrc="/metadataServices/services/measureUnitManager/removeMeasureUnit.json" idKeys="id"
			   reset="NOT" query="NOT" showConvertDropdown="false" fixedFooter="true">
		<youi:fieldLayout prefix="query">
			<youi:fieldSelect convert="measurement" property="measurement"  caption="度量衡"/>
			<youi:fieldSelect convert="baseUnitConvert" property="baseUnitId"  caption="基础单位"/>
			<youi:fieldText column="2" property="unitCaption"  caption="单位名称" operator="LIKE"/>
		</youi:fieldLayout>

		<youi:gridCol width="15%" orderBy="desc" convert="measurement" property="measurement"  caption="度量衡"/>
		<youi:gridCol width="25%" property="unitCaption"  caption="单位名称"/>
		<youi:gridCol width="15%" property="rate"  caption="换算率"/>

		<youi:gridCol width="15%" property="baseUnitId" convert="baseUnitConvert"  caption="基础单位"/>
		<youi:gridCol width="35%" property="description"  caption="单位描述"/>
		<youi:gridCol width="10%" type="button" property="button" caption="操作">
			<youi:button name="edit" caption="编辑"/>
			<youi:button name="split" caption=""/>
			<youi:button name="removeRecord" icon="remove" caption="删除"/>
		</youi:gridCol>
	</youi:grid>
	
	<!-- Grid编辑按钮动作：打开修改计量单位弹出页 -->
	<youi:func name="grid_measureUnit_edit" params="dom,options,record">
		$elem('subpage_measureUnit_edit',pageId).subpage('open',{},'',record,pageId);
	</youi:func>
	<!-- Grid新增按钮动作：打开新增计量单位弹出页 -->
	<youi:func name="grid_measureUnit_add" params="dom,options">
		$elem('subpage_measureUnit_add',pageId).subpage('open',{},'',{},pageId);
	</youi:func>
	<!-- 编辑计量单位的subpage内容变化回调函数 -->
	<youi:func name="subpage_measureUnit_edit_change" params="record">
		$elem('grid_measureUnit',pageId).grid('refresh');
	</youi:func>
	<!-- 新增计量单位的subpage内容变化回调函数 -->
	<youi:func name="subpage_measureUnit_add_change" params="record">
		$elem('grid_measureUnit',pageId).grid('refresh');
	</youi:func>
	
</youi:page>