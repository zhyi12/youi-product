<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="" pageId="${param.pageId}">

    <youi:form id="form" submit="NOT" reset="NOT" panel="false">
        <youi:fieldLayout columns="1">
            <youi:fieldHidden property="parentId" caption="parentId"/>
            <youi:fieldText property="id" caption="指标编码"/>
            <youi:fieldText property="text" caption="指标名称"/>
        </youi:fieldLayout>
    </youi:form>

    <youi:button name="submit" caption="确定" submitProperty="submit" submitValue="1"/>

    <%-- init 触发主页面subpage的组件的initPageData事件，并设置页面异步加载完成后的回调函数 --%>
    <youi:func name="init" >
        $('#'+pageId).trigger('initPageData',{callback:'P_'+pageId+'_initPageData'});
    </youi:func>

    <%-- 页面加载完成，默认的form表单数据设置后的操作 --%>
    <youi:func name="initPageData" params="record">

    </youi:func>
</youi:page>