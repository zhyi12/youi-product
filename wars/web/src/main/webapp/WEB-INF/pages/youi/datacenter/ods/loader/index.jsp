<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="贴源数据加载">

    <youi:subpage src="page/${_pagePath}/config.html?dataResourceId={id}&title={caption}"
                  subpageId="config" caption="配置" type="secondPage"/>

    <youi:toolbar styleClass="fixed-height" refWidgetId="grid_dataResource">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
    </youi:toolbar>
    <youi:grid id="grid_dataResource" src="/metadataServices/services/dataResourceManager/getPagerDataResources.json"
               removeSrc="/metadataServices/services/dataResourceManager/removeDataResource.json" idKeys="id"
               showNum="true" query="NOT" reset="NOT" fixedFooter="true">
        <youi:fieldLayout prefix="query">
            <youi:fieldText property="caption"  caption="资源名称"/>
            <youi:fieldText property="catalog"  caption="catalog"/>
            <youi:fieldText property="schema"  caption="schema"/>
            <youi:fieldText property="status"  caption="资源状态"/>
        </youi:fieldLayout>

        <youi:gridCol width="45%" property="caption"  caption="资源名称"/>
        <youi:gridCol width="15%" property="catalog"  caption="catalog"/>
        <youi:gridCol width="15%" property="schema"  caption="schema"/>
        <youi:gridCol width="15%" property="status"  caption="资源状态"/>

        <youi:gridCol width="10%" type="button" property="button" caption="操作">
            <youi:button name="config" caption="配置" icon="gear"/>
        </youi:gridCol>
    </youi:grid>

    <youi:func name="grid_dataResource_config" params="dom,commandOptions,record">
        $elem('subpage_config',pageId).subpage('open',{},{},record);
    </youi:func>

</youi:page>