<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="编辑制度" pageId="${param.pageId}"
           autoLoadData="${param.id!=null}" dataSrc="/metadataServices/services/metaObjectNodeManager/getRefMetaObject.json?id=${param.id}">

    <youi:form id="form" panel="false" reset="NOT" submit="NOT"
               action="metadata.metaObjectNodeManager.createMetaObjectNode">

        <youi:fieldLayout labelWidths="100,100">
            <youi:fieldHidden property="projectId"/>
            <youi:fieldHidden property="metaObjectName" defaultValue="metaPlan" caption="元数据名"/>
            <youi:fieldHidden property="refMetaObjectId"  caption="关联元数据"/>
            <youi:fieldText notNull="true" column="2" property="text" caption="制度名称"/>
            <youi:fieldCalendar notNull="true" property="begDate" caption="启用日期"/>
            <youi:fieldCalendar notNull="true" property="endDate" caption="过期日期"/>
            <youi:fieldText notNull="true" column="2" property="version" caption="制度版本"/>
            <youi:fieldTree property="agencyId" caption="所属机构" column="2">

            </youi:fieldTree>
        </youi:fieldLayout>

    </youi:form>

    <youi:button name="submit" caption="确定" submitProperty="submit" submitValue="1"/>

    <youi:func name="init" params="result">
        $('#'+pageId).trigger('initPageData',{callback:'P_'+pageId+'_initPageData',record:result.record});
    </youi:func>

    <%-- 页面加载完成，默认的form表单数据设置后的操作 --%>
    <youi:func name="initPageData" params="record,options">

    </youi:func>
</youi:page>
