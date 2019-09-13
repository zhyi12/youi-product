<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--

--%>
<youi:page caption="" pageId="${param.pageId}">

   <%-- 约定id为form的表单，配合designer组件使用 --%>
   <youi:form id="form" panel="false" reset="NOT" submit="NOT"
              action="/metadataServices/services/metaObjectNodeManager/createMetaObjectNode.json">
      <youi:fieldLayout labelWidths="100,100">
         <youi:fieldHidden property="metaObjectName" defaultValue="metaTask" caption="元数据名"/>
         <youi:fieldHidden property="refMetaObjectId"  caption="关联元数据"/>
         <youi:fieldHidden property="parentId" caption="父节点ID"/>
         <youi:fieldText notNull="true" column="2" property="text" caption="方案名称"/>
         <youi:fieldText notNull="true" column="2" property="code" caption="方案代码"/>
         <youi:fieldText column="2" property="num" caption="顺序号"/>
      </youi:fieldLayout>
   </youi:form>

   <youi:button name="submit" caption="确定" submitProperty="submit" submitValue="1"/>

   <%-- init 触发主页面subpage的组件的initPageData事件，并设置页面异步加载完成后的回调函数 --%>
   <youi:func name="init" >
      $('#'+pageId).trigger('initPageData',{callback:'P_'+pageId+'_initPageData'});
   </youi:func>

   <%-- 页面加载完成，默认的form表单数据设置后的操作 --%>
   <youi:func name="initPageData" params="record">
      console.log(record);
   </youi:func>
</youi:page>
