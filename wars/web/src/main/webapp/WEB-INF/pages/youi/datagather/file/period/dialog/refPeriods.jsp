<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="" pageId="${param.pageId}">

    <youi:ajaxUrl name="matchPeriodItemsUrl" src="/dataWorkingServices/services/periodService/matchPeriodItems.json"/>

    <youi:form id="form" submit="NOT" reset="NOT" panel="false">
        <youi:fieldLayout columns="1">
            <youi:fieldHidden property="id" caption="id"/>
        </youi:fieldLayout>

        <youi:fieldGrid property="items">
            <youi:grid id="grid_items" load="false" query="NOT" reset="NOT" showFooter="false" idKeys="id">
                <youi:gridCol width="15%" property="id" caption="ID"/>
                <youi:gridCol width="25%" property="text" caption="text"/>
                <youi:gridCol width="10%" property="level" caption="累计"/>
                <youi:gridCol editor="fieldPeriodType" width="50%" property="mappedId" caption="报告期"/>
            </youi:grid>
        </youi:fieldGrid>
    </youi:form>

    <youi:button name="submit" caption="确定" submitProperty="submit" submitValue="1"/>

    <%-- init 触发主页面subpage的组件的initPageData事件，并设置页面异步加载完成后的回调函数 --%>
    <youi:func name="init" >
        $('#'+pageId).trigger('initPageData',{callback:'P_'+pageId+'_initPageData'});
    </youi:func>

    <%-- 页面加载完成，默认的form表单数据设置后的操作 --%>
    <youi:func name="initPageData" params="record" urls="matchPeriodItemsUrl">
        $.youi.ajaxUtils.ajax({
            url:funcUrls.matchPeriodItemsUrl,
            data:$.youi.recordUtils.recordToAjaxParamStr(record),
            success:function(result){
                $elem('grid_items',pageId).grid('parseRecords',result.records);
            }
        });
    </youi:func>
</youi:page>