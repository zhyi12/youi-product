<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="元数据设计器-${param.title}">

    <youi:subpage src="page/${_pagePath}.dialog/editMetaPlan.html" subpageId="addMetaPlan" caption="增加制度" type="dialog"/>
    <youi:subpage src="page/${_pagePath}.dialog/editMetaTask.html" subpageId="addMetaTask" caption="增加方案" type="dialog"/>

    <youi:xmenu id="xmenu_meta">
        <youi:xmenuItem name="openAddMetaTask" caption="新增方案" groups="metaPlan"/>
        <youi:xmenuItem name="openAddMetaReport" caption="新增报表" groups="metaTask,folder"/>
        <youi:xmenuItem name="openAddMetaIndicator" caption="新增指标" groups="metaReport"/>
    </youi:xmenu>

    <%-- 左边布局 --%>
    <youi:customWidget name="page_spliter" widgetName="pageSpliter"
                       styleClass="col-sm-3 page-inner-height">
        <youi:toolbar id="tree_bar" styleClass="fixed-height" refWidgetId="metaProjectDesigner">
            <youi:toolbarItem name="openAddMetaPlan" caption="新增制度" tooltips="" icon="plus"/>
        </youi:toolbar>

        <youi:tree id="tree_metaobject" iteratorSrc="/metadataServices/services/metaObjectNodeManager/getTopProjectMetaObjectTreeNodes.json?projectId=proj001"
                   rootText="" hideRoot="true" xmenu="xmenu_meta" styleClass="no-padding col-sm-12 auto-height hide-root">
        </youi:tree>
    </youi:customWidget>

    <%-- 右边布局 --%>
    <youi:customWidget widgetName="treePage" name="treePage"
                       refs="tree_metaobject"
                       styleClass="col-sm-9 page-inner-height page-spliter-right no-padding hide-content">
        <youi:toolbar id="tree_page_bar" styleClass="fixed-height" refWidgetId="tree_metaobject">
            <li class="pull-left" style="height: 30px"></li>
        </youi:toolbar>

        <youi:customWidget data-useModelTree="true"  data-projectId="${param.projectId}"
                           refs="tree_metaobject,subpage_addMetaPlan,subpage_addMetaTask"
                widgetName="metaProjectDesigner" name="metaProjectDesigner" pageModule="metadata">
        </youi:customWidget>

    </youi:customWidget>

</youi:page>