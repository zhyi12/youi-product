<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:style href="/asserts/css/page/page.reportFileEditor.css"/>
<youi:page caption="识别数据">

<%--    <youi:ajaxUrl name="getModelUrl" src="page/datas/data.html?path=crosstable/cross"/>--%>
    <youi:ajaxUrl name="getModelUrl" src="/dataFileServices/services/periodFileConfigManager/getPeriodFileConfig.json?periodFileId=${param.id}"/>
    <youi:ajaxUrl name="saveModelUrl" src="/dataFileServices/services/periodFileConfigManager/savePeriodFileConfig.json?periodFileId=${param.id}"/>
    <youi:ajaxUrl name="fileUrl" src="/dataFileServices/services/periodFileService/parseXlsReport.json?id=${param.id}"/>

    <youi:subpage height="80" src="page/${_pagePath}.dialog/rename.html?pageId={subPageId}"
                  subpageId="rename" caption="重命名" type="dialog"/>

    <youi:subpage height="500" width="1080" src="page/${_pagePath}.dialog/crossTable.html?pageId={subPageId}"
                  subpageId="crossTable" caption="交叉表" type="dialog"/>
    <%--    --%>
    <youi:xmenu id="xmenu_tree">
        <youi:xmenuItem name="removeTreeNode" caption="删除" groups="categorys,category-item,periods,period-item,areas,area-item,attrs,attr-item"/>

        <youi:xmenuItem name="removeNextAllNode" caption="删除下方全部节点" groups="category-item,period-item,area-item,attr-item"/>

        <youi:xmenuItem name="treeNodeMoveUp" caption="上移"
                        groups="main,slave,categorys,periods,areas,attrs"/>
        <youi:xmenuItem name="treeNodeMoveDown" caption="下移"
                        groups="main,slave,categorys,periods,areas,attrs"/>

        <youi:xmenuItem name="removeMainArea" caption="删除主栏区域" groups="main"/>
        <youi:xmenuItem name="removeSlaveArea" caption="删除宾栏区域" groups="slave"/>
        <youi:xmenuItem name="openAddCategoryItem" caption="新增分类项" groups="categorys"/>

        <youi:xmenuItem name="openAddIndicator" caption="新增指标" groups="pub-items,header-items"/>
        <youi:xmenuItem name="openAddCategoryItem" caption="新增分组项" groups="pub-items,header-items"/>
        <youi:xmenuItem name="openAddYear" caption="新增年份" groups="pub-items,header-items"/>



        <youi:xmenuItem name="openRename" caption="重命名" groups="categorys,category-item,period-item,area-item,attr-item"/>
    </youi:xmenu>
    <%--    --%>
    <youi:customWidget name="page_spliter" widgetName="pageSpliter"
                       styleClass="col-sm-2 page-inner-height">
        <youi:toolbar styleClass="fixed-height" refWidgetId="reportFileEditor">
            <youi:toolbarItem name="refresh" caption="清空配置" tooltips=""/>
            <youi:toolbarItem name="showCrossTable" caption="交叉表" tooltips=""/>
        </youi:toolbar>
        <youi:tree id="tree_model" xmenu="xmenu_tree"
                   styleClass="col-sm-12 no-padding hide-root overflow auto-height" hideRoot="true">

        </youi:tree>
    </youi:customWidget>
    <%--    --%>
    <youi:customWidget widgetName="reportFileEditor" name="reportFileEditor" pageModule="datafile"
                       styleClass="page-inner-height page-spliter-right col-sm-10"
                       refs="tree_model,subpage_rename,subpage_crossTable"
                       urls="getModelUrl,saveModelUrl,fileUrl">
        <youi:toolbar styleClass="fixed-height" refWidgetId="reportFileEditor">
            <youi:toolbarItem name="save" caption="保存配置" tooltips="" icon="save"/>
            <youi:toolbarItem name="saveData" caption="保存数据" tooltips="" icon="save" groups="data"/>
            <youi:toolbarItem name="setMainArea" caption="主栏区域" icon="table" tooltips=""/>
            <youi:toolbarItem name="setSlaveArea" caption="宾栏区域" icon="table" tooltips=""/>
            <youi:toolbarItem name="setIndicatorArea" caption="指标区" icon="list" tooltips=""/>
            <youi:toolbarItem name="setCategoryArea" caption="分类区" icon="th-list" tooltips=""/>
            <youi:toolbarItem name="setPeriodArea" caption="报告期区" icon="time" tooltips=""/>
            <youi:toolbarItem name="setYearArea" caption="年份区" icon="time" tooltips=""/>
            <youi:toolbarItem name="setMonthArea" caption="月份区" icon="time" tooltips=""/>
            <youi:toolbarItem name="setQuarterArea" caption="季度区" icon="time" tooltips=""/>
            <youi:toolbarItem name="setAttrArea" caption="属性区" icon="tag" tooltips=""/>
        </youi:toolbar>
    </youi:customWidget>

</youi:page>