<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="api">

    <youi:ajaxUrl name="apiUrl" src="/${param.serverId}/v2/api-docs"/>

    <%--  --%>
    <youi:customWidget widgetName="swagger" name="swagger" urls="apiUrl">

    </youi:customWidget>
</youi:page>