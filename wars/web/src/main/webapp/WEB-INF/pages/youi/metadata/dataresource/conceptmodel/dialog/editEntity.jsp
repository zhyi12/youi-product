<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page pageId="${param.dialogId}">

    <youi:ajaxUrl name="matchingMetaDataItemsUrl" src="/metadataServices/services/metaDataItemManager/matchingMetaDataItems.json"/>

    <youi:form id="form" panel="false" submit="NOT" reset="NOT" styleClass="no-padding"
               action="">
        <youi:tabs id="tabs">
            <youi:tabItem id="general" caption="基本信息">
                <youi:fieldLayout columns="1" labelWidths="100" styleClass="top-padding">
                    <youi:fieldHidden property="dataResourceId" defaultValue="${param.dataResourceId}"/>
                    <youi:fieldHidden property="id"/>
                    <youi:fieldHidden property="tableId"/>
                    <youi:fieldText notNull="true" property="tableName" caption="英文表名"/>
                    <youi:fieldText notNull="true" property="caption" caption="中文表名"/>
                    <youi:fieldText notNull="true" property="entityCode" caption="实体编号"/>
                    <youi:fieldArea property="commont" caption="注释" rows="10"/>
                </youi:fieldLayout>
            </youi:tabItem>
            <youi:tabItem id="attributes" caption="实体属性">
                <youi:fieldGrid property="attributes">
                    <youi:grid id="grid_dataTableColumn" editable="true" reset="NOT" query="NOT"
                               showFooter="false" pageSize="2000" showCheckbox="true" load="false"
                               idKeys="id">
                        <youi:gridCol editor="fieldText" width="45%" property="columnCaption" nowrap="false" caption="中文名"/>
                        <youi:gridCol editor="fieldMatching" width="45%" property="dataItemId" nowrap="false" caption="数据项"/>
                        <youi:gridCol editor="fieldSelect" width="10%" property="primary" caption="主键" convert="boolean"/>
                    </youi:grid>
                </youi:fieldGrid>
            </youi:tabItem>
        </youi:tabs>

    </youi:form>

    <youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>

    <%-- init 触发主页面subpage的组件的initPageData事件，并设置页面异步加载完成后的回调函数 --%>
    <youi:func name="init">
        $elem('grid_dataTableColumn',pageId).find('.editor-tools:first').append('<span data-command="gridCommand" data-name="matchMetadataItems" title="匹配数据项" class="youi-icon icon-link"></span>');
        $('#'+pageId).trigger('initPageData',{callback:'P_'+pageId+'_initPageData'});
    </youi:func>

    <%-- 页面加载完成，默认的form表单数据设置后的操作 --%>
    <youi:func name="initPageData" params="record">

    </youi:func>

    <youi:func name="grid_dataTableColumn_search" params="dom,options,record">
        var rowElem = $(dom).parents('tr:first');
        if(!record.id){
            record.id = 'row_'+rowElem.prevAll().length+new Date().getTime();
            rowElem.attr('data-id',record.id);
        }
        $elem('dialog_search',pageId).form('fillRecord',record).dialog('open');
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

</youi:page>