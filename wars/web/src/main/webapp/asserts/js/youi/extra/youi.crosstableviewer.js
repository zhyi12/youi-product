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

        /**
         * 初始化组件
         * @private
         */
        _initWidget:function(){
            this._initContent();
        },

        _initContent:function(){
            var contentClazz = this.widgetName+'-content';
            this.contentElem = this.element.find('>.'+contentClazz);
            if(!this.contentElem.length){
                this.contentElem = $('<div class="'+contentClazz+'"></div>');
            }

            this.contentElem.crossTable({

            });
        },

        /**
         *
         */
        parseRecords:function (result) {
            if(result.records){
                this.contentElem.crossTable('drawCubes',result.records);
            }
        }
    }));

})(jQuery);