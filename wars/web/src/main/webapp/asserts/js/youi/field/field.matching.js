/**
 * field组件
 * Copyright (c) 2009 zhouyi
 * licenses
 * doc
 */
(function($) {
    var _log = $.youi.log;

    /**
     *
     */
    $.widget("youi.fieldMatching",$.youi.abstractWidget,$.extend({},$.youi.field,{

        _initField:function () {
            var parent = this.element.parent(),
                matchingItem = parent.data('matchingItem');

            if(matchingItem){
                this.element.addClass('dropdown open').html(_buildMatchingHtml(matchingItem,this.element.data('value')));
            }else{
                this.element.text(this.element.data('value'));
            }
        },

        _initAction:function () {

        },

        setValue:function (value) {
            this.element.data('value',value).attr('data-value',value);
        },

        getValue:function () {
            return this.element.data('value');
        },

        clear:function () {
            //this.element.clear();
        }

    }));

    function _buildMatchingHtml(matchingItem,selectedValue) {
        var htmls = [],liHtmls=[],selectedItem = {id:'',text:''};

        $(matchingItem.matchingResults).each(function () {
            liHtmls.push('<li class="option-item" data-value="'+this.id+'">'+this.id+'-'+this.text+'</li>');
            if(selectedValue == this.id){
                selectedItem = this;
            }
        });

        htmls.push('<div class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true" data-value="'+selectedItem.id+'">'+(selectedItem.id+'-'+selectedItem.text)+'</div>');
        htmls.push('<ul class="dropdown-menu" >');

        htmls = htmls.concat(liHtmls);

        htmls.push('</ul>');

        return htmls.join('');
    }

    $.youi.editorFactory.registerEditor('fieldMatching',{
        createHtml:function (value,text,options) {
            return '<div style="width:100%;" class="editor fieldMatching" data-value="'+value+'"/>';
        },

        getValue:function (editorName,editorElement) {
            return editorElement.data('value');
        },

        closeEditor:function (editorElement) {
            editorElement.remove();
        }
    });

})(jQuery);