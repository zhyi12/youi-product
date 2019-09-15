<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="CUBE设计-${param.title}" dataSrc="/metadataServices/services/dataQueryManager/getDataQuery.json?id=${param.id}">


    <youi:customWidget name="page_spliter" widgetName="pageSpliter"
                       styleClass="col-sm-3 page-inner-height">
        <youi:toolbar styleClass="fixed-height" refWidgetId="cubeDesigner">
            <youi:toolbarItem name="back" caption="返回" icon="save" tooltips=""/>
            <youi:dropdown caption="模版" styleClass="cube-list">

            </youi:dropdown>
        </youi:toolbar>
        <youi:tabs id="tabs">
            <youi:tabItem id="fields" caption="字段">

            </youi:tabItem>
            <youi:tabItem id="params" caption="参数"/>
        </youi:tabs>
    </youi:customWidget>

    <%-- asserts/js/page/youi.cubedesigner.js --%>
    <youi:customWidget widgetName="cubeDesigner" name="cubeDesigner" pageModule="dataquery"
                       styleClass="no-padding page-inner-height col-sm-9 page-spliter-right">
        <youi:toolbar styleClass="fixed-height" refWidgetId="cubeDesigner">

            <youi:toolbarItem name="save" caption="保存" icon="save" tooltips=""/>
        </youi:toolbar>
    </youi:customWidget>

    <%-- 初始化数据 --%>
    <youi:func name="init" params="result">
        $elem('cubeDesigner',pageId).cubeDesigner('parseColumns',result.record.queryColumns,$elem('tabs',pageId).next('.tab-content').find('[data-id=fields]'));
    </youi:func>

</youi:page>