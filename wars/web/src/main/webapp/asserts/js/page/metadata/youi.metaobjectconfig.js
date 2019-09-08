/**
 * Created by zhouyi on 2019/9/6.
 */


(function($) {

    $.widget("youi.metaObjectConfig",$.youi.abstractWidget,$.extend({},{

        options:{
            metaObjectName:'',
            getMetaObjectConfigUrl:'',
            saveMetaObjectConfigUrl:'',
            removeMetaObjectConfigUrl:'',
        },
        /**
         *
         * @private
         */
        _initWidget:function () {
            this.gridElem = this.element.find('.youi-grid:first');
            this._loadMetaObjectConfig();
            this._bindWidgetCommand();
        },

        _loadMetaObjectConfig:function () {
            if(this.options.metaObjectName && this.options.getMetaObjectConfigUrl){
                $.youi.ajaxUtils.ajax({
                    url:this.options.getMetaObjectConfigUrl,
                    data:{metaObjectName:this.options.metaObjectName},
                    success:this._proxy('_parseRecord')
                });
            }
        },

        _parseRecord:function (result) {
            //properties
            this.gridElem.grid('parseRecords',result.record.properties);
        },

        submit:function () {
            $.youi.messageUtils.confirm('确认保存？',this._proxy('_submit'));
        },

        _submit:function () {
            if(this.options.metaObjectName && this.options.saveMetaObjectConfigUrl){
                $.youi.ajaxUtils.ajax({
                    url:this.options.saveMetaObjectConfigUrl,
                    data:$.youi.recordUtils.recordToAjaxParamStr({metaObjectName:this.options.metaObjectName,properties:this.gridElem.grid('getRecords')}),
                    success:function (result) {
                        $.youi.messageUtils.showMessage('保存成功');
                    }
                });
            }
        }

    }));


})(jQuery);