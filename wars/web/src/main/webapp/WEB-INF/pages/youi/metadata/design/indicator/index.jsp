<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="${param.title}-指标管理">

    <youi:ajaxUrl name="getMetaDataItemUrl" src="/metadataServices/services/metaDataItemManager/getMetaDataItemByName.json"/>
    <youi:ajaxUrl name="getIndicatorUrl" src="/metadataServices/services/metaProjectIndicatorManager/getMetaProjectIndicator.json?id={id}"/>
    <youi:ajaxUrl name="removeIndicatorUrl" src="/metadataServices/services/metaProjectIndicatorManager/removeMetaProjectIndicator.json"/>
    <%-- --%>
    <youi:subpage src="page/${_pagePath}/editIndicator.html?projectId=${param.projectId}&parentId={parentId}" height="100"
                  subpageId="addIndicator" caption="新增指标" type="dialog"/>

    <youi:subpage src="page/${_pagePath}/addIndicatorTree.html?projectId=${param.projectId}&parentId={parentId}" height="400"
                  subpageId="addIndicatorTree" caption="批量新增" type="dialog"/>

    <youi:xmenu id="xmenu_indicator">
        <youi:xmenuItem name="addChildIndicator" caption="新增下级指标" groups="metaprojectindicator"/>
        <youi:xmenuItem name="removeIndicator" caption="删除指标" groups="metaprojectindicator"/>
    </youi:xmenu>

    <youi:customWidget name="page_spliter" widgetName="pageSpliter"
                       styleClass="col-sm-3 page-inner-height">
        <youi:toolbar styleClass="fixed-height" refWidgetId="tree_indicator">
            <youi:toolbarItem name="addIndicator" caption="新增指标" icon="plus" tooltips=""/>
            <youi:toolbarItem name="addIndicatorTree" caption="批量新增"  icon="plus" tooltips=""/>
        </youi:toolbar>

        <youi:tree id="tree_indicator" styleClass="col-sm-12 no-padding overflow auto-height" rootText="项目指标" xmenu="xmenu_indicator"
                   iteratorSrc="/metadataServices/services/metaProjectIndicatorManager/getProjectIndicatorTree.json?metaProjectId=${param.projectId}">

        </youi:tree>
    </youi:customWidget>
    <%-- 指标编辑 --%>
    <youi:form id="form_indicator" submit="NOT" reset="NOT" action="/metadataServices/services/metaProjectIndicatorManager/saveMetaProjectIndicator.json"
               styleClass="col-sm-9 page-inner-height page-spliter-right no-padding hide-content" panel="false">
        <youi:toolbar styleClass="fixed-height" refWidgetId="form_indicator">
            <youi:toolbarItem name="submit" caption="保存"  icon="save" tooltips="" groups="metaprojectindicator"/>
        </youi:toolbar>

        <youi:fieldLayout labelWidths="100" columns="1" styleClass="top-padding">
            <youi:fieldLabel property="id" caption="指标编码" />
            <youi:fieldHidden property="parentId" caption="parentId" />
            <youi:fieldHidden property="metaProjectId" caption="项目ID" />
            <youi:fieldText notNull="true" property="text" caption="指标名称"/>

            <youi:fieldAutocomplete property="unitId" code="id" show="unitCaption" showProperty="text"
                                    findTextSrc="/metadataServices/services/measureUnitManager/getMeasureUnit.json"
                                    src="/metadataServices/services/measureUnitManager/searchByTerm.json" caption="计量单位"/>
            <youi:fieldAutocomplete property="metaDataItemName" code="name" show="text" showProperty="text"
                                    findTextSrc="/metadataServices/services/metaDataItemManager/getMetaDataItemByName.json"
                                    src="/metadataServices/services/metaDataItemManager/searchByTerm.json" caption="数据项"/>
        </youi:fieldLayout>
    </youi:form>
    <%-- 打开新增指标窗口 --%>
    <youi:func name="tree_indicator_addIndicator">
        $elem('subpage_addIndicator',pageId).subpage('open');
    </youi:func>

    <%-- 打开批量新增指标窗口 --%>
    <youi:func name="tree_indicator_addIndicatorTree">
        $elem('subpage_addIndicatorTree',pageId).subpage('open');
    </youi:func>

    <%-- 树节点右键菜单回调函数 --%>
    <youi:func name="tree_indicator_xmenu_addChildIndicator" params="treeNode">
        var parentId = treeNode.data('id');
        $elem('subpage_addIndicator',pageId).subpage('open',{},{},{parentId:parentId});
    </youi:func>

    <youi:func name="tree_indicator_xmenu_removeIndicator" params="treeNode">
        var text = $.youi.treeUtils.getNodeText(treeNode);
        $.youi.messageUtils.confirm('确认删除['+text+']指标?',function(){
            $.youi.pageUtils.doPageFunc('doRemoveIndicator',pageId,null,treeNode);
        });
    </youi:func>

    <%-- 删除指标 --%>
    <youi:func name="doRemoveIndicator" params="treeNode" urls="removeIndicatorUrl">
        var id = treeNode.data('id');
        $.youi.ajaxUtils.ajax({
            url:funcUrls.removeIndicatorUrl,
            data:{id:id},
            success:function(result){
                var treeElem = $elem('tree_indicator',pageId);
                treeElem.tree('removeNode',treeNode);
            }
        });
    </youi:func>

    <%-- 指标节点切换选中 --%>
    <youi:func name="tree_indicator_select" params="treeNode" urls="getIndicatorUrl">
        var groups = [],record = treeNode.data();
        if(treeNode.hasClass('metaprojectindicator')){
            groups.push('metaprojectindicator');
        }
        var findSrc = $.youi.recordUtils.replaceByRecord(funcUrls.getIndicatorUrl,record);
        $elem('form_indicator',pageId).trigger('activeTools',{groups:groups}).form('find',findSrc);
    </youi:func>

    <%-- 新增指标成功后更新树 --%>
    <youi:func name="subpage_addIndicator_change" params="record">
        var parentNode,treeElem = $elem('tree_indicator',pageId);
        if(record.parentId){
            parentNode = treeElem.find('.treeNode[id='+record.parentId+']:first');
        }else{
            parentNode = treeElem.find('.treeNode.root:first');
        }

        if(parentNode.length){
            var attrObj = {id:record.id};
            attrObj['project-id'] = record.metaProjectId;
            treeElem.tree('addNode',parentNode,record.id,record.text,attrObj,{group:'metaprojectindicator'});
        }
    </youi:func>

    <%-- 批量新增指标成功后更新树 --%>
    <youi:func name="subpage_addIndicatorTree_change" params="record">
        var treeElem = $elem('tree_indicator',pageId),
            rootElem = treeElem.find('.treeNode.root:first').removeClass('loaded').removeClass('expanded');
        rootElem.find('>ul').empty();
        treeElem.tree('openPath',rootElem.attr('id')+'/');
    </youi:func>

    <%-- 指标保存成功后 --%>
    <youi:func name="form_indicator_afterSubmit" params="result">
        var treeElem = $elem('tree_indicator',pageId),
            treeNode = treeElem.find('.treeNode[data-id='+result.record.id+']');

        treeNode.find('>span>a').text(result.record.text);
    </youi:func>

</youi:page>