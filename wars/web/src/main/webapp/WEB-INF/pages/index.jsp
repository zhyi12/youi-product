<%@ taglib prefix="youi" uri="http://www.cjyoui.xyz/youi" %>
<%@ page language="java" pageEncoding="UTF-8"%>

首页
<form action="/uaa/oauth/client" method="get" target="_blank">
    <input type="submit" value="测试获取登录数据"/>
</form>

<form action="/logout" method="post">
    <input type="submit" value="测试退出"/>
</form>