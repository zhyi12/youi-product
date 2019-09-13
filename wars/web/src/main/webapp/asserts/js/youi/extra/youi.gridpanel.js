/**
 * 
 */
require("./../youi.core.js");
(function($) {

    /**
     *  grid 容器
     */
    $.widget("youi.gridPanel",$.youi.abstractWidget,$.extend({},{

        options:{
            bindResize:true
        },

        /**
         *
         * @private
         */
        _initWidget:function () {
            
        },

        _resize:function () {
            this._resizePageHeight();
        }

    }));

})(jQuery);