/**
 * Created by zhouyi on 2017/2/16.
 */

require("./youi.core.js");
(function( $, undefined ) {
    'use strict';

    var _LOG = $.youi.log,
        _EDITING_CLASS = 'editing';

    function EditorFactory() {
        this.editors = [];
    }

    $.extend(EditorFactory.prototype,{

        /**
         * 创建editor
         * @param editorName
         */
        createHtml:function (editorName,value,text,options) {
            var editor = this.editors[editorName];
            return editor.createHtml(value,text,options);
        },
        
        getEditorValue:function (editorName,editorElement) {
            return this.editors[editorName].getValue(editorName,editorElement);
        },

        closeEditor:function (editorName,editorElement) {
            return this.editors[editorName].closeEditor(editorElement);
        },

        isSupportEditor:function (editorName) {
            return this.editors[editorName];
        },

        /**
         * 注册editor
         * @param editorName
         * @param editor
         */
        registerEditor:function (editorName,editor) {
            this.editors[editorName] = $.extend({
                createHtml:function (value,text,options) {
                    return '<div class="youi-field editor fieldSpinner input-group"></div>';
                },

                getValue:function (editorName,editorElement) {
                    if(editorElement[editorName]){
                        return editorElement.fieldValue();
                    }
                    return '';
                },

                closeEditor:function (editorElement) {
                    editorElement.remove();
                }
            },editor);

            return this;
        }
    });

    $.youi.editorFactory = new EditorFactory();

    $.youi.editorFactory.registerEditor('fieldText',{
        createHtml:function (value,text,options) {
            return '<input style="width:100%;" class="editor fieldText" value="'+value+'"/>';
        },

        getValue:function (editorName,editorElement) {
            return editorElement.val();
        },

        closeEditor:function (editorElement) {
            editorElement.remove();
        }
    }).registerEditor('fieldSelect',{
        createHtml:function (value,text,options){
            var converts = $.youi.serverConfig.convertArray[options.convert],
                htmls = [];

            htmls.push('<div class="editor open fieldSelect dropdown">');
            htmls.push(	'<div class="dropdown-property" data-toggle="dropdown" aria-haspopup="false"  aria-expanded="true">'+text+'<span class="caret"></span></div>');
            htmls.push(	'<ul class="dropdown-menu">');
            for(var iConvert in converts){
                htmls.push('<li data-value="'+iConvert+'" class="option-item">'+converts[iConvert]+'</li>');
            }
            htmls.push(	'</ul>');
            htmls.push('</div>');
            return htmls.join('');
        }
    });

    $.widget("youi.editor",$.youi.abstractWidget,$.extend({},{
        options:{
            /**
             * 自定义扩展获取编辑属性
             * @param editElem
             * @returns {{}}
             */
            editOptions:function (editElem) {
                return {};
            }
        },

        _initAction:function () {
            this._on({
                'mouseout input.editor':function (event) {
                    this._closeEditor(this._getEditElement($(event.currentTarget)));
                    return false;
                },
                'click [data-editor]':function (event) {
                    var editElem = $(event.currentTarget),
                        editorName = editElem.data('editor'),
                        value = editElem.data('value'),
                        text = editElem.text();

                    if(editElem.parents('.readonly').length){
                        return ;
                    }

                    if(!$.youi.editorFactory.isSupportEditor(editorName)){
                        _LOG.info('不支持的编辑组件：'+editorName);
                        return ;
                    }

                    var lastEditingElem = this.element.find('[data-editor].'+_EDITING_CLASS);

                    event.stopPropagation();

                    if(editElem[0]===lastEditingElem[0]){//比较上一个编辑的元素和当前点击的编辑单位是否相同
                        return;
                    }
                    //关闭上一个打开的编辑框
                    this._closeEditor(lastEditingElem);
                    //打开编辑
                    var editOptions = this.options.editOptions(editElem);
                    editElem.empty().addClass(_EDITING_CLASS).html($.youi.editorFactory.createHtml(editorName,value,text,editOptions));

                    //初始化为注册的field组件
                    if(editorName!=='fieldText'&&editorName!=='fieldSelect'){
                        editElem.find('>.editor')[editorName]($.extend({},editOptions,{
                            property:'editor-value',
                            defaultValue:value,
                            initHtml:true
                        },editOptions.editorOptions));
                    }
                },
                'click .editor .option-item':function (event) {
                    //选择项
                    var itemElem = $(event.currentTarget),
                        editElem = this._getEditElement(itemElem),
                        value = itemElem.data('value'),
                        oldValue = editElem.data('value');

                    editElem.data('value',value).text(itemElem.text()).removeClass(_EDITING_CLASS);
                    //值已经变化
                    if(value!==oldValue){
                        this._valueChange(editElem,value,oldValue);
                    }
                    return false;
                },
                'click':function () {
                    this._closeEditor(this.element.find('[data-editor].'+_EDITING_CLASS+':first'));
                }
            });
        },

        /**
         * 关闭编辑
         * @param editingElem
         * @private
         */
        _closeEditor:function (editingElem) {
            if(editingElem&&editingElem.hasClass(_EDITING_CLASS)){
                var editorName = editingElem.data('editor'),
                    oldValue = editingElem.data('value');

                if($.youi.editorFactory.isSupportEditor(editorName)){
                    if(editingElem.find('.editor.dropdown').length===0){
                        var value = $.youi.editorFactory.getEditorValue(editorName,editingElem.find('>.editor'));

                        if(!value&&value!==0){
                            value = '';
                        }

                        editingElem.data('value',value).text(value);
                        if(value!==oldValue){
                            this._valueChange(editingElem,value,oldValue);
                        }
                    }else{
                        editingElem.text(editingElem.find('.dropdown-property').text());
                    }
                }

                editingElem.removeClass(_EDITING_CLASS);
            }
        },

        /**
         * 获取编辑元素
         * @param elem
         * @returns {*}
         * @private
         */
        _getEditElement:function (elem) {
            return elem.parents('[data-editor]:first');
        },

        /**
         *
         * @param editElem  编辑的对象元素
         * @param value 新值
         * @param oldValue 旧值
         * @private
         */
        _valueChange:function (editElem,value,oldValue) {
            if(value!==oldValue){
                this._callGloablFunc('valueChange',editElem,value,oldValue);
            }
        }

    }));

})(jQuery);