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
            getOdsTableMappingUrl:''
        },

        /**
         *
         * @private
         */
        _initWidget:function () {
            this._initRefWidget();//初始化关联组件
            this._bindWidgetCommand();//组件事件代理
        },

        /**
         * 模型树节点选择
         * @param treeNode
         * @private
         */
        _treeNodeSelect:function (treeNode) {
            if(this.options.getOdsTableMappingUrl && !treeNode.hasClass('root')){
                var tableName = treeNode.attr('id');
                //获取OdsMapping信息
                var url = $.youi.recordUtils.replaceByRecord(this.options.getOdsTableMappingUrl,{tableName:tableName},true);
                $.youi.ajaxUtils.ajax({
                    url:url,
                    success:this._proxy('_parseTableMapping')
                });
            }
        },

        /**
         *
         * @param result
         * @private
         */
        _parseTableMapping:function (result) {
            if(result.record){
                //
            }else{
                //没有则通过
            }
        }
    }));

})(jQuery);