/*!
 * youi JavaScript Library v3.0.0
 *
 *
 * Copyright 2016-2020, zhyi_12
 * Dual licensed under the MIT or GPL Version 2 licenses.
 *
 * Date: 2019-12-08
 */
(function($) {
    /**
     * 交叉表的viewer
     */
    $.widget("youi.crossTableViewer",$.youi.abstractWidget,$.extend({},{

        options:{
            bindResize:true
        },
        /**
         * 初始化组件
         * @private
         */
        _initWidget:function(){
            this._initContent();
            this.element.trigger('initParseRecords');
        },

        _initContent:function(){
            var contentClazz = this.widgetName+'-content';
            this.contentElem = this.element.find('>.'+contentClazz);
            if(!this.contentElem.length){
                this.contentElem = $('<div class="'+contentClazz+'"></div>');
            }

            this.contentElem.addClass('auto-height').crossTable({
                afterParse:this._proxy('_afterParseCubes')
            });
        },

        _initAction:function(){
            // this._on({
            //     'initParseRecords':function (event,ui) {
            //         console.log(ui);
            //     }
            // });
        },

        _afterParseCubes:function(cubes){
            this._resize();
        },

        /**
         *
         */
        parseRecords:function (result) {
            if(result.records){
                this.contentElem.crossTable('drawCubes',result.records);
            }
        },

        _resize:function () {
            this._resizePageHeight();
        }
    }));

})(jQuery);