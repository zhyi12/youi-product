<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="" pageId="${param.pageId}">
    <youi:ajaxUrl name="loadDataUrl" src="/dataWorkingServices/services/workingCrossDataQueryService/queryByCrossReport.json?id=${param.id}"/>
    <youi:ajaxUrl name="loadDataCubesUrl" src="/dataWorkingServices/services/workingCrossDataQueryService/queryByDataCubes.json?id=${param.id}"/>

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
    <youi:func name="initPageData" params="record" urls="loadDataUrl,loadDataCubesUrl">
        $.youi.ajaxUtils.ajax({
            url:funcUrls.loadDataUrl,
            data:$.youi.recordUtils.recordToAjaxParamStr($.extend({},record.model)),
            success:function(result){
                $elem('crossTableViewer',pageId).crossTableViewer('parseRecords',{records:record.cubes});
                var datas = [],values = [];
                $(result.records).each(function(){
                    datas[this.rowIndex-1] = datas[this.rowIndex-1]||[];
                    datas[this.rowIndex-1][this.colIndex] = this.value;
                });

                $(datas).each(function(){values = values.concat(this)});

                $elem('crossTableViewer',pageId).find('td.data').each(function(index){
                    $(this).text(values[index]);
                });
            }
        });
    </youi:func>

</youi:page>