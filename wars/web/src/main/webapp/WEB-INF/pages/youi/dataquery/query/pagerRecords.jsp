<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:script src="/asserts/js/youi/field/field.queryparam.js"/>

<youi:page caption="${param.title} - 数据预览" dataSrc="/metadataServices/services/dataQueryManager/getDataQuery.json?id=${param.id}">
    <%-- grid panel 用于自动计算高度 --%>
    <youi:customWidget widgetName="gridPanel" name="gridPanel" styleClass="page-inner-height no-padding">
        <youi:toolbar refWidgetId="grid_queryData" styleClass="fixed-height">
            <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>

            <youi:dropdown caption="数据预览条数" icon="list">
                <youi:toolbarItem name="selectPageSize" caption="30条" value="30" tooltips=""/>
                <youi:toolbarItem name="selectPageSize" caption="50条" value="50" tooltips=""/>
                <youi:toolbarItem name="selectPageSize" caption="100条" value="100" tooltips=""/>
                <youi:toolbarItem name="selectPageSize" caption="200条" value="200" tooltips=""/>
                <youi:toolbarItem name="selectPageSize" caption="1000条" value="1000" tooltips=""/>
            </youi:dropdown>

        </youi:toolbar>

        <youi:grid id="grid_queryData" load="false" reset="NOT" query="NOT"
                   styleClass="auto-height" showFooter="false"
                   src="/metadataServices/services/dataQueryService/queryRowDataByPager.json?id=${param.id}">

            <youi:fieldLayout columns="1" labelWidths="0" styleClass="fixed-height">
                <youi:fieldHidden property="id" defaultValue="${param.id}"/>
                <youi:fieldCustom property="params" custom="fieldQueryParam" caption="参数" styleClass="form-inline"/>
            </youi:fieldLayout>

            <youi:gridCol property="Row" caption="序号" width="2%" align="center"/>
        </youi:grid>
    </youi:customWidget>

    <youi:func name="init" params="result">
        var cols = [];
        $(result.record.queryColumns).each(function(){
            cols.push({
                orderBy:'desc',
                property:this.columnName,
                caption:this.text||this.columnName,
                width:'20%'
            });
        });
        $elem('field_params',pageId).fieldQueryParam('loadParams',result.record.queryParams);
        $elem('grid_queryData',pageId).grid('appendCols',cols).grid('refresh');
    </youi:func>

</youi:page>