<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<%@ page import="org.springframework.core.io.Resource" %>
<%@ page import="org.springframework.util.FileCopyUtils" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="application/json" %>
<%

    String json = "{}";
    String path = request.getParameter("path");
    path = "WEB-INF/datas/"+path+".json";

    try{
        Resource resource = RequestContextUtils.findWebApplicationContext(request).getResource(path);
        byte[] bytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
        response.getWriter().write(new String(bytes));
    }catch (Exception err){
        out.println("{\"message\":{\"\":\""+err.getMessage()+"\"}}");
    }
%>
