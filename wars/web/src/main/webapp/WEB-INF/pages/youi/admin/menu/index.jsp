<%@ include file="/WEB-INF/pages/common/include.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<youi:page caption="菜单管理">

    <youi:ajaxUrl name="getMenu" src="/operatorServices/services/menuManager/getMenu.json"/>

    <!-- 树右键菜单 -->
    <youi:xmenu id="xmenu_menu">
        <youi:xmenuItem caption="添加 {0}子菜单" icon="plus" name="addMenu" groups="treeNode"/>
        <youi:xmenuItem caption="删除菜单-{0}" icon="delete-slender" name="removeMenu" groups="source-data"/>
    </youi:xmenu>

    <youi:customWidget name="page_spliter" widgetName="pageSpliter" styleClass="col-sm-3 page-inner-height">
        <youi:toolbar refWidgetId="tree_menu" styleClass="fixed-height">
            <youi:toolbarItem caption="添加" tooltips="新增菜单" name="xmenu_addMenu" icon="add"/>
            <youi:toolbarItem caption="全展开" tooltips="展开菜单" name="expandAll" icon="open-all"/>
        </youi:toolbar>
        <youi:tree id="tree_menu" rootText="系统菜单" hideRoot="true" height="450"
                   styleClass="col-sm-12 auto-height"
                   iteratorSrc="/page/getMenuTree.json" xmenu="xmenu_menu">
        </youi:tree>
    </youi:customWidget>

    <div class="col-sm-9 page-inner-height page-spliter-right">
        <youi:toolbar refWidgetId="form_menu">
            <youi:toolbarItem caption="提交" tooltips="保存菜单" name="submit" icon="save" groups="menu"/>
            <youi:toolbarItem caption="清除角色关联" tooltips="清除角色关联" name="removeRefRoles" icon="delete-slender" groups="menu"/>
            <youi:toolbarItem caption="重置" tooltips="重置" name="formReset" icon="reset" groups="menu"/>
        </youi:toolbar>
        <youi:form id="form_menu" caption=""  styleClass="disabled"
                   reset="NOT" submit="NOT" idKeys="menuId" panel="false"
                   action="/operatorServices/services/menuManager/saveMenu.json">
            <youi:fieldLayout labelWidths="100,100">
                <youi:fieldHidden property="menuId"/>
                <youi:fieldHidden property="parentMenuId"/>
                <youi:fieldHidden property="webAppId" defaultValue="${_appName}"/>
                <youi:fieldText notNull="true" property="menuCode"  caption="菜单编号"/>
                <youi:fieldText notNull="true" styleClass="autoAlign"  property="menuCaption"  caption="菜单名称"/>

                <youi:fieldText property="menuNum" styleClass="autoAlign"  caption="菜单序号"/>
                <youi:fieldText property="menuStyle"  caption="菜单样式"/>

                <youi:fieldText styleClass="autoAlign" column="2" property="pageUrl"  caption="菜单地址"/>
                <youi:fieldCustom column="2" property="params" custom="fieldKeyValue" caption="页面参数"/>

            </youi:fieldLayout>
        </youi:form>
    </div>

    <youi:func name="tree_menu_select" params="treeNode" urls="getMenu">
        var groups = [],
            formElem = $elem('form_menu',pageId);

        formElem.form('clear').addClass('disabled');
        if(treeNode.hasClass('menu')){
            groups = ['menu'];
            var menuId = treeNode.attr('id');
            formElem.removeClass('disabled').form('find',funcUrls.getMenu+'?menuId='+menuId);
        }
        //激活绑定的toolbar上的按钮
        formElem.trigger('activeTools',{groups:groups});
    </youi:func>

</youi:page>


