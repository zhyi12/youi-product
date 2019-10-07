<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据资源组" dataSrc="/metadataServices/services/dataDictionaryFinder/getDataSourceIteratorTree.json">

    <youi:fieldLayout>
        <youi:customWidget widgetName="assignList" name="datasources" data-columns="3" data-height="360"
                           styleClass="border no-padding">

        </youi:customWidget>
    </youi:fieldLayout>

    <youi:func name="init" params="result">
        $elem('datasources',pageId).assignList('parseList',result);
    </youi:func>
</youi:page>