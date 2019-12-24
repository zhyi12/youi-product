<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:style href="/asserts/css/page/page.reportFileEditor.css"/>
<youi:page caption="识别数据">

<%--    <youi:ajaxUrl name="getModelUrl" src="page/datas/data.html?path=crosstable/cross"/>--%>
    <youi:ajaxUrl name="getModelUrl" src="/dataFileServices/services/periodFileConfigManager/getPeriodFileConfig.json?periodFileId=${param.id}"/>
    <youi:ajaxUrl name="saveModelUrl" src="/dataFileServices/services/periodFileConfigManager/savePeriodFileConfig.json?periodFileId=${param.id}"/>
    <youi:ajaxUrl name="fileUrl" src="/dataFileServices/services/periodFileService/parseXlsReport.json?id=${param.id}"/>
    <youi:ajaxUrl name="saveDataUrl" src="/dataWorkingServices/services/crossReportService/saveWorkingDatasFromCrossReport.json?id=${param.id}"/>

    <youi:subpage height="80" src="page/${_pagePath}.dialog/rename.html?pageId={subPageId}"
                  subpageId="rename" caption="重命名" type="dialog"/>

    <youi:subpage height="80" src="page/${_pagePath}.dialog/addPubYear.html?pageId={subPageId}"
                  subpageId="addPubYear" caption="新增年份" type="dialog"/>

    <youi:subpage height="80" src="page/${_pagePath}.dialog/addPubIndicator.html?pageId={subPageId}"
                  subpageId="addPubIndicator" caption="新增指标" type="dialog"/>

    <youi:subpage height="80" src="page/${_pagePath}.dialog/refMonth.html?pageId={subPageId}"
                  subpageId="refMonth" caption="关联月份" type="dialog"/>
    <youi:subpage height="80" src="page/${_pagePath}.dialog/refPeriod.html?pageId={subPageId}"
                  subpageId="refPeriod" caption="关联报告期" type="dialog"/>

    <youi:subpage height="500" src="page/${_pagePath}.dialog/refPeriods.html?pageId={subPageId}"
                  subpageId="refPeriods" caption="关联报告期" type="dialog"/>

    <youi:subpage height="80" src="page/${_pagePath}.dialog/refAttr.html?pageId={subPageId}"
                  subpageId="refAttr" caption="关联属性" type="dialog"/>

    <youi:subpage height="500" width="1080" src="page/${_pagePath}.dialog/crossTable.html?pageId={subPageId}"
                  subpageId="crossTable" caption="交叉表" type="dialog"/>
    <%--    --%>
    <youi:xmenu id="xmenu_tree">
        <youi:xmenuItem name="removeTreeNode" caption="删除" groups="categorys,indicators-item,categorys-item,periods,periods-item,areas,areas-item,attrs,attrs-item,years-item"/>

        <youi:xmenuItem name="removeNextAllNode" caption="删除下方全部节点" groups="categorys-item,periods-item,area-item,attrs-item"/>

        <youi:xmenuItem name="treeNodeMoveUp" caption="上移"
                        groups="main,slave,categorys,periods,areas,attrs"/>
        <youi:xmenuItem name="treeNodeMoveDown" caption="下移"
                        groups="main,slave,categorys,periods,areas,attrs"/>

        <youi:xmenuItem name="removeMainArea" caption="删除主栏区域" groups="main"/>
        <youi:xmenuItem name="removeSlaveArea" caption="删除宾栏区域" groups="slave"/>
        <youi:xmenuItem name="openAddCategoryItem" caption="新增分类项" groups="categorys"/>

        <youi:xmenuItem name="openAddPubIndicator" caption="新增指标" groups="pub-items,header-items"/>
        <youi:xmenuItem name="openAddCategoryItem" caption="新增分组项" groups="pub-items,header-items"/>
        <youi:xmenuItem name="openAddPubYear" value="year" caption="新增年份" groups="pub-items,header-items"/>

        <youi:xmenuItem name="openRefMonth"  caption="关联月份" groups="months-item"/>
        <youi:xmenuItem name="openRefAttr"  caption="关联属性" groups="attrs-item"/>
        <youi:xmenuItem name="openRefPeriod"  caption="关联报告期" groups="periods-item"/>
        <youi:xmenuItem name="openRefPeriods"  caption="关联报告期" groups="periods"/>

        <youi:xmenuItem name="openRename" caption="重命名" groups="categorys,categorys-item,periods-item,areas-item,attrs-item"/>
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
                       refs="tree_model,subpage_rename,subpage_crossTable,subpage_addPubYear,subpage_addPubIndicator,subpage_refMonth,subpage_refAttr,subpage_refPeriod,subpage_refPeriods"
                       urls="getModelUrl,saveModelUrl,fileUrl,saveDataUrl">
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