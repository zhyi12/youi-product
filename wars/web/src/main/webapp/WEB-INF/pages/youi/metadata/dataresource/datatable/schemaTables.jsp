<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据表"
           dataSrc="/metadataServices/services/dataDictionaryFinder/findTables.json?catalog=${param.catalog}&schema=${param.schema}">

    <youi:form id="form" styleClass="no-padding"
               action="/metadataServices/services/dataTableManager/syncDataTables.json" panel="false" reset="NOT" submit="NOT" confirmMessage="确认同步?">
        <youi:fieldLayout styleClass="border-bottom-open">
            <youi:fieldLabel property="catalog" caption="catalog" defaultValue="${param.catalog}"/>
            <youi:fieldLabel property="schema" caption="schema" defaultValue="${param.schema}"/>
        </youi:fieldLayout>

        <youi:customWidget widgetName="assignList" name="tableNames" data-columns="6" data-height="360"
                           styleClass="border no-padding">

        </youi:customWidget>
    </youi:form>

    <youi:button name="submit" caption="同步数据表" submitProperty="submit" submitValue="1"/>

    <youi:func name="init" params="result">
        $elem('tableNames',pageId).assignList('parseList',result);
    </youi:func>

</youi:page>