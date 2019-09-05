<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="元数据设计器-${param.title}">

    <youi:xmenu id="xmenu_meta">
        <youi:xmenuItem name="addMetaTask" caption="新增方案" groups="metaPlan"/>
        <youi:xmenuItem name="addMetaReport" caption="新增报表" groups="metaTask,folder"/>

        <youi:xmenuItem name="addMetaIndicator" caption="新增指标" groups="metaReport"/>
    </youi:xmenu>

    <%-- 左边布局 --%>
    <youi:customWidget name="page_spliter" widgetName="pageSpliter"
                       styleClass="col-sm-3 page-inner-height">
        <youi:toolbar id="tree_bar" styleClass="fixed-height" refWidgetId="tree_metaobject">
            <youi:toolbarItem name="addMetaPlan" caption="新增制度" tooltips="" icon="plus"/>
        </youi:toolbar>

        <youi:tree id="tree_metaobject" iteratorSrc="/metadataServices/services/metaObjectNodeManager/getTopProjectMetaObjectTreeNodes.json?projectId=proj001"
                   rootText="" hideRoot="true" xmenu="xmenu_meta" styleClass="no-padding col-sm-12 auto-height hide-root">
        </youi:tree>
    </youi:customWidget>

    <%-- 右边布局 --%>
    <youi:customWidget widgetName="treePage" name="treePage" refs="tree_metaobject"
                       styleClass="col-sm-9 page-inner-height page-spliter-right no-padding hide-content">
        <youi:toolbar id="tree_bar" styleClass="fixed-height" refWidgetId="tree_metaobject">
            <li class="pull-left" style="height: 30px"></li>
        </youi:toolbar>

    </youi:customWidget>

</youi:page>