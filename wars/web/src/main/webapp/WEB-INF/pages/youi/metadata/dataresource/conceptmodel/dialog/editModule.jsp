<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page pageId="${param.dialogId}">

    <youi:form id="form" panel="false" submit="NOT" reset="NOT"
               action="/metadataServices/services/conceptItemManager/saveModuleItem.json">
        <youi:fieldLayout columns="1" labelWidths="120">
            <youi:fieldHidden property="dataResourceId" defaultValue="${param.dataResourceId}"/>
            <youi:fieldHidden property="subSystemId"/>
            <youi:fieldText notNull="true" property="id" caption="模块编码"/>
            <youi:fieldText notNull="true" property="caption" caption="模块名称"/>
        </youi:fieldLayout>
    </youi:form>

    <youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>

    <%-- init 触发主页面subpage的组件的initPageData事件，并设置页面异步加载完成后的回调函数 --%>
    <youi:func name="init">
        $('#'+pageId).trigger('initPageData',{callback:'P_'+pageId+'_initPageData'});
    </youi:func>

    <%-- 页面加载完成，默认的form表单数据设置后的操作 --%>
    <youi:func name="initPageData" params="record">

    </youi:func>
</youi:page>