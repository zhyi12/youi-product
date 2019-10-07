
<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- ***********************************************************************************************
*   @页面说明:管理jsp page页面请求
*       1、左侧展示从jsp文件读取的页面请求列表，右边展示存储在数据库中的页面请求列表
*       2、通过"页面扫描"按钮扫描web应用中的包含youi:page标签的jsp页面
*       3、通过"同步页面"按钮加载并同步全部的pageUrl的子页面、授权元素、页面数据等信息
*
*   @author         zhouyi
*   @since          3.0.0
*   @createTime     2018-05-21
*   @modify
*************************************************************************************************--%>

<youi:page caption="页面管理">
    <%-- 获取全部web page请求url --%>
    <youi:ajaxUrl name="getAllWebPages" src="/page/getAllWebPages.json"/>

    <%-- 左边栏使用pageSpliter组件 --%>
    <youi:customWidget name="page_spliter" widgetName="pageSpliter" styleClass="col-sm-3 page-inner-height">
        <youi:toolbar styleClass="fixed-height">
            <youi:toolbarItem name="scanAllWebPages" caption="页面扫描" tooltips="" icon="saomiao"/>
            <youi:toolbarItem name="syncAllWebPages" caption="同步页面" tooltips="" icon="play"/>
        </youi:toolbar>
        <youi:grid id="scaneed_webPages" caption="扫描到的页面" query="NOT" reset="NOT" showFooter="false"
                   styleClass="auto-height col-sm-12 no-padding">
            <youi:gridCol width="10%" align="center" property="button" caption="操作" type="button">
                <youi:button name="addToPage" caption="" icon="plus"/>
            </youi:gridCol>
            <youi:gridCol property="pageUrl" caption="从jsp文件扫描出的请求路径"/>
        </youi:grid>
    </youi:customWidget>
    <%-- 右边栏使用page-spliter-right 响应pageSpliter组件的位置调整 --%>
    <youi:grid id="grid_confided_page" idKeys="pageId" reset="NOT"
               src="/operatorServices/services/pageManager/getPagerPages.json"
               removeSrc="/operatorServices/services/pageManager/removePage.json"
               caption="受管页面" styleClass="toolbar-buttons col-sm-9 page-inner-height page-spliter-right ">
        <youi:gridCol width="20%" property="pageCaption" caption="页面描述"/>
        <youi:gridCol width="70%" nowrap="false" property="pageUrl" caption="页面路径"/>

        <youi:gridCol width="10%" align="center" property="button" caption="操作" type="button">
            <youi:button name="edit" caption="" icon="rename"/>
            <youi:button name="removeRecord" caption="" icon="delete-slender"/>
        </youi:gridCol>

        <youi:gridQueryButton name="add" icon="add" caption="添加"/>
    </youi:grid>
    <%-- 弹出表单：编辑页面信息 --%>
    <youi:form id="form_page" dialog="true" reset="NOT"
               caption="编辑页面" action="/operatorServices/services/pageManager/savePage.json">
        <youi:fieldLayout columns="1" labelWidths="100">
            <youi:fieldHidden property="pageId"/>
            <youi:fieldText notNull="true" property="pageCaption" caption="页面描述"/>
            <youi:fieldText readonly="true" property="pageUrl" caption="页面路径"/>
            <youi:fieldText notNull="true" property="pageName" caption="页面名"/>

            <youi:fieldCustom custom="fieldList" customOptions="{valueMode:'array',readonly:true}" property="dataUrls" caption="数据"/>
            <youi:fieldCustom custom="fieldList" customOptions="{valueMode:'array',readonly:true}" property="subPages" caption="子页面"/>
            <youi:fieldCustom custom="fieldList" customOptions="{valueMode:'array',readonly:true}" property="elementAuths" caption="授权元素"/>

        </youi:fieldLayout>
    </youi:form>

    <%-- 页面初始化函数 --%>
    <youi:func name="init" urls="getAllWebPages">
        $.youi.ajaxUtils.ajax({
            url:funcUrls.getAllWebPages,
            success:function(results){
                $elem('scaneed_webPages',pageId).grid('parseRecords',results);
            }
        });
    </youi:func>

    <youi:func name="scaneed_webPages_addToPage" params="dom,commandOption,record">
        $elem('form_page',pageId).form('clear').form('fillRecord',record).dialog('open');
    </youi:func>

    <youi:func name="grid_confided_page_edit" params="dom,commandOption,record">
        $elem('form_page',pageId).form('clear').form('fillRecord',record).dialog('open');
    </youi:func>

    <%-- 页面销毁函数 --%>
    <youi:func name="destory">

    </youi:func>

</youi:page>