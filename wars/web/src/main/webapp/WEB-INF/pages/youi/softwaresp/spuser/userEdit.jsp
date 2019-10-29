<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="用户编辑" autoLoadData="${param.id!=null}"
		   dataSrc="/uaa/services/spUserManager/getSpUser.json?id=${param.id}">

	<youi:form id="form_user" panel="false" action="/uaa/services/spUserManager/saveSpUser.json" reset="NOT" submit="NOT"
		idKeys="roleId" >
		<youi:fieldLayout columns="1" styleClass="col-sm-12" labelWidths="120">
			<youi:fieldHidden property="id" caption="用户ID"/>
			<youi:fieldText notNull="true" property="username" caption="登录名"/>
			<youi:fieldText notNull="true" property="userCaption" caption="用户描述"/>
			<youi:fieldAutocomplete code="agencyId" show="companyCaption"
					src="/softwarespServices/services/spCompanyManager/getSpCompanys.json" notNull="true" property="agencyId" caption="所属开发商"/>
			<youi:fieldText property="phone" dataType="phone" caption="手机号"/>
		</youi:fieldLayout>
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