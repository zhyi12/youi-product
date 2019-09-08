<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="方案配置">
    <youi:ajaxUrl name="getMetaObjectConfigUrl" src="/metadataServices/services/metaObjectConfigManager/getMetaObjectConfig.json"/>
    <youi:ajaxUrl name="saveMetaObjectConfigUrl" src="/metadataServices/services/metaObjectConfigManager/saveMetaObjectConfig.json"/>
    <youi:ajaxUrl name="removeMetaObjectConfigUrl" src="/metadataServices/services/metaObjectConfigManager/removeMetaObjectConfig.json"/>

    <youi:customWidget widgetName="metaObjectConfig" name="metaObjectConfig" pageModule="metadata"
                       data-metaObjectName="metaTask" urls="getMetaObjectConfigUrl,saveMetaObjectConfigUrl,removeMetaObjectConfigUrl">

        <youi:toolbar styleClass="fixed-height" refWidgetId="metaObjectConfig">
            <youi:toolbarItem name="submit" caption="提交" icon="save" tooltips=""/>
        </youi:toolbar>

        <youi:grid id="grid_metaObjectConfig" panel="false" showCheckbox="true"
                   editable="true" load="false" query="NOT" reset="NOT" showFooter="false">
            <youi:gridCol width="15%" editor="fieldText" property="caption" caption="中文名称"/>
            <youi:gridCol width="15%" editor="fieldText" property="property" caption="属性名"/>
            <youi:gridCol width="15%" editor="fieldText" property="notNull" caption="可否为空"/>
            <youi:gridCol width="15%" editor="fieldText" property="fieldType" caption="编辑类型"/>
            <youi:gridCol width="15%" editor="fieldText" property="column" caption="列占位"/>
            <youi:gridCol width="15%" editor="fieldText" property="dataType" caption="数据类型"/>
            <youi:gridCol width="15%" editor="fieldText" property="dataLength" caption="长度"/>
        </youi:grid>

    </youi:customWidget>
</youi:page>