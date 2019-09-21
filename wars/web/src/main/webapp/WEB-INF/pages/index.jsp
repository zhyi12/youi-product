<%@ taglib prefix="youi" uri="http://www.cjyoui.xyz/youi" %>
<%@ page language="java" pageEncoding="UTF-8"%>

<youi:html title="${sysCaption}">
    <%@ include file="/WEB-INF/pages/common/commonScriptAndCss.jsp"%>
    <youi:body decorator="modern">

        <youi:ajaxUrl name="themeUrl" src="/theme/{theme}.json"/>
        <youi:func name="init">

            $('.youi-page-header:first').on('click','a[data-theme]',function(event){
                var theme = $(event.currentTarget).data('theme');
                //后台记录theme变更
                $.youi.ajaxUtils.ajax({
                    url:$.youi.recordUtils.replaceByRecord($.youi.serverConfig.urls.themeUrl,{theme:theme}),
                    notShowLoading:true,
                });
                //前台重写theme
                var themeHref = $.youi.serverConfig.contextPath+'asserts/css/themes/'+theme+'/main.css';

                var themeElem = $('link[href="'+themeHref+'"]');
                if(!themeElem.length){
                    themeElem = $('<link href="/asserts/css/themes/'+theme+'/main.css" type="text/css" rel="stylesheet"/>').appendTo($('body',document));
                }
                $('body',document).append(themeElem);
            });


            if(!window.location.hash){
                window.location.hash = '#p:welcome.html';
            }
        </youi:func>
    </youi:body>

</youi:html>
