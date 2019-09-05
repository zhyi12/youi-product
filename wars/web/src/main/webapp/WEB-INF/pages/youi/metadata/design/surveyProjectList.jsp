<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="统计项目管理">

    <youi:ajaxUrl name="removeSurveyProjectUrl" src="/metadataServices/services/surveyProjectManager/removeSurveyProject.json"/>
    <youi:ajaxUrl name="importDataUrl" src="/metadataServices/services/tjMetaObjectImportManager/importTjMetaObject.json"/>
    <youi:ajaxUrl name="importMetaObjectExcel" src="/metadataServices/services/tjMetaObjectExcelManager/importMetaObjectByExcel.json"/>

    <youi:subpage subpageId="survey_project_design" caption="设计统计项目"
                  src="page/${_pagePath}/surveyProjectDesigner.html?surveyProjectId={id}&surveyProjectCaption={metaObjectCaption}" type="page"/>

    <youi:subpage src="page/${_pagePath}/surveyIndicatorManager.html?surveyProjectId={id}"
                  subpageId="survey_indicator_manager" caption="指标管理" type="page" />

    <youi:grid id="grid_survey_project" src="/metadataServices/services/surveyProjectManager/getPagerSurveyProject.json" idKeys="id"
               showNum="true" styleClass="grid-query-inline" showConvertDropdown="false">
        <youi:fieldLayout prefix="query" labelWidths="120" columns="1" styleClass="col-sm-10" >
            <youi:fieldHidden property="metaObjectName" defaultValue="surveyProject"/>
            <youi:fieldText operator="LIKE" property="metaObjectCaption"  caption="统计项目名称"/>
        </youi:fieldLayout>


        <youi:gridCol width="60%"  property="metaObjectCaption"  caption="统计项目名称" orderBy="asc" nowrap="false"/>
        <youi:gridCol width="15%" property="version"  caption="版本号"/>
        <youi:gridCol width="15%" property="agencyId"  caption="机构" convert="tjAgency"/>

        <youi:gridCol width="10%" type="button" property="button" caption="操作">
            <youi:button name="editProject" caption="编辑" icon="rename"/>
            <youi:button name="split"/>
            <youi:button name="designProject" caption="设计" icon="design"/>
            <youi:button name="split"/>
            <youi:button name="manageIndicator" caption="指标管理" icon="index-management1"/>
            <youi:button name="split"/>
            <youi:button name="removeProject" caption="删除" icon="delete"/>
        </youi:gridCol>

        <youi:button name="addProject" caption="添加项目" icon="plus"/>
        <youi:button name="importMeta" caption="导入excel" icon="excel-import"/>
        <youi:button name="importDDIXml" caption="导入DDI" icon="import"/>
    </youi:grid>

    <youi:form id="form_survey_project" reset="NOT" dialog="true" submit="NOT" close="NOT"
               action="/metadataServices/services/surveyProjectManager/saveSurveyProject.json">
        <youi:fieldLayout columns="1">
            <youi:fieldHidden property="id" caption="统计项目id"/>
            <youi:fieldHidden property="metaObjectName" caption="统计项目元数据对象name" defaultValue="surveyProject"/>
        </youi:fieldLayout>
        <youi:fieldLayout columns="1" prefix="addProject">
            <youi:fieldText property="metaObjectCaption" caption="名称"/>
            <youi:fieldText property="version" caption="版本"/>
        </youi:fieldLayout>
        <youi:button name="submit" caption="确认" styleClass="test"/>
        <youi:button name="close" caption="关闭"/>
    </youi:form>

    <!-- 导入excel文件 -->
    <youi:form id="form_uploadExcel" caption="上传Excel文件"  dialog="true"
               reset="NOT" submit="NOT"  close="NOT" action="/metadataServices/services/tjMetaObjectExcelManager/importMetaObjectByExcel.json">
        <youi:fieldLayout columns="1" prefix="upload" labelWidths="120" height="80">
            <youi:fieldCustom notNull="true" custom="fieldPlupload"
                              customOptions="{limits:1,mineTypeCaption:'',mineTypeExtensions:'xls,xlsx,csv,dbf'}"
                              property="excelFile" caption="元数据Excel"/>
        </youi:fieldLayout>

        <youi:button name="submit" submitProperty="submit" submitValue="1" caption="提交"/>
        <youi:button name="close" caption="关闭"/>
    </youi:form>

    <!-- 导入DDI xml form-->
    <youi:form id="form_upload_ddi_xml" caption="上传DDI XML文件" dialog="true"
               reset="NOT" submit="NOT" close="NOT"
               action="/metadataServices/services/tjMetaObjectImportManager/importTjMetaObject.json">
        <youi:fieldLayout columns="1" prefix="ddiXmlUpload" labelWidths="140" height="80">
            <youi:fieldCustom notNull="true" custom="fieldPlupload"
                              customOptions="{limits:1,mineTypeCaption:'',mineTypeExtensions:'xml'}"
                              property="fileStorePath" caption="元数据DDI XML"/>
        </youi:fieldLayout>
        <youi:button name="submit" submitProperty="submit" submitValue="1" caption="提交"/>
        <youi:button name="close" caption="关闭"/>

    </youi:form>


    <!-- 导入DDI xml-->
    <youi:func name="grid_survey_project_importDDIXml" urls="importDataUrl">
        $elem("form_upload_ddi_xml",pageId).form("reset").dialog("open");
    </youi:func>

    <!-- 导入DDI xml完成后回调 -->
    <youi:func name="form_upload_ddi_xml_afterSubmit">
        $.youi.messageUtils.showMessage("导入完成！");
        $elem("grid_survey_project",pageId).grid("refresh");
    </youi:func>

    <!-- 添加统计项目 -->
    <youi:func name="grid_survey_project_addProject" params="dom,options">
        $elem("form_survey_project",pageId).form("reset").dialog("open",{title: "添加统计项目"});
    </youi:func>

    <!-- 导入元数据 -->
    <youi:func name="grid_survey_project_importMeta">
        $elem("form_uploadExcel",pageId).form("clear").dialog("open",{title: "导入元数据"});
    </youi:func>

    <!-- 编辑统计项目 -->
    <youi:func name="grid_survey_project_editProject" params="dom,options,record">
        $elem("form_survey_project",pageId).form("reset").form("fillRecord",record).dialog("open",{title: "编辑统计项目"});
    </youi:func>

    <!-- 设计统计项目 -->
    <youi:func name="grid_survey_project_designProject" params="dom,options,record">
        $elem("subpage_survey_project_design",pageId).subpage("open",{},"",{id:record.id,metaObjectCaption:encodeURIComponent(record.metaObjectCaption)},pageId);
    </youi:func>
    <!--指标管理页面-->
    <youi:func name="grid_survey_project_manageIndicator" params="dom,options,record">
        $elem("subpage_survey_indicator_manager",pageId).subpage("open",{},"",record,pageId);
    </youi:func>

    <!-- 删除统计项目 -->
    <youi:func name="grid_survey_project_removeProject" params="dom,options,record" urls="removeSurveyProjectUrl">
        $.youi.messageUtils.confirm("确认删除统计项目？",function(){
            $.youi.ajaxUtils.ajax({
                url: funcUrls.removeSurveyProjectUrl,
                data:{
                    metaObjectId: record.id,
                    metaObjectName: record.metaObjectName
                },
                type: "POST",
                success: function(){
                    $.youi.messageUtils.showMessage("删除成功！");
                    $elem("grid_survey_project",pageId).grid("refresh");
                }
            });
        });
    </youi:func>

    <!-- 导入元数据 -->
    <youi:func name="grid_survey_project_importData" urls="importDataUrl">
        $.youi.ajaxUtils.ajax({
            url:funcUrls.importDataUrl,
            data:{
                fileStorePath: "1234"
            },
            type: "POST",
            success:function(){
                $.youi.messageUtils.showMessage("导入完成！");
                $elem("grid_survey_project",pageId).grid("refresh");
            }
        });
    </youi:func>

    <youi:func name="form_survey_project_beforeSubmit" params="results">
        var metaObjectCaption = $elem("addProject_metaObjectCaption",pageId).fieldValue();
        if(metaObjectCaption.length>100){
            $.youi.messageUtils.showError('统计项目名称长度不能超过100！');
            return false;
        }
    </youi:func>

    <!-- form_survey_project 提交后回调-->
    <youi:func name="form_survey_project_afterSubmit" params="record">
        $.youi.messageUtils.showMessage('保存统计项目成功.');
        $elem("form_survey_project",pageId).form("reset").dialog("close");
        $elem("grid_survey_project",pageId).grid("refresh");
    </youi:func>

    <!-- form_uploadExcel 提交后回调-->
    <youi:func name="form_uploadExcel_afterSubmit" params="results">
        $.youi.messageUtils.showMessage(results.record.html);
        $elem("form_uploadExcel",pageId).form("reset");
        $elem("grid_survey_project",pageId).grid("refresh");
    </youi:func>

</youi:page>