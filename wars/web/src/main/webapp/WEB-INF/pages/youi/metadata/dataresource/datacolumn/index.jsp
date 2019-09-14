<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据列">

    <youi:subpage height="150" src="page/${_pagePath}/dataTableColumnEdit.html?id={id}" subpageId="dataTableColumn_edit" caption="修改数据列" type="dialog"/>
    <youi:subpage height="150" src="page/${_pagePath}/dataTableColumnEdit.html" subpageId="dataTableColumn_add" caption="添加数据列" type="dialog"/>

    <youi:toolbar refWidgetId="grid_dataTableColumn">
        <youi:toolbarItem name="refresh" caption="查询" icon="search" tooltips=""/>
    </youi:toolbar>

    <youi:grid id="grid_dataTableColumn" reset="NOT" query="NOT" fixedFooter="true"
               removeSrc="/metadataServices/services/dataTableColumnManager/removeDataTableColumn.json" idKeys="id"
               src="/metadataServices/services/dataTableColumnManager/getPagerDataTableColumns.json">
        <youi:fieldLayout>
            <youi:fieldText property="dataResouceId" caption="数据资源" defaultValue="${param.dataResourceId}"/>
            <youi:fieldText property="tableName" caption="表名" defaultValue="${param.tableName}"/>
        </youi:fieldLayout>

        <youi:gridCol width="35%" property="caption" caption="中文名"/>
        <youi:gridCol width="15%" property="dataResourceId" caption="数据资源"/>
        <youi:gridCol width="15%" property="tableName" caption="表名"/>
        <youi:gridCol width="15%" property="columnName" caption="列名"/>

        <youi:gridCol width="10%" type="button" property="button" caption="操作">
            <youi:button name="edit" caption="编辑"/>
            <youi:button name="removeRecord" icon="remove" caption="删除"/>
        </youi:gridCol>

    </youi:grid>

    <!-- Grid编辑按钮动作：打开修改数据列弹出页 -->
    <youi:func name="grid_dataTableColumn_edit" params="dom,options,record">
        $elem('subpage_dataTableColumn_edit',pageId).subpage('open',{},'',record,pageId);
    </youi:func>
    <!-- Grid新增按钮动作：打开新增数据列弹出页 -->
    <youi:func name="grid_dataTableColumn_add" params="dom,options">
        $elem('subpage_dataTableColumn_add',pageId).subpage('open',{},'',{},pageId);
    </youi:func>
    <!-- 编辑数据列的subpage内容变化回调函数 -->
    <youi:func name="subpage_dataTableColumn_edit_change" params="record">
        $elem('grid_dataTableColumn',pageId).grid('refresh');
    </youi:func>
    <!-- 新增数据列的subpage内容变化回调函数 -->
    <youi:func name="subpage_dataTableColumn_add_change" params="record">
        $elem('grid_dataTableColumn',pageId).grid('refresh');
    </youi:func>

</youi:page>