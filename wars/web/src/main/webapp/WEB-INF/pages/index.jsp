<%@ taglib prefix="youi" uri="http://www.cjyoui.xyz/youi" %>
<%@ page language="java" pageEncoding="UTF-8"%>

<youi:html title="${sysCaption}">
    <%@ include file="/WEB-INF/pages/common/commonScriptAndCss.jsp"%>
    <youi:body decorator="modern">
        <youi:func name="init">
            if(!window.location.hash){
                window.location.hash = '#p:welcome.html';
            }
        </youi:func>
    </youi:body>

</youi:html>
