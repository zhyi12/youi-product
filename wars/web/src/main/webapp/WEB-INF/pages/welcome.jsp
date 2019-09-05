<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="首页" styleClass="welcome-page" dataSrc="/exchangeServices/tcp/user/getUser.json">
    <form action="/logout" method="post">
        <input type="submit" name="submit" value="退出">
    </form>
</youi:page>