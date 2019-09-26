<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="${param.serverId}-api">

    <youi:ajaxUrl name="apiUrl" src="/${param.serverId}/v2/api-docs"/>

    <%--  --%>
    <youi:customWidget widgetName="swagger" name="swagger" urls="apiUrl" styleClass="no-padding">

    </youi:customWidget>

</youi:page>