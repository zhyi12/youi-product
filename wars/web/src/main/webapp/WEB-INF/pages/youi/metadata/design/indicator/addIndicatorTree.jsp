<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="批量添加指标">

    <youi:ajaxUrl name="loadTreeNodesUrl" src="/mobileServices/services/mindicatorManager/parseIndicatorTreeByFile.json"/>

    <%-- --%>
    <youi:form id="form_meta_indicator" reset="NOT" submit="NOT" close="NOT" panel="false"
               action="/metadataServices/services/metaProjectIndicatorManager/addTreeIndicators.json">
        <youi:toolbar styleClass="fixed-height" refWidgetId="indicator">
            <youi:toolbarItem name="undo" caption="撤销" tooltips="" icon="undo" groups="undo"/>
            <youi:toolbarItem name="redo" caption="重做" tooltips="" icon="redo" groups="redo"/>

            <youi:toolbarItem name="addTreeNode" caption="新指标" tooltips="" icon="plus"/>
            <youi:toolbarItem name="nodeCommand" value="removeNode" caption="删除指标" tooltips="" icon="remove" groups="indicator"/>
            <youi:toolbarItem name="nodeCommand" value="moveUp" caption="上移" tooltips="" icon="arrow-up" groups="moveUp"/>
            <youi:toolbarItem name="nodeCommand" value="moveDown" caption="下移" tooltips="" icon="arrow-down" groups="moveDown"/>
            <youi:toolbarItem name="nodeCommand" value="moveLeft" caption="左移" tooltips="" icon="arrow-left" groups="moveLeft"/>
            <youi:toolbarItem name="nodeCommand" value="moveRight" caption="右移" tooltips="" icon="arrow-right" groups="moveRight"/>
            <youi:toolbarItem name="unitConfig" caption="计量单位" tooltips="" icon="gear" groups="indicator"/>
        </youi:toolbar>

        <youi:fieldLayout columns="1" prefix="addMetaProject" labelWidths="120">
            <youi:fieldHidden property="parentId" caption="parentId" defaultValue="${param.parentId}"/>
            <youi:fieldHidden property="projectId" caption="项目ID" defaultValue="${param.projectId}"/>

        </youi:fieldLayout>

        <youi:customWidget data-idAttr="id" data-textAttr="text" data-pidAttr="parentId"
                widgetName="treeBuilder" name="indicator" urls="loadTreeNodesUrl" styleClass="auto-height">

        </youi:customWidget>
    </youi:form>

    <youi:button name="submit" caption="确定" submitProperty="submit" submitValue="1"/>

    <youi:func name="form_meta_indicator_afterSubmit" params="result">
        $.youi.pageUtils.closeAndRefreshSubpage(pageId,result.record);//关闭并刷新
    </youi:func>
</youi:page>