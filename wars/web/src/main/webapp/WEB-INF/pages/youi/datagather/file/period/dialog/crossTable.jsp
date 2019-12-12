<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="" pageId="${param.pageId}">

    <youi:customWidget widgetName="crossTableViewer" name="crossTableViewer" styleClass="page-inner-height">
        <youi:toolbar refWidgetId="crossTableViewer" styleClass="fixed-height">
            <youi:toolbarItem name="exportXls" caption="导出" tooltips=""/>
        </youi:toolbar>
    </youi:customWidget>

    <%-- init 触发主页面subpage的组件的initPageData事件，并设置页面异步加载完成后的回调函数 --%>
    <youi:func name="init" >
        $('#'+pageId).trigger('initPageData',{callback:'P_'+pageId+'_initPageData'});
    </youi:func>

    <%-- 页面加载完成，默认的form表单数据设置后的操作 --%>
    <youi:func name="initPageData" params="record">
        var crossTableViewerElem = $elem('crossTableViewer',pageId);
        if(crossTableViewerElem.crossTableViewer){
            $elem('crossTableViewer',pageId).crossTableViewer('parseRecords',{records:record.cubes});
        }else{
            $elem('crossTableViewer',pageId).bind('initParseRecords',function(){
                $elem('crossTableViewer',pageId).crossTableViewer('parseRecords',{records:record.cubes});
            });
        }
    </youi:func>

</youi:page>