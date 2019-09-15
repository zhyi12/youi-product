<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="" autoLoadData="${param.id!=null}"
           dataSrc="/metadataServices/services/dataQueryManager/getDataQuery.json?id=${param.id}">

    <youi:ajaxUrl name="parseDataQueryUrl" src="/metadataServices/services/dataQueryService/parseDataQuery.json"/>
    <youi:ajaxUrl name="keyTreeUrl" src="/metadataServices/services/dataResourceService/getUserDataResourceTree.json"/>

    <youi:xmenu id="xmenu_data">
        <youi:xmenuItem name="buildSqlSelect" caption="生成select语句" groups="data-table"/>
    </youi:xmenu>

    <youi:form id="form_dataQuery" panel="false" submit="NOT" reset="NOT"
               action="/metadataServices/services/dataQueryManager/saveDataQuery.json"
               idKeys="id" styleClass="no-padding">

        <youi:tabs id="tabs_sql">
            <youi:tabItem id="sqlEditor" caption="SQL">
                <youi:fieldLayout columns="1" labelWidths="0">
                    <youi:fieldHidden property="id"  caption="i18n.dataQuery.id"/>
                    <youi:fieldHidden property="name"  caption="name"/>
                    <youi:fieldHidden property="showTotalCount"  caption="showTotalCount"/>
                    <youi:fieldHidden property="caption"  caption="caption"/>
                    <youi:fieldHidden property="sqlExpression" caption=""/>
                </youi:fieldLayout>
                <youi:customWidget widgetName="expressionEditor" name="sqlExpression"
                                   urls="keyTreeUrl" refs="xmenu_data" data-treeNodeGroups="data-table,table-column"
                                   data-itemProps="id,catalog,schema,table-name,column-name,group"
                                   data-itemCaption="数据资源" data-rootText="资源树">

                </youi:customWidget>
            </youi:tabItem>
            <youi:tabItem id="queryConditions" caption="查询条件" styleClass="sqlChange">
                <youi:fieldGrid property="queryColumns" caption="1">
                    <youi:grid editable="true" query="NOT" reset="NOT" showCheckbox="true" showFooter="false">
                        <youi:gridCol width="25%" editor="fieldText" property="columnName" caption="列名"/>
                        <youi:gridCol width="25%" editor="fieldText" property="property" caption="属性名"/>
                        <youi:gridCol width="50%" editor="fieldText" property="text" caption="中文名"/>
                    </youi:grid>
                </youi:fieldGrid>
            </youi:tabItem>
            <youi:tabItem id="queryParams" caption="参数">
                <youi:fieldGrid property="queryParams">
                    <youi:grid editable="true" query="NOT" reset="NOT" showCheckbox="true" showFooter="false" idKeys="id">
                        <youi:gridCol width="10%" editor="fieldText" property="name" caption="参数序号"/>
                        <youi:gridCol width="30%" editor="fieldText" property="property" caption="参数名"/>
                        <youi:gridCol width="40%" editor="fieldText" property="text" caption="中文名"/>
                        <youi:gridCol width="20%" editor="fieldText" property="convert" caption="代码集"/>
                    </youi:grid>
                </youi:fieldGrid>
            </youi:tabItem>
        </youi:tabs>
    </youi:form>

    <youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>

    <youi:func name="init" params="results">
        if(results&&results.record){
            $elem('form_dataQuery',pageId).form('fillRecord',results.record);
        }
        $elem('tabs_sql',pageId).on('shown.bs.tab',function(event){
            var tabId = $(event.target).data('id');
            if('sqlEditor'!=tabId){
                $.youi.pageUtils.doPageFunc('parseQuerySql',pageId);
            }
            return false;
        });

        $elem('sqlExpression',pageId).expressionEditor('parseExpressionItems',{
            operator:{
                text:'操作符',
                items:[
                    {id:'select',text:'SELECT',expression:'SELECT    FROM     WHERE  '},
                    {id:'leftJoin',text:'LEFT JOIN',expression:' LEFT JOIN ',column:12},
                    {id:'empty',text:'空值',expression:"'_EMPTY'",column:12},
                    {id:'nullableCondition',text:'可空条件',expression:"(?='_EMPTY' or  =?)",column:12},
                ]
            }
        });
    </youi:func>

    <youi:func name="parseQuerySql" urls="parseDataQueryUrl">
        var formRecord = $elem('form_dataQuery',pageId).form('getFormRecord');
        $.youi.ajaxUtils.ajax({
            url:funcUrls.parseDataQueryUrl,
            data:$.youi.recordUtils.recordToAjaxParamStr(formRecord),
            notShowLoading:true,
            success:function(result){
                $elem('form_dataQuery',pageId).form('fillRecord',result.record);
            }
        });
    </youi:func>

    <%-- 生成表查询的sql语句 --%>
    <youi:func name="sqlExpressiontree_xmenu_buildSqlSelect" params="treeNode">
        var items = [];
        treeNode.find('.treeNode.table-column').each(function(){
            var columnNode = $(this);
            items.push($.extend({},columnNode.data(),{text:$.youi.treeUtils.getNodeText(columnNode)}));
        });
        items.push($.extend({},treeNode.data(),{text:$.youi.treeUtils.getNodeText(treeNode)}));
        var itemLength = items.length;
        $elem('sqlExpression',pageId).expressionEditor('insertItems',items,function(itemHtml,index){
            if(index==0){
                return 'SELECT&nbsp;' + itemHtml;
            }else if(index == itemLength-1){
                return '&nbsp;FROM '+itemHtml;
            }
            return ','+itemHtml;
        });
    </youi:func>

    <youi:func name="form_dataQuery_afterSubmit" params="results">
        $.youi.messageUtils.showMessage('保存数据查询成功.');
        $elem('form_dataQuery',pageId).form('reset');
        $.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
    </youi:func>

</youi:page>