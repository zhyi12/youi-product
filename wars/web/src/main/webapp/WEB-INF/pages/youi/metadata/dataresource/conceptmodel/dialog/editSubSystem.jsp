<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page pageId="${param.dialogId}">
    <youi:form id="form" panel="false" submit="NOT" reset="NOT"
               action="/metadataServices/services/conceptItemManager/saveSubSystemItem.json">
        <youi:fieldLayout columns="1" labelWidths="120">
            <youi:fieldHidden property="dataResourceId" defaultValue="${param.dataResourceId}"/>
            <youi:fieldHidden property="id" caption="主键"/>
            <youi:fieldText notNull="true" property="name" caption="子系统编码"/>
            <youi:fieldText notNull="true" property="caption" caption="子系统名称"/>
        </youi:fieldLayout>
    </youi:form>

    <youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
</youi:page>