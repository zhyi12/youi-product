<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:script src="/asserts/ueditor/ueditor.config.js"/>
<youi:script src="/asserts/ueditor/ueditor.all.min.js"/>

<youi:page caption="数据查询服务">
    <youi:subpage height="500" width="1100" src="page/${_pagePath}/querySqlEditor.html?id={id}" subpageId="dataQuery_edit" caption="编辑SQL查询" type="dialog"/>
    <youi:subpage height="150" src="page/${_pagePath}/dataQueryEdit.html" subpageId="dataQuery_add" caption="新增数据查询" type="dialog"/>

    <youi:subpage src="page/${_pagePath}/pagerRecords.html?id={id}" subpageId="pagerRecords" caption="数据查询" type="secondPage"/>

    <youi:toolbar refWidgetId="grid_dataQuery">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="plus"/>
    </youi:toolbar>

    <youi:grid id="grid_dataQuery" src="/metadataServices/services/dataQueryManager/getPagerDataQuerys.json"
               removeSrc="/metadataServices/services/dataQueryManager/removeDataQuery.json" idKeys="id"
               showNum="true" fixedFooter="true" reset="NOT" query="NOT">
        <youi:fieldLayout prefix="query">
            <youi:fieldText property="name"  caption="查询名称"/>
            <youi:fieldText property="caption"  caption="中文描述"/>
        </youi:fieldLayout>

        <youi:gridCol width="15%" property="name"  caption="查询名称"/>
        <youi:gridCol width="25%" property="caption"  caption="中文描述"/>

        <youi:gridCol width="55%" property="sqlExpression.source"  caption="SQL表达式"/>
        <youi:gridCol width="10%" type="button" property="button" caption="操作">
            <youi:button name="edit" caption="编辑"/>
            <youi:button name="pagerRecords" caption="查询" icon="search"/>
            <youi:button name="removeRecord" icon="remove" caption="删除"/>
        </youi:gridCol>
    </youi:grid>

    <!-- Grid编辑按钮动作：打开修改数据查询弹出页 -->
    <youi:func name="grid_dataQuery_edit" params="dom,options,record">
        $elem('subpage_dataQuery_edit',pageId).subpage('open',{title:record.caption+'('+record.name+')SQL编辑'},{},record,pageId);
    </youi:func>
    <!-- Grid新增按钮动作：打开新增数据查询弹出页 -->
    <youi:func name="grid_dataQuery_add" params="dom,options">
        $elem('subpage_dataQuery_add',pageId).subpage('open',{},'',{},pageId);
    </youi:func>

    <youi:func name="grid_dataQuery_pagerRecords" params="dom,options,record">
        $elem('subpage_pagerRecords',pageId).subpage('open',{},{},record,pageId);
    </youi:func>
    <!-- 编辑数据查询的subpage内容变化回调函数 -->
    <youi:func name="subpage_dataQuery_edit_change" params="record">
        $elem('grid_dataQuery',pageId).grid('refresh');
    </youi:func>
    <!-- 新增数据查询的subpage内容变化回调函数 -->
    <youi:func name="subpage_dataQuery_add_change" params="record">
        $elem('grid_dataQuery',pageId).grid('refresh');
    </youi:func>
</youi:page>