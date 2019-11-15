<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="制度设计">
    <youi:subpage src="page/${_pagePath}.dialog/editMetaProject.html" height="240"
                  subpageId="addMetaProject" caption="新增项目" type="dialog"/>

    <youi:subpage src="page/${_pagePath}.dialog/editMetaProject.html?projectId={projectId}" height="240"
                  subpageId="editMetaProject" caption="修改项目" type="dialog"/>

    <youi:subpage src="page/${_pagePath}/designer.html?projectId={projectId}&title={projectCaption}" height="240"
                  subpageId="designer" caption="元数据设计器" type="page"/>
    <%-- 指标管理 --%>
    <youi:subpage src="page/${_pagePath}.indicator/index.html?projectId={projectId}&title={projectCaption}"
                  subpageId="indicator" caption="指标管理" type="secondPage"/>
    <%-- --%>
    <youi:grid id="grid_meta_project" src="metadata.metaProjectManager.getPagerMetaProject"
               idKeys="projectId" removeSrc="metadata.metaProjectManager.removeMetaProject"
               query="NOT" reset="NOT" showConvertDropdown="false">

        <youi:toolbar refWidgetId="grid_meta_project">
            <youi:toolbarItem name="refresh" caption="查询" icon="search" tooltips=""/>
            <youi:toolbarItem name="addMetaProject" caption="添加项目" icon="plus" tooltips=""/>
        </youi:toolbar>

        <youi:fieldLayout prefix="query" labelWidths="120" columns="1">
            <youi:fieldText operator="LIKE" property="projectCaption"  caption="项目名称"/>
        </youi:fieldLayout>

        <youi:gridCol type="link" width="60%"  property="projectCaption"  caption="项目名称" orderBy="asc" nowrap="false"/>
        <youi:gridCol width="15%" property="projectCode"  caption="项目代码"/>
        <youi:gridCol width="15%" property="gathering"  caption="是否在线填报"/>

        <youi:gridCol width="10%" type="button" property="button" caption="操作">
            <youi:button name="editMetaProject" caption="编辑" icon="rename"/>
            <youi:button name="designer" caption="设计" icon="design"/>
            <youi:button name="indicator" caption="指标管理" icon="index-management1"/>
            <youi:button name="removeRecord" caption="删除" icon="delete"/>
        </youi:gridCol>
    </youi:grid>

    <%-- grid回调函数 - 打开新增项目窗口页面 --%>
    <youi:func name="grid_meta_project_addMetaProject">
        $elem('subpage_addMetaProject',pageId).subpage('open');
    </youi:func>

    <%-- grid回调函数 - 打开编辑项目窗口页面 --%>
    <youi:func name="grid_meta_project_editMetaProject" params="dom,commandOptions,record">
        $elem('subpage_editMetaProject',pageId).subpage('open',{},{},record);
    </youi:func>
    <%-- grid回调函数 - 打开项目设计窗口页面 --%>
    <youi:func name="grid_meta_project_designer" params="dom,commandOptions,record">
        $elem('subpage_designer',pageId).subpage('open',{},{},record);
    </youi:func>
    <%-- 通过单元格链接打开项目设计窗口页面 --%>
    <youi:func name="grid_meta_project_cellLink" params="dom,commandOptions,record">
        $elem('subpage_designer',pageId).subpage('open',{},{},record);
    </youi:func>
    <%-- 打开项目指标界面 --%>
    <youi:func name="grid_meta_project_indicator" params="dom,commandOptions,record">
        $elem('subpage_indicator',pageId).subpage('open',{},{},record);
    </youi:func>

    <!-- 新增项目的subpage内容变化回调函数 -->
    <youi:func name="subpage_addMetaProject_change" params="record">
        $elem('grid_meta_project',pageId).grid('refresh');
    </youi:func>

    <!-- 编辑项目的subpage内容变化回调函数 -->
    <youi:func name="subpage_editMetaProject_change" params="record">
        $elem('grid_meta_project',pageId).grid('refresh');
    </youi:func>
</youi:page>