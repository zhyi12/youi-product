/**
 * Created by zhouyi on 2019/9/2.
 */


(function($) {

    $.widget("youi.myWidget",$.youi.abstractWidget,$.extend({},{

        /**
         *
         * @private
         */
        _initWidget:function () {
            this.element.html("----------------"+this.options.width);
        }

    }));


})(jQuery);