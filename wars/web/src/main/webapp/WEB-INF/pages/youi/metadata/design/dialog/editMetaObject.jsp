<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="">

    <youi:ajaxUrl name="getFieldItemsUrl"
                  src="/metadataServices/services/metaObjectService/getMetaObjectFieldItems.json?id=${param.refMetaObjectId}&metaObjectName=${param.metaObjectName}"/>

    <youi:customWidget data-metaObjectName="${param.metaObjectName}"
                       urls="getFieldItemsUrl"
                       widgetName="formFields" name="formFields" styleClass="col-sm-10">

    </youi:customWidget>

    <%-- 展示下级元数据统计信息 --%>
    <youi:gridList styleClass="col-sm-2" load="false">

    </youi:gridList>


    <youi:func name="init" params="result">
        console.log(result);
    </youi:func>

</youi:page>
