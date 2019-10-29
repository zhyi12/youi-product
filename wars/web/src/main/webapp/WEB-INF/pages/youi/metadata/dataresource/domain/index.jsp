<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:script src="/asserts/js/lib/jsplumb.min.js"/>
<youi:script src="/asserts/js/youi/youi.plumbflow.js"/>
<youi:style href="/asserts/css/youi/youi.flow.css"/>
<youi:page caption="主题库">
    <youi:customWidget name="page_spliter" widgetName="pageSpliter"
                       styleClass="col-sm-3 page-inner-height">
        <youi:toolbar id="tree_bar" styleClass="fixed-height" refWidgetId="metaCdmDesigner">
            <youi:toolbarItem name="openAddMetaDomainReaml" caption="新建主题库类型" tooltips="" icon="plus"/>
        </youi:toolbar>
        <%-- 概念模型树 --%>
        <youi:tree id="tree_dataResource"
                   rootText="" hideRoot="true" xmenu="xmenu_dataResource" styleClass="no-padding col-sm-12 auto-height hide-root">

        </youi:tree>
    </youi:customWidget>
    <%--  --%>
    <youi:customWidget widgetName="plumbFlow" name="plumbFlow" styleClass="page-inner-height youi-flow col-sm-9 page-spliter-right no-padding">
        <youi:toolbar refWidgetId="plumbFlow" styleClass="no-border fixed-height">
            <youi:toolbarItem name="save" caption="保存" icon="save" tooltips=""/>
            <youi:toolbarItem name="zoomOut" value="1" caption="缩小" icon="zoom-out" tooltips=""/>
            <youi:toolbarItem name="zoomIn" value="1" caption="放大" icon="zoom-in" tooltips=""/>
            <youi:toolbarItem name="createNode" value="folder" caption="新主题组" icon="plus" tooltips=""/>
            <youi:toolbarItem name="createNode" value="domain" caption="新主题" icon="plus" tooltips=""/>
        </youi:toolbar>
    </youi:customWidget>

</youi:page>