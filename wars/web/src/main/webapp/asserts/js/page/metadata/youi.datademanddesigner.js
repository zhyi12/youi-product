/*!
 * youi JavaScript Library v3.0.0
 *
 *
 * Copyright 2016-2020, zhyi_12
 * Dual licensed under the MIT or GPL Version 2 licenses.
 *
 * Date: 2019-9-2
 */
(function($) {

    var _COMMAND = "dataDemandDesignerCommand";
    /**
     * 数据需求设计器（需求的思维导图设计）
     */
    $.widget("youi.dataDemandDesigner", $.youi.abstractWidget, $.extend({}, $.youi.abstractDesigner, {

        /**
         * 参数配置
         */
        options:{
            useModelTree:true,
            bindResize:true,
            getDataDemandRealmsUrl:'',//获取需求域集合
            getRealmDataDemandsUrl:''//获取域下面的需求集合
        },
        /**
         *
         * @private
         */
        _initWidget:function () {
            this._initRefWidget();//初始化关联组件
            this._loadModelTree();
            this._bindWidgetCommand();//组件事件代理
            this._initContent(function (contentElem) {
                contentElem.addClass('col-sm-12 no-padding').plumbFlow({});
            });
            this._resize();
        },

        /**
         *
         * @private
         */
        _loadModelTree:function () {
            if(this.options.getDataDemandRealmsUrl){
                $.youi.ajaxUtils.ajax({
                    url:this.options.getDataDemandRealmsUrl,
                    success:this._proxy('_parseModelTree')
                });
            }
        },

        /**
         * 解析树数据
         * @param result
         * @private
         */
        _parseModelTree:function (result) {
            if(result){
                var treeNodesHtml = [],getRealmDataDemandsUrl = this.options.getRealmDataDemandsUrl||'';
                $(result.records).each(function () {
                    treeNodesHtml.push($.youi.treeUtils.treeNodeHtml(this.id,this.text,{
                            src:$.youi.recordUtils.replaceByRecord(getRealmDataDemandsUrl,{realmId:this.id})
                        },
                        {group:'demand-realm'}));
                });
                this.modelTreeElem.find('>ul:first').html(treeNodesHtml.join(''));
            }
        },

        /**
         * 树节点选择
         * @param treeNode
         * @private
         */
        _treeNodeSelect:function (treeNode) {
            var groups = [];
            if(treeNode.hasClass('datademand')){
                groups.push('data-demand');
            }
            this.element.trigger('activeTools',{groups:groups});
        },

        /**
         *
         */
        openAddDataDemandRealm:function () {
            this._openSubPage('addDataDemandRealm',{},{});
        },
        /**
         * 保存思维导图
         */
        save:function () {
            var xml = this.contentElem.plumbFlow('getXml');
            console.log(xml);
        },

        /********************************************** 窗口回调方法 **************************************************/

        /**
         * 在需求域节点上增加需求，填充新增需求的弹出form
         * @private
         */
        _addDataDemandFormRecord:function (record) {
            return {realmId:record.id};
        },

        /**
         * resize
         * @private
         */
        _resize:function () {
            this._resizePageHeight();
        }

    }));


})(jQuery);
