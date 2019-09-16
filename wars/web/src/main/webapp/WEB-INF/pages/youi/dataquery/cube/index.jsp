<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="CUBE查询服务">

    <youi:customWidget name="page_spliter" widgetName="pageSpliter"
                       styleClass="col-sm-3 page-inner-height">
        <youi:toolbar styleClass="fixed-height" refWidgetId="cubeDesigner">
            <youi:toolbarItem name="back" caption="刷新" icon="refresh" tooltips=""/>
        </youi:toolbar>

        <youi:tabs id="tabs_sql">
            <youi:tabItem id="table" caption="物理表">
                <youi:tree rootText="数据资源树" styleClass="col-sm-12"
                           iteratorSrc="/metadataServices/services/dataResourceService/getUserDataResourceTree.json">
                </youi:tree>
            </youi:tabItem>
            <youi:tabItem id="query" caption="数据查询">

            </youi:tabItem>
        </youi:tabs>

    </youi:customWidget>

</youi:page>