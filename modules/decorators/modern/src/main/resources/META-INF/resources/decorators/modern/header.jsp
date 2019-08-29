<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:script src="/decorators/modern/app.js"></youi:script>
<youi:script src="/decorators/modern/layout.js"></youi:script>

<youi:style href="/decorators/modern/layout.css"></youi:style>
<!-- 主题样式 -->
<%@ include file="/WEB-INF/pages/common/header.jsp"%>

<script type="text/javascript">
	$(function(){
		$('body',document).app({

		});
	});
</script>
