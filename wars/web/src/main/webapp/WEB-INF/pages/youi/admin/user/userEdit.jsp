<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="用户编辑">

	<youi:ajaxUrl name="getUserConfigsUrl" src="/uaa/services/userConfigManager/getUserConfigs.json"/>

	<youi:form id="form_user" panel="false" submit="NOT" reset="NOT" action="/uaa/services/userConfigManager/saveUser.json"
		idKeys="userId">
		<youi:fieldLayout labelWidths="120" columns="1" prefix="query">
			<youi:fieldHidden property="userId" defaultValue="${param.userId}"/>
			<youi:fieldHidden property="editType" defaultValue="${param.editType}"/>
			<youi:fieldText property="username" notNull="true" caption="用户名" defaultValue="${param.username}"/>
			<youi:fieldText property="userCaption" caption="用户名称" defaultValue="${param.userCaption}"/>
			<youi:fieldText property="agencyId" notNull="true" caption="所属机构" defaultValue="${param.agencyId}"/>
			<youi:fieldText property="areaId" notNull="true" caption="所属区域" defaultValue="${param.areaId}"/>
			<youi:fieldSelect property="professions" caption="所属专业" code="professionId" show="professionCaption" multiple="true" readonly="true"
							  src="/metadataServices/services/professionManager/getAllProfessions.json" authCode="sysAdminRole"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>

	<youi:func name="init" urls="getUserConfigsUrl">
		var username = $elem('query_username',pageId).fieldValue();
		//根据用户名获取用户配置
		$.youi.ajaxUtils.ajax({
			url:funcUrls.getUserConfigsUrl,
			type:'POST',
			data:$.youi.recordUtils.recordToAjaxParamStr({username:[username]}),
			success:function(result) {
				$elem('query_professions',pageId).fieldValue(result.records[0].params.professions);
			}
		});
		//编辑时隐藏用户名
		if ($.youi.stringUtils.notEmpty($elem('query_editType',pageId).fieldValue())
			&& $elem('query_editType',pageId).fieldValue() == 'edit') {
			$elem('form_user',pageId).parents('.modal-dialog:first').find('.youi-field[data-property="username"] input:first').attr('readOnly', 'true');
		} else {
			$elem('form_user',pageId).parents('.modal-dialog:first').find('.youi-field[data-property="username"] input:first').removeAttr('readOnly');
		}
		//实施账号才能编辑专业属性
		if ($.inArray('ROLE_ADMIN',$.stats.userDetails.roles) != -1){
			$elem('query_professions',pageId).find('>a').attr('data-toggle','dropdown');
			$elem('query_professions',pageId).removeClass('readonly');
			$elem('query_professions',pageId).find('>ul').css('display','');
		}
	</youi:func>

	<youi:func name="form_user_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存用户成功.');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>
</youi:page>