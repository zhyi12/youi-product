<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="数据表">

    <youi:subpage height="150" src="page/${_pagePath}/dataTableEdit.html?id={id}" subpageId="dataTable_edit" caption="修改数据表" type="dialog"/>
    <youi:subpage height="150" src="page/${_pagePath}/dataTableEdit.html" subpageId="dataTable_add" caption="添加数据表" type="dialog"/>

    <youi:subpage  src="page/youi.metadata.dataresource.datacolumn/dataTableColumnsEdit.html?dataResourceId={dataResourceId}&tableName={tableName}" subpageId="dataColumns" caption="数据列" type="secondPage"/>

    <youi:toolbar refWidgetId="grid_dataTable">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="plus"/>
        <youi:toolbarItem name="dbCheck" caption="检查" tooltips="数据库检查" icon="check-sign"/>
    </youi:toolbar>
    <youi:grid id="grid_dataTable" src="/metadataServices/services/dataTableManager/getPagerDataTables.json"
               removeSrc="/metadataServices/services/dataTableManager/removeDataTable.json" idKeys="id,dataResourceId"
               showNum="true" query="NOT" reset="NOT" fixedFooter="true" showCheckbox="true">
        <youi:fieldLayout prefix="query">
            <youi:fieldText property="tableName"  caption="表名"/>
            <youi:fieldText property="tableCaption"  caption="中文表名"/>
            <youi:fieldText property="dataResourceId" column="2" caption="数据资源"/>
        </youi:fieldLayout>
        <youi:gridCol width="15%" property="catalog"  caption="catalog"/>
        <youi:gridCol width="15%" property="schema"  caption="schema"/>
        <youi:gridCol width="10%" property="entityCode"  caption="实体编号"/>
        <youi:gridCol width="25%" property="tableName"  caption="表名"/>
        <youi:gridCol width="35%" property="tableCaption"  caption="中文表名"/>

        <youi:gridCol width="10%" type="button" property="button" caption="操作">
            <youi:button name="edit" caption="编辑"/>
            <youi:button name="dataColumns" caption="数据列" icon="list"/>

            <youi:button name="removeRecord" icon="remove" caption="删除"/>
        </youi:gridCol>
    </youi:grid>

    <youi:form id="dialog_dataTable_check" dialog="true" caption="数据表检查" reset="NOT" submit="NOT" styleClass="no-padding">
        <youi:tabs id="tabs_dataTable_check" itemSrc="page/${_pagePath}/checkResult.html?dataResouceId={resourceId}&tableName={tableName}&catalog={catalog}&schema={schema}" styleClass="tabs-check" itemHeight="400">
        </youi:tabs>
    </youi:form>

    <!-- Grid编辑按钮动作：打开修改数据表弹出页 -->
    <youi:func name="grid_dataTable_edit" params="dom,options,record">
        $elem('subpage_dataTable_edit',pageId).subpage('open',{},{},record,pageId);
    </youi:func>
    <!-- Grid新增按钮动作：打开新增数据表弹出页 -->
    <youi:func name="grid_dataTable_add" params="dom,options">
        $elem('subpage_dataTable_add',pageId).subpage('open',{},{},{},pageId);
    </youi:func>

    <youi:func name="grid_dataTable_dataColumns" params="dom,options,record">
        $elem('subpage_dataColumns',pageId).subpage('open',{},{},record,pageId);
    </youi:func>

    <!-- Grid编辑按钮动作：数据检查 -->
    <youi:func name="grid_dataTable_dbCheck">
        var selectedRecords  = $elem('grid_dataTable',pageId).grid('getSelectedRecords');
        //遍历选择的记录
        var tabsHeaderHtmls = [],tabsPanelHtmls = [],firstTabItemId;
        $(selectedRecords).each(function(index){
            var tabItemId = 'P_'+pageId+'_'+this.id;
            tabsHeaderHtmls.push('<li title="'+this.tableName+'" id="'+tabItemId+'" class="tabs-item">');
            tabsHeaderHtmls.push('<a data-id="'+this.id+'" class="tabs-item-text" data-toggle="tab" href="#'+tabItemId+'_panel">');
            tabsHeaderHtmls.push(this.tableCaption+'</a> </li>');
            tabsPanelHtmls.push('<div id="'+tabItemId+'_panel" class="tab-pane" data-resource-id="'+this.dataResourceId+'"  data-schema="'+this.schema+'" data-catalog="'+this.catalog+'" data-table-name="'+this.tableName+'" data-id="'+this.id+'"></div>');

            if(!firstTabItemId)firstTabItemId = tabItemId;
        });
        var dialogElem = $elem('dialog_dataTable_check',pageId).dialog('open'),
            tabsElem = dialogElem.find('.tabs-check.youi-tabs:first').html(tabsHeaderHtmls.join(''));

        tabsElem.next('.tab-content').html(tabsPanelHtmls.join(''));
        tabsElem.find('a:first').click();
    </youi:func>

    <!-- 编辑数据表的subpage内容变化回调函数 -->
    <youi:func name="subpage_dataTable_edit_change" params="record">
        $elem('grid_dataTable',pageId).grid('refresh');
    </youi:func>
    <!-- 新增数据表的subpage内容变化回调函数 -->
    <youi:func name="subpage_dataTable_add_change" params="record">
        $elem('grid_dataTable',pageId).grid('refresh');
    </youi:func>

</youi:page>