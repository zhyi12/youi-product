<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/common/include.jsp"%>

<div class="col-sm-12 youi-page-header">

    <div class="pull-left sys-title">
        <h3 style="padding-left:70px;">
            <a data-menu-clear class="page-link" href='' title="个人工作台" data-toggle="tooltip" data-placement="bottom">
                ${sysCaption}
            </a>
        </h3>
    </div>

    <div id="system_h_menu" class="youi-menu v-menu">
        <youi:pageMenu menuProviderName="menuProvider" menuDecorator="modern"/>
    </div>

    <div class="pull-left sys-menu-bar" data-selected="${barMenuId}">

    </div>

    <ul id="main-menu" class="nav navbar-nav pull-right">
        <li class="dropdown hidden-xs">
            <a href="#" title="" class="dropdown-toggle" data-toggle="dropdown">
				<span class="youi-icon icon-user padding-right-small">
					<%=SecurityContextHolder.getContext().getAuthentication().getPrincipal()%>
				</span>
            </a>
            <ul class="dropdown-menu">
				<li class="link-item">
					<a href="#p:page/common/modifyPassword.html" class="page-link"><span
						class="youi-icon icon-password2 padding-right-small">修改密码</span></a></li>

			</ul>
        </li>

        <li class="dropdown hidden-xs" id="changeTheme">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" title="切换页面风格" class>
                <span class="youi-icon icon-list padding-right-small "></span>
            </a>
            <ul class="dropdown-menu">
                <li class="link-item">
                    <a class="link-item" href="javascript:;" data-theme="default">
                        <div style="width: 10px;height: 10px;background-color: #CC9900;display: inline-block"></div>
                        <span class="theme-icon icon-default">
                            默认
                        </span>
                    </a>
                </li>

                <li class="link-item">
                    <a class="link-item" href="javascript:;" data-theme="blue">
                        <div style="width: 10px;height: 10px;background-color: #4884ec;display: inline-block"></div>
                        <span class="theme-icon icon-blue">
                            蓝色
                        </span>

                    </a>
                </li>

                <li class="link-item">
                    <a class="link-item" href="javascript:;" data-theme="green">
                        <div style="width: 10px;height: 10px;background-color: #61d466;display: inline-block"></div>
                        <span class="theme-icon icon-green">
                            绿色
                        </span>
                    </a>
                </li>

                <li class="link-item">
                    <a class="link-item" href="javascript:;" data-theme="red">
                        <div style="width: 10px;height: 10px;background-color: #fc7977;display: inline-block"></div>
                        <span class="theme-icon icon-red">红色</span>
                    </a>
                </li>

                <li class="link-item">
                    <a class="link-item" href="javascript:;" data-theme="yellow">
                        <div style="width: 10px;height: 10px;background-color: #fda040;display: inline-block"></div>
                        <span class="theme-icon icon-yellow">黄色</span>
                    </a>
                </li>

                <li class="link-item">
                    <a class="link-item" href="javascript:;"  data-theme="purple">
                        <div  style="width: 10px;height: 10px;background-color: #d46be6;display: inline-block"></div>
                        <span class="theme-icon icon-purple" >紫色</span>
                    </a>
                </li>

            </ul>
        </li>


        <li  class="dropdown hidden-xs">
            <a class="page-link" tabindex="-1" href="#" onclick="javascript:$.youi.logout()" title="退出系统" >
				<span class="youi-icon icon-off padding-right-small">
					退出
				</span>
            </a>
        </li>
    </ul>

</div>

