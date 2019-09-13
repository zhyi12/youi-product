<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据资源">

    <youi:subpage height="150" src="page/${_pagePath}/dataResourceEdit.html?id={id}" subpageId="dataResource_edit" caption="修改数据资源" type="dialog"/>
    <youi:subpage height="150" src="page/${_pagePath}/dataResourceEdit.html" subpageId="dataResource_add" caption="添加数据资源" type="dialog"/>

    <youi:subpage src="page/${_pagePath}.datatable/schemaTables.html?catalog={catalog}&schema={schema}"
                  subpageId="schemaTables" caption="数据表同步" type="dialog"/>

    <youi:customWidget name="page_spliter" widgetName="pageSpliter"
                       styleClass="col-sm-3 page-inner-height">
        <youi:toolbar styleClass="fixed-height">
            <youi:toolbarItem name="refresh" caption="刷新" tooltips=""/>
        </youi:toolbar>
        <youi:tree styleClass="col-sm-12 no-padding auto-height" iteratorParam="id" rootText="数据资源"
                iteratorSrc="/metadataServices/services/dataDictionaryFinder/getDataSourceIteratorTree.json">

        </youi:tree>
    </youi:customWidget>


    <youi:customWidget widgetName="gridPanel"
                       styleClass="col-sm-9 page-inner-height page-spliter-right no-padding hide-content">
        <youi:toolbar styleClass="fixed-height" refWidgetId="grid_dataResource">
            <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
            <youi:toolbarItem name="add" caption="新增" tooltips="" icon="plus"/>
        </youi:toolbar>

        <youi:grid id="grid_dataResource" src="/metadataServices/services/dataResourceManager/getPagerDataResources.json"
                   removeSrc="/metadataServices/services/dataResourceManager/removeDataResource.json" idKeys="id"
                   showNum="true" query="NOT" reset="NOT" fixedFooter="true">
            <youi:fieldLayout prefix="query">
                <youi:fieldText property="caption"  caption="资源名称"/>
                <youi:fieldText property="catalog"  caption="资源目录"/>
                <youi:fieldText property="schema"  caption="数据结构"/>
                <youi:fieldText property="status"  caption="资源状态"/>
            </youi:fieldLayout>

            <youi:gridCol width="45%" property="caption"  caption="资源名称"/>
            <youi:gridCol width="15%" property="catalog"  caption="资源目录"/>
            <youi:gridCol width="15%" property="schema"  caption="数据结构"/>
            <youi:gridCol width="15%" property="status"  caption="资源状态"/>

            <youi:gridCol width="10%" type="button" property="button" caption="操作">
                <youi:button name="edit" caption="编辑"/>
                <youi:button name="schemaTables" caption="表同步" icon="table"/>
                <youi:button name="split" caption=""/>
                <youi:button name="removeRecord" icon="remove" caption="删除"/>
            </youi:gridCol>
        </youi:grid>
    </youi:customWidget>

    <!-- Grid编辑按钮动作：打开修改数据资源弹出页 -->
    <youi:func name="grid_dataResource_edit" params="dom,options,record">
        $elem('subpage_dataResource_edit',pageId).subpage('open',{},{},record,pageId);
    </youi:func>
    <!-- Grid新增按钮动作：打开新增数据资源弹出页 -->
    <youi:func name="grid_dataResource_add" params="dom,options">
        $elem('subpage_dataResource_add',pageId).subpage('open',{},{},{},pageId);
    </youi:func>

    <youi:func name="grid_dataResource_schemaTables" params="dom,options,record">
        $elem('subpage_schemaTables',pageId).subpage('open',{},{},record,pageId);
    </youi:func>


    <!-- 编辑数据资源的subpage内容变化回调函数 -->
    <youi:func name="subpage_dataResource_edit_change" params="record">
        $elem('grid_dataResource',pageId).grid('refresh');
    </youi:func>
    <!-- 新增数据资源的subpage内容变化回调函数 -->
    <youi:func name="subpage_dataResource_add_change" params="record">
        $elem('grid_dataResource',pageId).grid('refresh');
    </youi:func>
</youi:page>