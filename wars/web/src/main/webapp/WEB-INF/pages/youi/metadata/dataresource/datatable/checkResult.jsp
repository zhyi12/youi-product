<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:style href="/asserts/css/youi/youi.matcherlist.css"/>
<youi:page >

    <youi:ajaxUrl name="matcherUrl" src="/metadataServices/services/dataTableCheckService/doCheckFromDb.json?tableName=${param.tableName}&schema=${param.schema}&catalog=${param.catalog}"/>

    <youi:customWidget widgetName="matcherList" name="matcherList" styleClass="page-inner-height no-padding"
                       urls="matcherUrl"
                       data-sourceItems="tableColumnItems" data-targetItems="dbColumnItems">
    </youi:customWidget>

</youi:page>