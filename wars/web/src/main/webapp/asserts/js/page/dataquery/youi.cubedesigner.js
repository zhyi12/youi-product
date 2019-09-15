/**
 * Created by zhouyi on 2019/9/2.
 */
(function($) {

    $.widget("youi.cubeDesigner", $.youi.abstractWidget, $.extend({}, $.youi.abstractDesigner,{
        /**
         *
         */
        options:{
            bindResize:true
        },
        /**
         *
         * @private
         */
        _initWidget:function () {

            this._initContent();

            var htmls = [];
            htmls.push('<div>行标签</div>');
            htmls.push('<div>列标签</div>');
            htmls.push('<div>值</div>');

            this.contentElem.html(htmls.join(''));
        },

        /**
         *
         * @param columns
         * @param container
         */
        parseColumns:function (columns,container) {
            var htmls = [];

            $(columns).each(function () {
                htmls.push(_buildColumnFieldHtml(this));
            });

            container && container.html && container.html(htmls.join(''));
        }
    }));

    function _buildColumnFieldHtml(column) {
        return '<div class="option-item" data-id="'+column.columnName+'">'+column.text+'</div>';
    }

})(jQuery);