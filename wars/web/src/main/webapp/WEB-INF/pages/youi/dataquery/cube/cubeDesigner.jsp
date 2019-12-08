<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:style href="/asserts/css/page/page.cubeDesigner.css"/>
<youi:page caption="CUBE设计-${param.title}">

    <youi:subpage src="page/${_pagePath}/crossTableViewer.html?pageId={subPageId}" subpageId="crossTableViewer"
                  caption="CUBE查询结果" type="secondPage"/>

    <youi:customWidget name="page_spliter" widgetName="pageSpliter"
                       styleClass="col-sm-2 page-inner-height">
        <youi:toolbar styleClass="fixed-height" refWidgetId="cubeDesigner">
            <youi:toolbarItem name="goBackPage" caption="返回" icon="save" tooltips=""/>
            <youi:dropdown caption="模版" styleClass="cube-list">

            </youi:dropdown>
        </youi:toolbar>
        <youi:tabs id="tabs">
            <youi:tabItem id="fields" caption="字段">
                <youi:gridList load="true" template="{text}" idKeys="columnName"  draggable="true" dropStyle="dimension-container"
                               rowStyle="option-item"
                               src="/metadataServices/services/dataQueryManager/getQueryColumns.json?id=${param.id}">

                </youi:gridList>
            </youi:tabItem>
            <youi:tabItem id="params" caption="参数"/>
        </youi:tabs>
    </youi:customWidget>

    <%-- asserts/js/page/youi.cubedesigner.js --%>
    <youi:customWidget widgetName="cubeDesigner" name="cubeDesigner" pageModule="dataquery"
                       refs="subpage_crossTableViewer"
                       styleClass="no-padding page-inner-height col-sm-10 page-spliter-right">
        <youi:toolbar styleClass="fixed-height" refWidgetId="cubeDesigner">
            <youi:toolbarItem name="query" caption="查询" icon="search" tooltips=""/>
            <youi:toolbarItem name="save" caption="保存" icon="save" tooltips=""  groups="cube-editor"/>
            <youi:toolbarItem name="lockCube" caption="锁定维度" icon="lock" tooltips=""  groups="cube-editor"/>
            <youi:toolbarItem name="addRowDimensionContainer" caption="增加行标签" icon="plus" tooltips="" />
        </youi:toolbar>
    </youi:customWidget>

    <%-- 初始化数据 --%>
    <youi:func name="init" params="result">

    </youi:func>

</youi:page>