<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="" pageId="${param.pageId}">

    <youi:form id="form" panel="false" reset="NOT" submit="NOT"
               action="/metadataServices/services/metaObjectNodeManager/createMetaObjectNode.json">

        <youi:fieldLayout labelWidths="100" columns="1">
            <youi:fieldHidden property="projectId"/>
            <youi:fieldHidden property="metaObjectName" defaultValue="metaIndicator" caption="元数据名"/>
            <youi:fieldHidden property="refMetaObjectId"  caption="关联元数据"/>
            <youi:fieldHidden property="parentId" caption="父节点ID"/>
            <youi:fieldText notNull="true" property="text" caption="指标名称"/>
            <youi:fieldAutocomplete src="a.json?projectId=${param.projectId}"  property="ref" caption="关联指标"/>
            <youi:fieldAutocomplete src="u.json" property="unitId" caption="计量单位"/>
        </youi:fieldLayout>
    </youi:form>
    <%--  --%>
    <youi:button name="submit" caption="确定" submitProperty="submit" submitValue="1"/>
    <%--  --%>
    <youi:func name="init" params="result">
        $('#'+pageId).trigger('initPageData',{callback:'P_'+pageId+'_initPageData',record:result.record});
    </youi:func>

    <%-- 页面加载完成，默认的form表单数据设置后的操作 --%>
    <youi:func name="initPageData" params="record,options">
        console.log(1);
    </youi:func>

</youi:page>
