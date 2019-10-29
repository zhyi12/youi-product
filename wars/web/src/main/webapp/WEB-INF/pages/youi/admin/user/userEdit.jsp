<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="用户编辑" autoLoadData="${param.userId!=null}"
		   dataSrc="/uaa/services/spUserManager/getSpUser.json?userId=${param.userId}">

	<youi:form id="form_user" panel="false" action="/uaa/services/spUserManager/saveSpUser.json" reset="NOT" submit="NOT"
		idKeys="roleId" >
		<youi:fieldLayout columns="1" styleClass="col-sm-12">
			<youi:fieldHidden property="userId" caption="用户ID"/>
			<youi:fieldText property="username" caption="登录名"/>
			<youi:fieldText property="userCaption" caption="用户描述"/>
		</youi:fieldLayout>
		<div class="col-sm-12" style="border-top:1px solid #ddd;border-bottom:1px solid #ddd;margin-bottom:10px;padding:5px 5px 5px 20px;">
			选择用户角色
			<youi:fieldLayout labelWidths="1" columns="1" styleClass="col-sm-12">
				<youi:fieldTree property="roles" src="/uaa/services/roleManager/getRoles.json"
					code="roleId" show="roleCaption" 
					valueMode="object"
					popup="false" simple="false" check="true" rootText="角色"></youi:fieldTree>
			</youi:fieldLayout>
		</div>
	</youi:form>

	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="result">
		if(result&&result.record){
			$elem('form_user',pageId).form('fillRecord',result.record);
		}
	</youi:func>
	
	<youi:func name="form_user_afterSubmit" params="result">
		$.youi.messageUtils.showMessage('保存用户成功.');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,result.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>