<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- 页面加载时获取数据资源集合，TODO 需要修改为合并配置修改的数据资源数据 --%>
<youi:page caption="数据源授权" dataSrc="/metadataServices/services/dataDictionaryFinder/getDataSourceIteratorTree.json">

    <%-- 获取机构的数据资源组集合() --%>
    <youi:ajaxUrl name="getAgencyDataAssignUrl" src="/baseServices/services/dataAuthService/getAgencyData.json?authDataType=datasource"/>

    <%-- 机构树 --%>
    <youi:customWidget name="page_spliter" widgetName="pageSpliter"
                       styleClass="col-sm-3 page-inner-height border">
        <youi:tabs id="tabs_agency" styleClass="top-border">
            <%--  --%>
            <youi:tabItem id="agency" caption="管理机构">

            </youi:tabItem>
            <%--  --%>
            <youi:tabItem id="softwareSP" caption="软件开发商">
                <youi:tree id="tree_software_sp" idAttr="agencyId" rootText="开发商列表" hideRoot="true" textAttr="shortCaption"  styleClass="hide-root"
                            iteratorSrc="/softwarespServices/services/spCompanyManager/getSpCompanys.json">
                </youi:tree>
            </youi:tabItem>
        </youi:tabs>
    </youi:customWidget>
    <%--  --%>
    <youi:form id="form_assign" panel="false" submit="NOT" reset="NOT"
               action="/baseServices/services/dataAuthService/authAgencyDataIds.json?authDataType=datasource" idKeys="agencyId"
               styleClass="col-sm-9 page-inner-height page-spliter-right no-padding no-border">
        <youi:toolbar id="tree_bar" styleClass="fixed-height" refWidgetId="form_assign">
            <youi:toolbarItem name="submit" caption="保存" tooltips="" icon="save" groups="spcompany,agency"/>
            <li class="pull-right agency-title">

            </li>
        </youi:toolbar>
        <youi:fieldLayout>
            <youi:fieldHidden property="agencyId"/>
        </youi:fieldLayout>
        <youi:customWidget widgetName="assignList" name="dataIds" data-columns="3" data-height="360"
                           styleClass="no-padding">

        </youi:customWidget>
    </youi:form>

    <youi:func name="init" params="result">
        $elem('dataIds',pageId).assignList('parseList',result);
    </youi:func>

    <%-- 开发商切换 --%>
    <youi:func name="tree_software_sp_select" params="treeNode" urls="getAgencyDataAssignUrl">
        var agencyId = treeNode.attr('id');
        $elem('field_agencyId').fieldValue(agencyId);
        $elem('form_assign',pageId).form('find',funcUrls.getAgencyDataAssignUrl+'&agencyId='+agencyId)
            .trigger('activeTools',{groups:['spcompany']})
            .find('.agency-title:first').text($.youi.treeUtils.getNodeText(treeNode)+'数据资源分配情况');

    </youi:func>
</youi:page>