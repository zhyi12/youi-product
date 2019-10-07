<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="用户管理">

    <youi:ajaxUrl name="getUserConfigsUrl" src="/uaa/services/userConfigManager/getUserConfigs.json"/>

    <youi:ajaxUrl name="getUnEffectPhoneModifyUrl" src="/uaa/services/phoneModifyManager/getUnEffectPhoneModify.json"/>
    <%--删除用户的专业--%>
    <youi:ajaxUrl name="removeUserProfessionsUrl" src="/uaa/services/userConfigManager/removeUserProfessions.json"/>
    <%--根据机构获取专业--%>
    <youi:ajaxUrl name="getAgencyProfessionUrl" src="/metadataServices/services/professionManager/getProfessionsByAgency.json"/>

    <youi:subpage height="300" width="550" src="page/${_pagePath}/userEdit.html?userId={userId}&username={username}&userCaption={userCaption}&areaId={areaId}&agencyId={agencyId}&editType={editType}"
                  subpageId="user_add" caption="添加用户" type="dialog"/>

<%--    <youi:subpage height="250" width="550" src="page/${_pagePath}/batchUserAdd.html"
                  subpageId="user_batch_add" caption="批量添加用户" type="dialog"/>--%>

    <youi:subpage height="350" width="550" src="page/${_pagePath}/userEdit.html?userId={userId}&username={username}&userCaption={userCaption}&areaId={areaId}&agencyId={agencyId}&editType={editType}"
                  subpageId="user_edit" caption="修改用户" type="dialog"/>

    <youi:subpage height="300" width="550" src="page/${_pagePath}/phoneEdit.html?username={username}&userCaption={userCaption}&oldPhone={mobile}&targetType={targetType}"
                  subpageId="phone_edit" caption="修改联系电话" type="dialog"/>

    <youi:subpage height="450" width="550" src="page/${_pagePath}/userRoles.html?userId={userId}&username={username}&userCaption={userCaption}&areaId={areaId}&agencyId={agencyId}"
                  subpageId="user_bindRole" caption="绑定角色" type="dialog"/>

    <youi:subpage height="450" width="550" src="page/${_pagePath}/batchUserRoles.html?&username={username}&agencyId={agencyId}"
                  subpageId="batchUser_bindRole" caption="批量用户绑定角色" type="dialog"/>

    <youi:grid id="grid_user" src="/uaa/services/userConfigManager/getPagerUsers.json" load="false"
               idKeys="userId" styleClass="grid-query-inline"
               showNum="true" showCheckbox="true">

        <youi:fieldLayout>
            <youi:fieldHidden property="professions" styleClass="array"/>
            <youi:fieldText property="username" operator="LIKE" caption="登录用户"/>
            <youi:fieldText property="agencyId" operator="LIKE" caption="所属机构"/>
        </youi:fieldLayout>

        <youi:gridCol width="15%" caption="登录用户" property="username"/>
        <youi:gridCol width="15%" caption="用户名称" property="userCaption" renderer="rendererUserCaption"/>
        <youi:gridCol width="15%" caption="所属区域" property="areaId" renderer="rendererAreaId"/>
        <youi:gridCol width="15%" caption="所属机构" property="agencyId"/>
        <youi:gridCol width="20%" caption="联系电话" property="mobile" renderer="rendererMobile"/>
        <youi:gridCol align="center" width="20%" type="button" property="button" caption="操作">

            <youi:button name="edit" icon="rename" caption="编辑"/>
            <youi:button name="split" caption=""/>
            <youi:button name="editMobile" icon="edit" caption="修改电话"/>
            <youi:button name="split" caption=""/>
            <youi:button name="bindRole" icon="bangdingjiaose" caption="绑定角色"/>
            <%--<youi:button name="editPassword" icon="edit" caption="修改密码"/>--%>
            <youi:button name="split" caption=""/>
            <youi:button name="remove" icon="delete-slender" caption="删除"/>

        </youi:gridCol>

        <youi:button name="add" caption="添加" icon="plus"/>
        <%--<youi:button name="batchAdd" caption="批量添加" icon="plus"/>--%>
        <youi:button name="batchBindRole" caption="用户批量绑定角色" tooltips="对查询过滤条件出的用户进行批量操作，谨慎操作" icon="bangdingjiaose" authCode="sysAdminRole"/>
    </youi:grid>

    <youi:func name="init" urls="getAgencyProfessionUrl">
        $.youi.ajaxUtils.ajax({
            url: funcUrls.getAgencyProfessionUrl,
            type: 'POST',
            notShowLoading: true,
            success: function (result) {
            if(result && result.records){
                var professionIds = [],
                professions = result.records;
                professions.forEach( function (profession) {
                    professionIds.push(profession.professionId);
                });
                $elem('field_professions',pageId).fieldValue(professionIds);
                $elem('grid_user',pageId).grid('refresh');
                }
            }
        });
    </youi:func>

    <youi:func name="rendererUserCaption">
        //仅仅为了添加class，找到单元格
    </youi:func>

    <youi:func name="rendererAreaId">
        //仅仅为了添加class，找到单元格
    </youi:func>

    <youi:func name="rendererMobile">
        //仅仅为了添加class，找到单元格
    </youi:func>

    <youi:func name="grid_user_afterLoad" params="count,records" urls="getUserConfigsUrl">
        if(count == 0){
            return;
        }

        var params = [];
        $(records).each(function(){
            params.push('username='+this.username);
        });
        $.youi.ajaxUtils.ajax({
            url:funcUrls.getUserConfigsUrl,
            type:'POST',
            data:params.join('&'),
            success:function(result) {
                $.youi.pageUtils.doPageFunc('refreshUser',pageId,null,result);
            }
        });
    </youi:func>

    <youi:func name="refreshUser" params="result">
        $(result.records).each(function(){
            var userRow = $elem('grid_user',pageId).find('.grow[data-user-id='+this.username+']');
            if(this.params && this.params.areaId){userRow.find('.cell.areaId').text(this.params.areaId);}
            if(this.params && this.params.userCaption){userRow.find('.cell.userCaption').text(this.params.userCaption);}
            if(this.params && this.params.mobile){userRow.find('.cell.mobile').text(this.params.mobile);}

        });
    </youi:func>

    <youi:func name="grid_user_add">
        $elem('subpage_user_add',pageId).subpage('open',{},'',{editType:'add'},pageId);
    </youi:func>

<%--
    <youi:func name="grid_user_batchAdd">
        $elem('subpage_user_batch_add',pageId).subpage('open',{},'',{},pageId);
    </youi:func>--%>

    <youi:func name="grid_user_edit" params="dom,options,record">
        var userRowRecord = $elem('grid_user',pageId).find('.grow[data-user-id='+record.userId+']');
        record.userCaption = userRowRecord.find('.cell.userCaption').text();
        record.areaId = userRowRecord.find('.cell.areaId').text();
        record.editType = 'edit';
        $elem('subpage_user_edit',pageId).subpage('open',{},'',record,pageId);
    </youi:func>

    <youi:func name="grid_user_remove" params="dom,options,record" urls="removeUserProfessionsUrl">
        $.youi.messageUtils.confirm('确定删除企业用户？',function(){
            //删除用户中包含的【当前用户专业】部分专业
            $.youi.ajaxUtils.ajax({
                url: funcUrls.removeUserProfessionsUrl,
                data:$.youi.recordUtils.recordToAjaxParamStr({username:record.username,professions:$elem('field_professions',pageId).fieldValue()[0].split(',')}),
                notShowLoading: true,
                success: function (result) {
                    $.youi.messageUtils.showMessage('删除成功');
                    $elem('grid_user',pageId).grid('refresh');
                }
            });
        });
    </youi:func>

    <youi:func name="grid_user_editMobile" params="dom,options,record" urls="getUnEffectPhoneModifyUrl">
        $.youi.ajaxUtils.ajax({
            url:funcUrls.getUnEffectPhoneModifyUrl,
            type:'POST',
            data:{'targetId':record.username},
            success:function(result) {
                if(result&&result.records[0]){
                    $.youi.messageUtils.showMessage('存在待审批的联系电话修改申请,无法再次修改.');
                }else{
                    var userRowRecord = $elem('grid_user',pageId).find('.grow[data-user-id='+record.userId+']');
                    record.userCaption = userRowRecord.find('.cell.userCaption').text();
                    record.mobile = userRowRecord.find('.cell.mobile').text();
                    record.targetType = 'admin';
                    $elem('subpage_phone_edit',pageId).subpage('open',{},'',record,pageId);
                }
            }
        });
    </youi:func>

    <youi:func name="grid_user_bindRole" params="dom,options,record">
        $elem('subpage_user_bindRole',pageId).subpage('open',{},'',record,pageId);
    </youi:func>

    <youi:func name="grid_user_batchBindRole">
        var record = {};
        record.username = $elem('field_username',pageId).fieldValue();
        record.agencyId = $elem('field_agencyId',pageId).fieldValue();
        $elem('subpage_batchUser_bindRole',pageId).subpage('open',{},'',record,pageId);
    </youi:func>

    <!-- 新增回调-->
    <youi:func name="subpage_user_add_change" params="record">
        $elem('grid_user',pageId).grid('refresh');
    </youi:func>

    <!-- 编辑回调-->
    <youi:func name="subpage_user_edit_change" params="record">
        $elem('grid_user',pageId).grid('refresh');
    </youi:func>

</youi:page>