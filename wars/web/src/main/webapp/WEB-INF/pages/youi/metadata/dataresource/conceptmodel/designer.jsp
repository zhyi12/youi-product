<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:script src="/asserts/js/youi/extra/youi.flow.js"/>

<youi:page caption="概念模型设计-${param.dataResourceId}" dataSrc="">

    <youi:customWidget name="page_spliter" widgetName="pageSpliter"
                       styleClass="col-sm-3 page-inner-height">
        <youi:toolbar id="tree_bar" styleClass="fixed-height" refWidgetId="metaCdmDesigner">
            <li>
                <a class="page-link youi-icon icon-reply" href="#p:page/youi.metadata.dataresource/index.html"> 返回</a>
            </li>
        </youi:toolbar>
        <%-- 概念模型树 --%>
        <youi:tree id="tree_metaobject"
                   rootText="" hideRoot="true" xmenu="xmenu_meta" styleClass="no-padding col-sm-12 auto-height hide-root">
        </youi:tree>
    </youi:customWidget>

    <youi:customWidget widgetName="metaCdmDesigner" pageModule="metadata" name="metaCdmDesigner"
                       styleClass="col-sm-9 page-inner-height page-spliter-right no-padding hide-content">

        <youi:toolbar styleClass="fixed-height" refWidgetId="metaCdmDesigner">
            <youi:toolbarItem name="save" caption="保存" tooltips="" icon="save"/>
        </youi:toolbar>

    </youi:customWidget>

    <youi:func name="init">

    </youi:func>
</youi:page>