<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="编辑项目"  autoLoadData="${param.projectId!=null}"
    dataSrc="/metadataServices/services/metaProjectManager/getMetaProject.json?projectId=${param.projectId}">

    <youi:form id="form_meta_project" reset="NOT" submit="NOT" close="NOT" panel="false"
               action="/metadataServices/services/metaProjectManager/saveMetaProject.json">
        <youi:fieldLayout columns="1" prefix="addMetaProject" labelWidths="120">
            <youi:fieldHidden property="projectId" caption="项目id"/>
            <youi:fieldText notNull="true" property="projectCaption" caption="项目名称"/>
            <youi:fieldArea property="description" caption="项目描述" rows="5"/>
        </youi:fieldLayout>
    </youi:form>

    <youi:button name="submit" caption="确定" submitProperty="submit" submitValue="1"/>

    <youi:func name="init" params="result">
        if(result){
            //填充数据
            $elem('form_meta_project',pageId).form('fillRecord',result.record);
        }
    </youi:func>

    <youi:func name="form_meta_project_afterSubmit" params="result">
        $.youi.pageUtils.closeAndRefreshSubpage(pageId,result.record);//关闭并刷新
    </youi:func>
</youi:page>