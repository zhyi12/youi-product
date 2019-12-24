<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="" pageId="${param.pageId}">

    <youi:form id="form" submit="NOT" reset="NOT" panel="false">
        <youi:fieldLayout columns="1" labelWidths="120">
            <youi:fieldHidden property="id" caption="id"/>
            <youi:fieldLabel property="text" caption="名称"/>
            <youi:fieldSelect notNull="true" property="periodType" convert="periodType" caption="报告期类型" defaultValue="1"/>
            <youi:fieldCustom notNull="true" custom="fieldPeriodType" customOptions="{parents:'periodType'}" property="mappedId" caption="报告期"/>
            <youi:fieldRadioGroup property="level" caption="累计" convert="boolean"/>
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