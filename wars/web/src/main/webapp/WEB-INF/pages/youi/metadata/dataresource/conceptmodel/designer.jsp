<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:script src="/asserts/js/youi/extra/youi.flow.js"/>
<youi:script src="/asserts/js/youi/field/field.matching.js"/>
<youi:page caption="概念模型设计${param.dataResourceId}" dataSrc="/metadataServices/services/dataTableSqlService/buildCreateSql.json?dataResourceId=mongodb_stats2-filing&tableName=stats_gather_form_quest">

    <youi:ajaxUrl name="getModelTreeUrl" src="/metadataServices/services/conceptItemManager/getConceptItemTree.json?dataResourceId=${param.dataResourceId}"/>
    <youi:ajaxUrl name="getConceptDiagramTreeUrl" src="/metadataServices/services/conceptDiagramManager/getConceptDiagramTree.json"/>
    <youi:ajaxUrl name="getDataTablesUrl" src="/metadataServices/services/dataTableManager/getDataTables.json?dataResourceId=${param.dataResourceId}"/>
    <youi:ajaxUrl name="getDataTableColumnsUrl" src="/metadataServices/services/dataTableColumnManager/getDataTableColumns.json?tableName={tableName}&dataResourceId=${param.dataResourceId}"/>
    <youi:ajaxUrl name="saveDiagramUrl" src="/metadataServices/services/conceptDiagramManager/saveModuleDiagramContent.json?dataResourceId=${param.dataResourceId}"/>
    <youi:ajaxUrl name="getDiagramContentUrl" src="/metadataServices/services/conceptDiagramManager/getModuleDiagramContent.json"/>
    <youi:ajaxUrl name="removeConceptItemUrl" src="/metadataServices/services/conceptItemManager/removeConceptItem.json"/>

    <youi:subpage height="130" src="page/${_pagePath}.dialog/editSubSystem.html?dialogId={subPageId}&dataResourceId=${param.dataResourceId}"
                  subpageId="addSubSystem" caption="新增子系统" type="dialog"/>
    <youi:subpage height="130" src="page/${_pagePath}.dialog/editModule.html?dialogId={subPageId}&dataResourceId=${param.dataResourceId}"
                  subpageId="addModule" caption="新增模块" type="dialog"/>

    <youi:subpage height="450" src="page/${_pagePath}.dialog/editEntity.html?dialogId={subPageId}&entityId={id}&dataResourceId=${param.dataResourceId}"
                  subpageId="editEntity" caption="编辑实体" type="dialog"/>
    <youi:xmenu id="xmenu_dataResource">
        <youi:xmenuItem name="removeConceptItem" value="subSystem" caption="删除子系统" groups="sub-system"/>
        <youi:xmenuItem name="removeConceptItem" value="module" caption="删除模块" groups="module"/>
        <youi:xmenuItem name="openAddModule" caption="新增模块" groups="sub-system"/>
    </youi:xmenu>

    <youi:customWidget name="page_spliter" widgetName="pageSpliter"
                       styleClass="col-sm-3 page-inner-height">
        <youi:toolbar id="tree_bar" styleClass="fixed-height" refWidgetId="metaCdmDesigner">
            <li>
                <a class="page-link youi-icon icon-reply" href="#p:page/youi.metadata.dataresource/index.html"> 返回</a>
            </li>
            <youi:toolbarItem name="openAddSubSystem" caption="新增子系统" tooltips="" icon="plus"/>
        </youi:toolbar>
        <%-- 概念模型树 --%>
        <youi:tree id="tree_dataResource"
                   rootText="" hideRoot="true" xmenu="xmenu_dataResource" styleClass="no-padding col-sm-12 auto-height hide-root">
            <youi:treeNode id="tree_metadata" caption="概念模型"/>
        </youi:tree>
    </youi:customWidget>

    <youi:customWidget widgetName="metaCdmDesigner" pageModule="metadata" name="metaCdmDesigner"
                       refs="tree_dataResource,subpage_addSubSystem,subpage_addModule,subpage_editEntity"
                       urls="removeConceptItemUrl,getModelTreeUrl,getConceptDiagramTreeUrl,getDataTablesUrl,getDataTableColumnsUrl,getDiagramContentUrl,saveDiagramUrl"
                       styleClass="col-sm-9 page-inner-height page-spliter-right no-padding hide-content">

        <youi:toolbar styleClass="fixed-height" refWidgetId="metaCdmDesigner">
            <li class="pull-right">
                <span class="youi-icon icon-module module-caption"> </span>
            </li>
            <youi:toolbarItem name="save" caption="保存" tooltips="保存模块模型图" icon="save" groups="module"/>

            <youi:toolbarItem name="undo" caption="撤销" tooltips="撤销" icon="undo" groups="undo"/>
            <youi:toolbarItem name="redo" caption="重做" tooltips="重做" icon="redo" groups="redo"/>

            <youi:dropdown styleClass="entity-repository" caption="选择实体表">

            </youi:dropdown>

            <youi:toolbarItem name="newEntity" caption="新实体" tooltips="新实体" icon="table" groups="module"/>

        </youi:toolbar>

    </youi:customWidget>

    <youi:func name="init" params="result">

    </youi:func>

</youi:page>