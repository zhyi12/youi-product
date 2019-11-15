<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="${param.title} - 数据加载配置">

    <youi:customWidget name="page_spliter" widgetName="pageSpliter"
                       styleClass="col-sm-3 page-inner-height">
        <youi:toolbar id="tree_bar" styleClass="fixed-height" refWidgetId="metaCdmDesigner">
            <youi:toolbarItem name="refresh" caption="刷新" tooltips="" icon="plus"/>
            <youi:toolbarItem name="dbconfig" caption="公用配置" tooltips="" icon="gear"/>
        </youi:toolbar>
        <%-- 概念模型树 --%>
        <youi:tree id="tree_dataResource" iteratorSrc="/metadataServices/services/dataTableManager/getDataTables.json?dataResourceId=${param.dataResourceId}" idAttr="tableName" textAttr="tableCaption"
                   rootText="数据表" hideRoot="false" xmenu="xmenu_dataResource" styleClass="no-padding col-sm-12 auto-height hide-root">
        </youi:tree>
    </youi:customWidget>

    <%-- --%>
    <youi:customWidget widgetName="odsLoaderConfig" pageModule="datacenter" name="odsLoaderConfig"
                       refs="tree_dataResource"
                       urls=""
                       styleClass="col-sm-9 page-inner-height page-spliter-right no-padding hide-content">
        <youi:toolbar styleClass="fixed-height" refWidgetId="odsLoaderConfig">
            <youi:toolbarItem name="refresh" caption="保存" tooltips="" icon="save" groups="data-table"/>
        </youi:toolbar>
        <div> 执行历史 </div>
        <div> 执行情况 </div>
    </youi:customWidget>

</youi:page>