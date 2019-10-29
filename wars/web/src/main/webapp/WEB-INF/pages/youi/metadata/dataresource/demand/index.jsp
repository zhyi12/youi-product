<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:script src="/asserts/js/lib/jsplumb.min.js"/>
<youi:script src="/asserts/js/youi/youi.plumbflow.js"/>
<youi:style href="/asserts/css/youi/youi.flow.css"/>
<youi:page caption="数据需求">
    <%--  --%>
    <youi:subpage height="80" src="page/${_pagePath}.dialog/editDataDemandRealm.html?dialogId={subPageId}" subpageId="addDataDemandRealm" caption="新增需求域" type="dialog"/>
    <youi:subpage height="80" src="page/${_pagePath}.dialog/editDataDemand.html?dialogId={subPageId}" subpageId="addDataDemand" caption="新增需求" type="dialog"/>
    <%-- 保存数据需求的思维导图 --%>
    <youi:ajaxUrl name="getDataDemandRealmsUrl" src="/metadataServices/services/dataDemandRealmManager/getDataDemandRealmTree.json"/>
    <youi:ajaxUrl name="getRealmDataDemandsUrl" src="/metadataServices/services/dataDemandManager/getRealmDataDemandTree.json?realmId={realmId}"/>
    <youi:ajaxUrl name="saveDataDemandMindMapUrl" src="/saveDataDemandMindMapUrl.json"/>

    <youi:xmenu id="xmenu_dataDemandRealm">
        <youi:xmenuItem name="openAddDataDemandRealm" caption="新增需求域"/>
        <youi:xmenuItem name="openAddDataDemand" caption="新增需求" groups="demand-realm"/>
    </youi:xmenu>

    <youi:customWidget name="page_spliter" widgetName="pageSpliter"
                       styleClass="col-sm-2 page-inner-height">
        <youi:toolbar id="tree_bar" styleClass="fixed-height" refWidgetId="dataDemandDesigner">
            <youi:toolbarItem name="openAddDataDemandRealm" caption="新增需求域" tooltips="" icon="plus"/>
        </youi:toolbar>
        <%--  数据需求域树 --%>
        <youi:tree id="tree_dataDemandRealm"
                   idAttr="id" textAttr="demandCaption"
                   rootText="" hideRoot="true" xmenu="xmenu_dataDemandRealm" styleClass="no-padding col-sm-12 auto-height hide-root">
        </youi:tree>
    </youi:customWidget>
    <%--  --%>
    <youi:customWidget widgetName="dataDemandDesigner" name="dataDemandDesigner" pageModule="metadata"
                       urls="getDataDemandRealmsUrl,getRealmDataDemandsUrl"
                       refs="tree_dataDemandRealm,subpage_addDataDemandRealm,subpage_addDataDemand"
                       styleClass="page-inner-height youi-flow col-sm-10 page-spliter-right no-padding">
        <youi:toolbar refWidgetId="dataDemandDesigner" styleClass="no-border fixed-height">
            <youi:toolbarItem name="save" caption="保存" icon="save" tooltips="" groups="data-demand"/>
            <youi:toolbarItem name="zoomOut" value="1" caption="缩小" icon="zoom-out" tooltips="" groups="data-demand"/>
            <youi:toolbarItem name="zoomIn" value="1" caption="放大" icon="zoom-in" tooltips="" groups="data-demand"/>
            <youi:toolbarItem name="createNode" value="folder" caption="新主题组" icon="plus" tooltips="" groups="data-demand"/>
            <youi:toolbarItem name="createNode" value="domain" caption="新主题" icon="plus" tooltips="" groups="data-demand"/>
        </youi:toolbar>
    </youi:customWidget>

</youi:page>