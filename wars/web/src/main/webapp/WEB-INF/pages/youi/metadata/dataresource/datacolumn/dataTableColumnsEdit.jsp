<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:script src="/asserts/js/youi/field/field.matching.js"/>
<youi:page caption="数据列">

    <youi:subpage src="page/${_pagePath}/createTableSql.html?dataResourceId=${param.dataResourceId}&tableName=${param.tableName}&_timestamp={timestamp}"
                  subpageId="createTableSql" caption="建表语句" type="dialog"/>

    <youi:ajaxUrl name="matchingMetaDataItemsUrl" src="/metadataServices/services/metaDataItemManager/matchingMetaDataItems.json"/>
    <youi:ajaxUrl name="updateDataTableColumnsUrl" src="/metadataServices/services/dataTableColumnManager/updateDataTableColumns.json"/>

    <youi:toolbar refWidgetId="grid_dataTableColumn">
        <youi:toolbarItem name="save" caption="保存" icon="save" tooltips=""/>
        <youi:toolbarItem name="matchMetadataItems" caption="匹配数据项" icon="link" tooltips=""/>
        <youi:toolbarItem name="showCreateTableSql" caption="建表语句" icon="config" tooltips=""/>
        <youi:toolbarItem name="refreshColumns" caption="刷新" icon="reload" tooltips=""/>
    </youi:toolbar>

    <youi:grid id="grid_dataTableColumn" editable="true" reset="NOT" query="NOT"
               showFooter="false" pageSize="200" showCheckbox="true"
               removeSrc="/metadataServices/services/dataTableColumnManager/removeDataTableColumn.json" idKeys="id"
               src="/metadataServices/services/dataTableColumnManager/getPagerDataTableColumns.json">
        <youi:fieldLayout>
            <youi:fieldLabel property="dataResourceId" caption="数据资源" defaultValue="${param.dataResourceId}"/>
            <youi:fieldLabel property="tableName" caption="表名" defaultValue="${param.tableName}"/>
        </youi:fieldLayout>

        <youi:gridCol editor="fieldText" width="35%" property="columnCaption" nowrap="false" caption="中文名"/>
        <youi:gridCol editor="fieldMatching" width="40%" property="dataItemId" nowrap="false" caption="数据项"/>
        <youi:gridCol width="15%" property="columnName" caption="列名"/>
        <youi:gridCol editor="fieldSelect" width="5%" property="primary" caption="主键" convert="boolean"/>

        <youi:gridCol width="10%" type="button" property="button" caption="操作">
            <youi:button name="search" caption="检索数据项" icon="search"/>
        </youi:gridCol>
    </youi:grid>

    <youi:form id="dialog_search" dialog="true" caption="检索数据项" reset="NOT">
        <youi:fieldLayout columns="1" prefix="search" labelWidths="100">
            <youi:fieldHidden property="id"/>
            <youi:fieldHidden property="columnText"/>
            <youi:fieldAutocomplete notNull="true" property="columnName" code="name" show="text" showProperty="text" src="/metadataServices/services/metaDataItemManager/searchByTerm.json" caption="数据项"/>
        </youi:fieldLayout>
    </youi:form>

    <%-- 检索选择值后设置中文名称到隐藏字段 --%>
    <youi:func name="search_columnName_change" params="value">
        var columnText = ($elem('search_columnName',pageId).find('.textInput').val());
        $elem('search_columnText',pageId).fieldValue(columnText);
    </youi:func>
    <%-- 刷新表格 --%>
    <youi:func name="grid_dataTableColumn_refreshColumns">
        $.youi.messageUtils.confirm('确认刷新?',function(){
            $elem('grid_dataTableColumn',pageId).grid('refresh');
        });
    </youi:func>

    <youi:func name="grid_dataTableColumn_showCreateTableSql">
        $elem('subpage_createTableSql',pageId).subpage('open');
    </youi:func>


    <%-- 匹配数据元 --%>
    <youi:func name="grid_dataTableColumn_matchMetadataItems">
        var texts = [];
        var records = $elem('grid_dataTableColumn',pageId).grid('getRecords'),
            sameText = '';
        $(records).each(function(){
            if(this.columnCaption){
                if($.inArray(this.columnCaption,texts)!=-1){
                    sameText = this.columnCaption;
                }
                texts.push(this.columnCaption);
            }
        });

        if(sameText){
            $.youi.messageUtils.showMessage('重复的列名称'+sameText);
            return;//直接返回
        }

        if(texts.length==0){
            return;
        }

        $.youi.messageUtils.confirm('确认执行匹配?',function(){
            $.youi.pageUtils.doPageFunc('matchingMetaDataByTexts',pageId,null,texts);
        });

    </youi:func>

    <youi:func name="grid_dataTableColumn_search" params="dom,options,record">
        var rowElem = $(dom).parents('tr:first');
        if(!record.id){
            record.id = 'row_'+rowElem.prevAll().length+new Date().getTime();
            rowElem.attr('data-id',record.id);
        }
        $elem('dialog_search',pageId).form('fillRecord',record).dialog('open');
    </youi:func>
    <%-- 文本匹配 --%>
    <youi:func name="matchingMetaDataByTexts" params="texts" urls="matchingMetaDataItemsUrl">
        $.youi.ajaxUtils.ajax({
            url:funcUrls.matchingMetaDataItemsUrl,
            data:$.youi.recordUtils.recordToAjaxParamStr({text:texts}),
            success:function(result){
                var textMatches = {};
                $(result.records).each(function(){
                    textMatches[this.text] = this;
                });

                $elem('grid_dataTableColumn',pageId).find('tr.grow').each(function(){
                    var text = $('td.fieldText:first',this).text(),
                        matchTdElem = $('td.fieldMatching:first',this);
                    if(textMatches[text] && textMatches[text].mappedId && textMatches[text].matchingResults){
                        matchTdElem.data('matchingItem',textMatches[text])
                        .data('value',textMatches[text].mappedId)
                        .attr('data-value',textMatches[text].mappedId)
                        .text(textMatches[text].mappedId+'-'+textMatches[text].matchingResults[0].text);
                    }
                });
            }
        });
    </youi:func>

    <%--  --%>
    <youi:func name="dialog_search_afterSubmit" params="result">
        var record = result.record,id = record.id[0],text = record.columnText[0];

        if(!record.columnName)return;

        var rowElem = $elem('grid_dataTableColumn',pageId).find('tr.grow[data-id='+id+']');
        rowElem.find('td.fieldText:first').text(text).data('value',text);
        rowElem.find('td.fieldMatching:first')
            .data('matchingItem',{id:id,mappedId:record.columnName,matchingResults:[{id:record.columnName,text:text}]})
            .data('value',record.columnName)
            .attr('data-value',record.columnName)
            .text(record.columnName+'-'+text);
    </youi:func>

    <%--  --%>
    <youi:func name="grid_dataTableColumn_save" urls="updateDataTableColumnsUrl">
        var records = $elem('grid_dataTableColumn',pageId).grid('getRecords');

        var paramRecord = {
            dataResourceId:$elem('field_dataResourceId',pageId).fieldValue(),
            tableName:$elem('field_tableName',pageId).fieldValue(),
            columns:records
        };

        $.youi.ajaxUtils.ajax({
            url:funcUrls.updateDataTableColumnsUrl,
            data:$.youi.recordUtils.recordToAjaxParamStr(paramRecord),
            success:function(result){
                $.youi.messageUtils.showMessage('保存成功.');
            }
        });
    </youi:func>

</youi:page>