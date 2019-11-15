/**
 * Created by zhouyi on 2019/11/13.
 */


(function($) {

    var _COMMAND = "odsLoaderConfigCommand";
    /**
     * 概念模型设计器
     */
    $.widget("youi.odsLoaderConfig",$.youi.abstractWidget,$.extend({},$.youi.abstractDesigner,{
        options:{
            useModelTree:true,
            bindResize:true,
            getModelTreeUrl:'',//模型树url
            getDataTablesUrl:'',//模块子节点树url
        },

        /**
         *
         * @private
         */
        _initWidget:function () {
            this._initRefWidget();//初始化关联组件
            this._loadModelTree();//加载模型树
            this._bindWidgetCommand();//组件事件代理
        },

        /**
         *
         * @private
         */
        _loadModelTree : function () {
            if(this.options.getModelTreeUrl){
                $.youi.ajaxUtils.ajax({
                    url:this.options.getModelTreeUrl,
                    success:this._proxy('_parseModelTree')
                });
            }
        },

        /**
         * 解析树
         * @param result
         * @private
         */
        _parseModelTree:function (result) {
            if(!result || !$.isArray(result.records)){
                return;
            }
            var rootId = 'ROOT',rootText = '数据库',getDataTablesUrl = this.options.getDataTablesUrl;
            //
            $(result.records).each(function () {
                $(this.children).each(function () {
                    if(getDataTablesUrl){
                        this.src =  $.youi.parameterUtils.connectParameter(getDataTablesUrl,'moduleId',this.id);
                    }
                });
            });

            var html = $.youi.treeUtils.treeNodeHtml(rootId,rootText,{group:'root'},{children:result.records});
            this.modelTreeElem.find('>ul:first').html(html);
        },
    }));

})(jQuery);