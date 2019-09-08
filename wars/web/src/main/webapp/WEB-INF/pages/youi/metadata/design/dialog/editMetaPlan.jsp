<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="编辑制度">

    <youi:form id="form_metaPlan" panel="false" reset="NOT" submit="NOT"
               action="">

        <youi:fieldLayout labelWidths="100,100">
            <youi:fieldHidden property="metaObjectName" defaultValue="metaPlan" caption="元数据名"/>
            <youi:fieldHidden property="refMetaObjectId"  caption="关联元数据"/>
            <youi:fieldText notNull="true" column="2" property="text" caption="制度名称"/>
            <youi:fieldCalendar notNull="true" property="begDate" caption="启用日期"/>
            <youi:fieldCalendar notNull="true" property="endDate" caption="过期日期"/>
            <youi:fieldText notNull="true" column="2" property="version" caption="制度版本"/>
            <youi:fieldText notNull="true" column="2" property="num" caption="排序编号"/>

            <youi:fieldTree property="agencyId" caption="所属机构" column="2">

            </youi:fieldTree>
        </youi:fieldLayout>

    </youi:form>

    <youi:button name="submit" caption="确定" submitProperty="submit" submitValue="1"/>
</youi:page>
