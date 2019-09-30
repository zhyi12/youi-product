/**
 * Created by zhouyi on 2019/9/2.
 */


(function($) {

    /**
     * 概念模型设计器
     */
    $.widget("youi.metaCdmDesigner",$.youi.abstractWidget,$.extend({},$.youi.abstractDesigner,{

        options:{
            bindResize:true
        },
        /**
         *
         * @private
         */
        _initWidget:function () {
            this._initContent(function (contentElem) {
                contentElem.css({width:800,height:300}).html(_buildContentHtml()).flow({
                    overpanels:[
                        {name:'addRefNode',subname:'userTask',groups:['node'],excludes:['endEvent'],caption:'添加UserTask节点'},
                        {name:'startSequence',subname:'startSequence',groups:['node'],caption:'线条'},
                        {name:'removeNode',groups:['node'],excludes:['startEvent'],caption:'删除'},
                    ],

                });
            });

            this._resize();
        },

        /**
         *
         * @private
         */
        _resize:function () {
            this._resizeHeight();
            this.contentElem.width(this.element.innerWidth()).find('>canvas')
                .height(this.contentElem.innerHeight())
                .width(this.element.innerWidth());
        }

    }));

    /**
     *
     * @private
     */
    function _buildContentHtml() {
        var htmls = [];

        htmls.push('<div id="node1" class="node userTask" style="left: 133px; top: 120px;" title="">机构</div>');
        htmls.push('<div id="node2" class="node userTask" style="left: 314px; top: 120px;" title="">企业</div>');



        return htmls.join('');
    }

})(jQuery);