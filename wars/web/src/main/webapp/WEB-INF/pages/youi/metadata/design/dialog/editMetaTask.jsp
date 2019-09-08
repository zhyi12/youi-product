<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="">
   <youi:form id="form_metaPlan" panel="false" reset="NOT" submit="NOT"
              action="">

      <youi:fieldLayout labelWidths="100,100">
         <youi:fieldHidden property="metaObjectName" defaultValue="metaTask" caption="元数据名"/>
         <youi:fieldHidden property="refMetaObjectId"  caption="关联元数据"/>
         <youi:fieldText notNull="true" column="2" property="text" caption="方案名称"/>
         <youi:fieldText notNull="true" column="2" property="num" caption="排序编号"/>
         <youi:fieldSelect property="taskGroupId" caption="所属方案组" column="2">

         </youi:fieldSelect>
      </youi:fieldLayout>

   </youi:form>

   <youi:button name="submit" caption="确定" submitProperty="submit" submitValue="1"/>
</youi:page>
