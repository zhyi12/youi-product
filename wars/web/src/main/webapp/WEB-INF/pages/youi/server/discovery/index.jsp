<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<youi:page caption="服务发现" dataSrc="/configServices/services/serverManager/getServerInfos.json">

    <youi:subpage src="page/youi.server.property/index.html?serverId={serverId}&title={serverId}" subpageId="serverProperties" caption="服务参数" type="secondPage"/>
    <youi:subpage src="page/youi.server.api/index.html?serverId={serverId}&title={serverId}" subpageId="serverApis" caption="服务API" type="secondPage"/>

    <youi:grid id="grid_server" load="false" query="NOT" reset="NOT" showFooter="false" idKeys="serverId">
        <youi:gridCol property="serverGroupName" caption="服务组"/>
        <youi:gridCol property="serverId" caption="服务名"/>
        <youi:gridCol property="address" caption="服务地址"/>

        <youi:gridCol width="10%" type="button" property="button" caption="操作">
            <youi:button name="serverProperties" caption="参数" icon="gear"/>
            <youi:button name="serverApis" caption="api" icon="list"/>
        </youi:gridCol>
    </youi:grid>

    <youi:func name="init" params="result">
        var records = [];
        $(result.records).each(function(){
            var instanceIds = [],serverId = this.instance[0].vipAddress,serverGroupName=this.instance[0].appGroupName;
            $(this.instance).each(function(){
                instanceIds.push(this.instanceId+'('+this.status+')');
            });
            var record = {
                serverGroupName:serverGroupName,
                serverId:serverId,
                name:this.name,
                address:instanceIds.join()
            };
            records.push(record);
        });
        $elem('grid_server',pageId).grid('parseRecords',records);
    </youi:func>

    <youi:func name="grid_server_serverProperties" params="dom,options,record">
        $elem('subpage_serverProperties',pageId).subpage('open',{},{},record);
    </youi:func>

    <youi:func name="grid_server_serverApis" params="dom,options,record">
        $elem('subpage_serverApis',pageId).subpage('open',{},{},record);
    </youi:func>
</youi:page>