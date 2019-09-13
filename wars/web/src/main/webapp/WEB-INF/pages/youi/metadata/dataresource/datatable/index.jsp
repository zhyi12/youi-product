<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据表">

    <youi:subpage height="150" src="page/${_pagePath}/dataTableEdit.html?id={id}" subpageId="dataTable_edit" caption="修改数据表" type="dialog"/>
    <youi:subpage height="150" src="page/${_pagePath}/dataTableEdit.html" subpageId="dataTable_add" caption="添加数据表" type="dialog"/>

    <youi:toolbar refWidgetId="grid_dataTable">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="plus"/>
    </youi:toolbar>
    <youi:grid id="grid_dataTable" src="/metadataServices/services/dataTableManager/getPagerDataTables.json"
               removeSrc="/metadataServices/services/dataTableManager/removeDataTable.json" idKeys="id"
               showNum="true" showCheckbox="true" query="NOT" reset="NOT" fixedFooter="true">
        <youi:fieldLayout prefix="query">
            <youi:fieldText property="tableName"  caption="表名"/>
            <youi:fieldText property="tableCaption"  caption="中文表名"/>
            <youi:fieldText property="dataResourceId" column="2" caption="数据资源"/>
        </youi:fieldLayout>
        <youi:gridCol width="25%" property="dataResourceId"  caption="数据资源"/>
        <youi:gridCol width="15%" property="tableName"  caption="表名"/>
        <youi:gridCol width="55%" property="tableCaption"  caption="中文表名"/>

        <youi:gridCol width="10%" type="button" property="button" caption="操作">
            <youi:button name="edit" caption="编辑"/>
            <youi:button name="split" caption=""/>
            <youi:button name="removeRecord" icon="remove" caption="删除"/>
        </youi:gridCol>
    </youi:grid>

    <!-- Grid编辑按钮动作：打开修改数据表弹出页 -->
    <youi:func name="grid_dataTable_edit" params="dom,options,record">
        $elem('subpage_dataTable_edit',pageId).subpage('open',{},'',record,pageId);
    </youi:func>
    <!-- Grid新增按钮动作：打开新增数据表弹出页 -->
    <youi:func name="grid_dataTable_add" params="dom,options">
        $elem('subpage_dataTable_add',pageId).subpage('open',{},'',{},pageId);
    </youi:func>
    <!-- 编辑数据表的subpage内容变化回调函数 -->
    <youi:func name="subpage_dataTable_edit_change" params="record">
        $elem('grid_dataTable',pageId).grid('refresh');
    </youi:func>
    <!-- 新增数据表的subpage内容变化回调函数 -->
    <youi:func name="subpage_dataTable_add_change" params="record">
        $elem('grid_dataTable',pageId).grid('refresh');
    </youi:func>

</youi:page>