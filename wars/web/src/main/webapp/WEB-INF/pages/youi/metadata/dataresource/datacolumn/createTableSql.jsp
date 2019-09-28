<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page dataSrc="/metadataServices/services/dataTableSqlService/buildCreateSql.json?dataResourceId=${param.dataResourceId}&tableName=${param.tableName}">

    <youi:fieldLayout columns="1">
        <youi:fieldArea property="html" caption="SQL" rows="20">

        </youi:fieldArea>
    </youi:fieldLayout>

    <youi:func name="init" params="result">
        $elem('field_html',pageId).fieldValue(result.record.html);
    </youi:func>

</youi:page>
