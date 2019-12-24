<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="分组编辑" autoLoadData="${param.id!=null}"
	dataSrc="/metadataServices/services/metaCategoryManager/getMetaCategory.json?id=${param.id}">

	<youi:ajaxUrl name="getMetaCategoryUrl" src="/metadataServices/services/metaCategoryManager/getMetaCategory.json"/>

	<youi:form id="form_metaCategory" panel="false" submit="NOT" reset="NOT" action="/metadataServices/services/metaCategoryManager/saveMetaCategory.json"
		idKeys="id">
		<youi:fieldLayout columns="1" labelWidths="100">
			<youi:fieldText notNull="true" property="id"  caption="分组编码"/>
			<youi:fieldText notNull="true" property="text"  caption="分组名称"/>
			<youi:fieldText property="expression"  caption="分组"/>
		</youi:fieldLayout>
	</youi:form>
	
	<youi:button name="submit" caption="提交" submitProperty="submit" submitValue="1"/>
	
	<youi:func name="init" params="results">
		if(results&&results.record){
			$elem('form_metaCategory',pageId).form('fillRecord',results.record);
		}
	</youi:func>
	
	<youi:func name="form_metaCategory_afterSubmit" params="results">
		$.youi.messageUtils.showMessage('保存分类属性成功.');
		$elem('form_metaCategory',pageId).form('reset');
		$.youi.pageUtils.closeAndRefreshSubpage(pageId,results.record);//关闭并刷新主页面的subpage组件
	</youi:func>

	<%--  检查是否已经存在	--%>
	<youi:func name="field_id_change" urls="getMetaCategoryUrl" params="value">
		if(!value)return;
		$.youi.ajaxUtils.ajax({
			url:funcUrls.getMetaCategoryUrl,
			data:'id='+value,
			notShowLoading:true,
			success:function(result){
				if(result.record && result.record.id==value){
					$.youi.messageUtils.showMessage(value+'已经存在');
					$elem('field_id',pageId).fieldClear();
				}
			}
		});
	</youi:func>
</youi:page>