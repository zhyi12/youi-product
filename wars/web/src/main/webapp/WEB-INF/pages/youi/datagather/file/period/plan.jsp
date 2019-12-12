<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="固定期文件上传计划">
    <youi:subpage height="150" src="page/${_pagePath}/periodFileEdit.html?id={id}" subpageId="periodFile_edit" caption="修改固定期文件" type="dialog"/>
    <youi:subpage height="150" src="page/${_pagePath}/periodFileEdit.html" subpageId="periodFile_add" caption="新增固定期文件" type="dialog"/>

    <%-- 工具栏 --%>
    <youi:toolbar refWidgetId="grid_periodFile">
        <youi:toolbarItem name="refresh" caption="查询" tooltips="" icon="search"/>
        <youi:toolbarItem name="add" caption="新增" tooltips="" icon="save"/>
    </youi:toolbar>
    <%-- 列表数据 --%>
    <youi:grid id="grid_periodFile" src="/dataFileServices/services/periodFileManager/getPagerPeriodFiles.json"
               removeSrc="/dataFileServices/services/periodFileManager/removePeriodFile.json" idKeys="id"
               showCheckbox="true" reset="NOT" query="NOT" fixedFooter="true">
        <youi:fieldLayout prefix="query">
            <youi:fieldText property="surveyTaskId"  caption="调查方案"/>
            <youi:fieldText property="agencyId"  caption="调查机构"/>
        </youi:fieldLayout>

        <youi:gridCol width="5%" property="areaId"  caption="行政区划"/>
        <youi:gridCol width="15%" property="agencyId"  caption="调查机构"/>
        <youi:gridCol width="5%" property="periodId"  caption="报告期"/>

        <youi:gridCol width="25%" property="reportCode"  caption="报表名称"/>

        <youi:gridCol width="25%" property="fileName"  caption="文件名称"/>

        <youi:gridCol width="15%" property="uploadTime"  caption="上传时间" type="date" format="millis" textFormat="yyyy-MM-dd HH:mm:ss"/>

        <youi:gridCol width="10%" type="button" property="button" caption="操作">
            <youi:button name="edit" caption="编辑"/>
            <youi:button name="split" caption=""/>
            <youi:button name="removeRecord" icon="remove" caption="删除"/>
        </youi:gridCol>

    </youi:grid>

    <!-- Grid编辑按钮动作：打开修改固定期文件弹出页 -->
    <youi:func name="grid_periodFile_edit" params="dom,options,record">
        $elem('subpage_periodFile_edit',pageId).subpage('open',{},'',record,pageId);
    </youi:func>
    <!-- Grid新增按钮动作：打开新增固定期文件弹出页 -->
    <youi:func name="grid_periodFile_add" params="dom,options">
        $elem('subpage_periodFile_add',pageId).subpage('open',{},'',{},pageId);
    </youi:func>
    <!-- 编辑固定期文件的subpage内容变化回调函数 -->
    <youi:func name="subpage_periodFile_edit_change" params="record">
        $elem('grid_periodFile',pageId).grid('refresh');
    </youi:func>
    <!-- 新增固定期文件的subpage内容变化回调函数 -->
    <youi:func name="subpage_periodFile_add_change" params="record">
        $elem('grid_periodFile',pageId).grid('refresh');
    </youi:func>
</youi:page>