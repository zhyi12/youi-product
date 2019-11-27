<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="机构管理">
	<%--  --%>
	<youi:subpage height="150" src="page/${_pagePath}/agencyEdit.html?parentId={parentId}" subpageId="agency_add" caption="新增机构" type="dialog"/>
	<%-- 主键获取机构信息 --%>
	<youi:ajaxUrl name="getAgencyUrl" src="/baseServices/services/agencyManager/getAgency.json"/>
	<%-- --%>
	<youi:xmenu id="xmenu_agency">
		<youi:xmenuItem name="addAgency" caption="新增机构" groups="agency"/>
		<youi:xmenuItem name="refresh" caption="刷新" groups="agency"/>
	</youi:xmenu>
	<%-- --%>
	<youi:customWidget name="page_spliter" widgetName="pageSpliter"
					   styleClass="col-sm-3 page-inner-height">
		<youi:toolbar styleClass="fixed-height" refWidgetId="tree_agency">
			<li class="search">
				<youi:fieldAutocomplete property="search" src=""/>
			</li>
		</youi:toolbar>
		<%-- 机构树 --%>
		<youi:tree id="tree_agency" styleClass="hide-root col-sm-12 no-padding" hideRoot="true" rootText="" xmenu="xmenu_agency"
				   iteratorSrc="/baseServices/services/agencyManager/getAgencyChildTree.json" iteratorParam="id">
		</youi:tree>
	</youi:customWidget>
	<%--  --%>
	<youi:form id="form_agency" panel="false" idKeys="id" action="/baseServices/services/agencyManager/saveAgency.json"
			   styleClass="no-padding page-inner-height col-sm-9 page-spliter-right" reset="NOT" submit="NOT">
		<youi:toolbar styleClass="fixed-height" refWidgetId="form_agency">
			<youi:toolbarItem name="submit" caption="保存" tooltips="" icon="save" groups="agency"/>
		</youi:toolbar>
		<youi:fieldLayout columns="1" labelWidths="100" styleClass="top-padding">
			<youi:fieldHidden property="id"  caption="i18n.agency.id"/>
			<youi:fieldText notNull="true" property="caption"  caption="机构名称"/>
			<youi:fieldText property="areaId"  caption="行政区划"/>
			<youi:fieldHidden property="parentId"  caption="父节点"/>
			<youi:fieldText property="parentText"  caption="父节点"/>
			<youi:fieldText property="num"  caption="序号"/>
		</youi:fieldLayout>
	</youi:form>
	<%-- 打开新增机构页面 --%>
	<youi:func name="tree_agency_xmenu_addAgency" params="treeNode">
		var id = treeNode.data('id');
		$elem('subpage_agency_add',pageId).subpage('open',{},{},{parentId:id});
	</youi:func>
	<%-- 刷新节点 --%>
	<youi:func name="tree_agency_xmenu_refresh" params="treeNode">
		treeNode.removeClass('loaded').removeClass('expanded').find('>ul').empty();
		treeNode.find('>.tree-trigger').trigger('click');
	</youi:func>

	<%-- 机构树节点选中回调  --%>
	<youi:func name="tree_agency_select" params="treeNode" urls="getAgencyUrl">
		var formElem = $elem('form_agency',pageId),id = treeNode.data('id'),parentNode = treeNode.parents('.treeNode:first'),
			parentText = parentNode.hasClass('root')?'':$.youi.treeUtils.getNodeText(parentNode);
		formElem.form('fillRecord',{id:id});
		formElem.form('find',funcUrls.getAgencyUrl+'?id='+id,function(){
			$elem('field_parentText',pageId).fieldValue(parentText);
		});
		formElem.trigger('activeTools',{groups:['agency']});
	</youi:func>

	<%-- 添加下级机构节点 --%>
	<youi:func name="addChildAgencyNode" params="parentNode,record">
		$elem('tree_agency',pageId).tree('addNode',record.id,record.caption,record,{group:'agency'});
	</youi:func>
	<%-- 子页面保存成功后回调 --%>
	<youi:func name="subpage_agency_add_change" params="record">
		var parentNode = $elem('tree_agency',pageId).find('.treeNode.agency[data-id='+record.parentId+']');
		if(parentNode.length){
			$.youi.pageUtils.doPageFunc('addChildAgencyNode',pageId,null,parentNode,record);
		}
	</youi:func>
	<%-- 修改机构后回调，同步更新树名称等信息 --%>
	<youi:func name="form_agency_afterSubmit" params="result">
		var treeNode = $elem('tree_agency',pageId).find('.treeNode.agency[data-id='+result.record.id+']');
		treeNode.find('>span>a').text(result.record.caption);
	</youi:func>

</youi:page>