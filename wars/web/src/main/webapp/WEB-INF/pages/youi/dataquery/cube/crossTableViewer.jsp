<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%--
    弹出框类型界面: param.pageId 为该弹出业务的pageId，通过约定的规则回调callback 里面指定的initPageData方法
    主要用于解决加载顺序的问题。
--%>
<youi:page caption="查询结果" pageId="${param.pageId}">

    <%--  执行数据查询  --%>
    <youi:ajaxUrl name="cubeQueryUrl" src="page/datas/data.html?path=cube/cube"/>

    <youi:customWidget widgetName="crossTableViewer" name="crossTableViewer" styleClass="page-inner-height">
        <youi:toolbar refWidgetId="crossTableViewer" styleClass="fixed-height">
            <youi:toolbarItem name="exportXls" caption="导出" tooltips=""/>
        </youi:toolbar>
    </youi:customWidget>

    <youi:func name="init" params="result">
        $('#'+pageId).trigger('initPageData',{callback:'P_'+pageId+'_initPageData',record:{a:1}});
    </youi:func>

    <%-- 页面加载完成，默认的form表单数据设置后的操作 record由两部分组成 --%>
    <youi:func name="initPageData" params="record,options" urls="cubeQueryUrl">
        //
        var queryRecord = {};//组织立方体查询对象

        $.youi.ajaxUtils.ajax({
            url:funcUrls.cubeQueryUrl,
            data:$.youi.recordUtils.recordToAjaxParamStr(queryRecord),
            success:function(result){
                $elem('crossTableViewer',pageId).crossTableViewer('parseRecords',result);
            }
        });
    </youi:func>

</youi:page>