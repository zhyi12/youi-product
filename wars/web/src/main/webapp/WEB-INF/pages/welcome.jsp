<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="首页" styleClass="welcome-page">
    ${theme}
    <form action="/logout" method="post">
        <input type="submit" name="submit" value="退出">
    </form>

    <youi:form id="form_upload" action="/testUpload.json">

        <youi:fieldCustom property="file" custom="fieldPlupload" notNull="true"
                          customOptions="{limits:1,mineTypeCaption:'',mineTypeExtensions:'xls,xlsx,csv,dbf'}" caption="选择文件"/>

    </youi:form>
</youi:page>