<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="编辑指标"  autoLoadData="${param.id!=null}"
    dataSrc="/metadataServices/services/metaProjectIndicatorManager/getMetaProjectIndicator.json?projectId=${param.id}">
    <%-- --%>
    <youi:form id="form_meta_indicator" reset="NOT" submit="NOT" close="NOT" panel="false"
               action="/metadataServices/services/metaProjectIndicatorManager/saveMetaProjectIndicator.json">
        <youi:fieldLayout columns="1" prefix="addMetaProject" labelWidths="120">
            <youi:fieldHidden property="id" caption="ID" defaultValue="${param.id}"/>
            <youi:fieldHidden property="parentId" caption="parentId" defaultValue="${param.parentId}"/>
            <youi:fieldHidden property="metaProjectId" caption="项目ID" defaultValue="${param.projectId}"/>
            <youi:fieldText notNull="true" property="text" caption="指标名称"/>

            <youi:fieldAutocomplete property="unitId" code="id" show="unitCaption" showProperty="text"
                                    findTextSrc="/metadataServices/services/measureUnitManager/getMeasureUnit.json"
                                    src="/metadataServices/services/measureUnitManager/searchByTerm.json" caption="计量单位"/>
            <youi:fieldAutocomplete property="metaDataItemName" code="name" show="text" showProperty="text"
                                    findTextSrc="/metadataServices/services/metaDataItemManager/getMetaDataItemByName.json"
                                    src="/metadataServices/services/metaDataItemManager/searchByTerm.json" caption="数据项"/>
        </youi:fieldLayout>
    </youi:form>

    <youi:button name="submit" caption="确定" submitProperty="submit" submitValue="1"/>

    <youi:func name="form_meta_indicator_afterSubmit" params="result">
        $.youi.pageUtils.closeAndRefreshSubpage(pageId,result.record);//关闭并刷新
    </youi:func>
</youi:page>