/**
 * Created by zhouyi on 2019/6/8.
 */
(function($) {
    'use strict';

    var _COMMAND = '';

    $.widget("youi.expressionEditor", $.youi.abstractWidget, $.extend({}, {

        options:{
            bindResize:true,
            rootText:'项',
            itemCaption:'项',
            keyTreeUrl:'',
            treeNodeGroups:'',//
            itemProps:'id,text',
            expressionItems:{
                operator:{
                    text:'操作符',
                    items:[{id:'add',text:'加',expression:'+'},
                        {id:'subtract',text:'减',expression:'-'},
                        {id:'multiply',text:'乘',expression:"*"},
                        {id:'divide',text:'除',expression:'/'},
                        {id:'pow',text:'平方',expression:'^'},
                        {id:'mod',text:'求余',expression:'%'},
                        {id:'lbrackets',text:'左括号',expression:'('},
                        {id:'rbrackets',text:'右括号',expression:')'},
                        {id:'sum',text:'求和',expression:'∑'}]
                },
                func:{
                    text:'常用函数',
                    items:[
                        {id:'max',text:'最大值',expression:'max({0},{1})'},
                        {id:'min',text:'最小值',expression:'min({0},{1})'},
                        {id:'avg',text:'平均值',expression:'avg({0},{1})'},
                        {id:'yearOnYear',text:'上年同期',expression:'yearOnYear({0})'},
                        {id:'monthOnMonth',text:'上期值',expression:'monthOnMonth({0})'}
                    ]
                }
            }
        },

        /**
         *
         * @private
         */
        _initWidget:function () {
            this.element.addClass('youi-field auto-height')
                .attr('data-field-type',this.widgetName)
                .attr('data-property',this.options.name);
            this.contentElem = this.element.find('.'+this.widgetName+'-content:first');
            if(typeof(this.options.treeNodeGroups) =='string'){
                this.options.treeNodeGroups = this.options.treeNodeGroups.split(',');
            }
            if(typeof(this.options.itemProps) =='string'){
                this.options.itemProps = this.options.itemProps.split(',');
            }
            this._initContentHtml();
        },

        _initContentHtml:function () {
            var htmls = [],idPrefix = this.element.attr('id'),editorId = idPrefix+'e';

            htmls.push('<div class="col-sm-3 panel-group no-padding" id="accordion" role="tablist" aria-multiselectable="true">');
            htmls.push(_buildPanel(idPrefix,{name:'indicators',text:this.options.itemCaption},true));
            //htmls.push(_buildExpressionItems(idPrefix,this.options.expressionItems));

            htmls.push('</div>');
            htmls.push('<div class="col-sm-9"><div id="'+editorId+'" class="expression-editor" style="height:100px;"></div></div>');

            this.contentElem.html(htmls.join(''));
            this.itemContainer = this.contentElem.find('.panel-body[data-id=indicators]');

            UE.delEditor(editorId);
            this.ueditor = UE.getEditor(editorId,{
                toolbars: [
                    ['undo', 'redo']
                ],
                initialFrameHeight :345,
                autoHeightEnabled: true,
                autoFloatEnabled: true,
                elementPathEnabled:false
            });

            //keyTreeUrl
            if(this.options.keyTreeUrl){
                this._initKeyTree();
            }
        },

        _initKeyTree:function () {
            var treeId = this.element.attr('id')+'tree';
            var htmls = ['<ul>'];
            htmls.push($.youi.treeUtils.treeNodeHtml('ROOT',this.options.rootText,{group:'root',src:this.options.keyTreeUrl}));
            htmls.push('</ul>');

            this.keyTreeElem = this.itemContainer.attr('id',treeId).html(htmls.join('')).tree({
                xmenu:this.options.refs[0],
                dragable:true,
                hideRoot:true,
                check:this.options.check=='true'?true:false
            });

            var funcName = treeId+'_xmenu_addItem';

            window[funcName] = this._proxy('_addItem');
        },

        _addItem:function (dom,commandOptions) {
            var selectedTreeNode = this.keyTreeElem.tree('getSelected');
            if(commandOptions.value&&selectedTreeNode.length){

                var postfix = '_'+(commandOptions.value=='yearOnYear'?'上年同期':'上期');

                var itemData = selectedTreeNode.data(),
                    item = $.extend({id:selectedTreeNode.attr('id'),mappedId:itemData.reportCode+itemData.physicalTableNum},itemData,
                        {text:$.youi.treeUtils.getNodeText(selectedTreeNode)+postfix,
                            expression:commandOptions.value});
                this._insertKeyItem(item);
            }
        },

        _initAction:function () {
            this._on({
                'dblclick .expressionEditor-content .expression-item':function (event,ui) {
                    var itemElem = $(event.currentTarget),
                        item = itemElem.data();

                    this.ueditor.ready(function() {
                        this.execCommand('inserthtml',item.expression);
                    });
                },
                'dblclick .expressionEditor-content .option-item':function (event,ui) {
                    var itemElem = $(event.currentTarget),
                        itemData = itemElem.data(),
                        item = $.extend({id:itemData.value,mappedId:''},itemData,{text:itemElem.text()});

                    this._insertKeyItem(item);
                },
                'dblclick .expressionEditor-content .treeNode':function (event,ui) {
                    var itemElem = $(event.currentTarget),
                        itemData = itemElem.data(),
                        group = itemData.group;

                    if($.inArray(group,this.options.treeNodeGroups)!=-1){
                        var item = $.extend({id:itemElem.attr('id')},itemData,{text:$.youi.treeUtils.getNodeText(itemElem)});
                        this._insertKeyItem(item);
                    }

                    return false;
                }
            });
        },

        _insertKeyItem:function (item) {
            var itemHtml = _buildKeyItem(item,this.options.itemProps);

            var ue = this.ueditor;
            this.ueditor.ready(function() {
                ue.execCommand('inserthtml',itemHtml);
            });
        },

        /**
         *
         * @param items
         * @param itemHtmlCallback
         */
        insertItems:function (items,itemHtmlCallback) {
            var htmls = [],props = this.options.itemProps;

            $(items).each(function (index) {
                var html = _buildKeyItem(this,props);
                if($.isFunction(itemHtmlCallback)){
                    html = itemHtmlCallback(html,index)||html;
                }
                htmls.push(html);
            });
            var ue = this.ueditor;
            this.ueditor.ready(function() {
                ue.execCommand('inserthtml',htmls.join(''));
            });
        },

        parseExpressionItems:function (expressionItems) {
            var idPrefix = this.element.attr('id');
            var html = _buildExpressionItems(idPrefix,expressionItems);

            this.element.find('.panel-group:first').append(html);
        },

        /**
         *
         */
        parseItems:function (result) {
            var htmls = [];
            if(result){
                $(result.records).each(function () {
                    htmls.push('<span data-id="'+this.id+'" class="option-item" data-expression="'+(this.expression||'')+'" data-value="'+this.id+'">'+this.text+'</span>');
                });
            }
            this.itemContainer.html(htmls.join(''))
        },

        getValue:function () {
            var valueHtml = this.ueditor.getContent();
            var items = [],source = '';
            var valueElem = $(valueHtml).appendTo(this.element);
            valueElem.find('input.keyword').each(function (index) {
                var elem = $(this);
                items.push($.extend({text:elem.val()},elem.data()));
                $('<span>{'+index+'}</span>').insertBefore(elem);
                elem.remove();
            });
            source = valueElem.text();
            valueElem.remove();

            return {
                items:items,
                source:source
            };
        },

        setValue:function (record) {
            if(record){
                var sourceParams = [],itemProps = this.options.itemProps;
                $(record.items).each(function () {
                    sourceParams.push(_buildKeyItem(this,itemProps));
                });
            }
            var valueContent = $.youi.getMessage(record.source,sourceParams)||'';

            this.ueditor.ready(function() {
                if(valueContent){
                    this.setContent(valueContent);
                }
            });
        },

        clear:function () {
            //this.element.empty();
        },

        validate:function(record){
            this.element.removeClass('validate-success').removeClass('validate-error').removeAttr('title');

            var errorMsg = [];

            if(errorMsg.length){
                this.element
                    .removeClass('validate-success')
                    .addClass('validate-error').attr('title',errorMsg.join(''));
            }else {
                this.element
                    .removeClass('validate-error').addClass('validate-success');
            }
        },

        _resize:function () {

        },

        _destroy:function () {
            this.ueditor = null;
        }

    }));

    function _buildDataAttrs(datas,itemProps) {
        var htmls = [],itemProps = itemProps||[];
        for(var iProp in datas){
            var name = $.youi.stringUtils.convertDataProperty(iProp,'-');
            if($.inArray(name,itemProps)!=-1){
                htmls.push('data-'+name+'="'+(datas[iProp]||'')+'"');
            }
        }
        return htmls.join(' ');
    }

    function _buildExpressionItems(idPrefix,expressionItems) {
        var htmls = [];
        for(var groupName in expressionItems){
            htmls.push(_buildPanel(idPrefix,$.extend({},expressionItems[groupName],{name:groupName})));
        }
        return htmls.join('');
    }

    function _buildPanel(idPrefix,panel,expanded) {
        var htmls = [];
        var panelId = idPrefix+'_'+panel.name;
        htmls.push('<div class="panel panel-default">');
        htmls.push('<div class="panel-heading" role="tab" id="'+panelId+'_head">');
        htmls.push('    <span class="panel-title">');
        htmls.push('   <a role="button" data-toggle="collapse" data-parent="#accordion" href="#'+panelId+'" aria-expanded="'+expanded+'" aria-controls="'+panelId+'">');
        htmls.push(panel.text);
        htmls.push(' </a>');
        htmls.push(' </span>');
        htmls.push(' </div>');
        htmls.push('<div id="'+panelId+'" class="panel-collapse collapse '+(expanded?' in':'')+'" role="tabpanel" aria-labelledby="'+panelId+'_head">');
        htmls.push('    <div class="panel-body" data-id="'+panel.name+'">');

        $(panel.items).each(function () {
            htmls.push(_buildExpressionItem(this));
        });

        htmls.push('</div></div>');
        htmls.push('</div>');
        return htmls.join('');
    }
    function _buildExpressionItem(expressionItem) {
        var html, expression = '<div class="expression-item col-sm-'+(expressionItem.column||6)+' '+expressionItem.id+'" data-expression="'+expressionItem.expression+'">'+(expressionItem.text||expressionItem.id)+'</div>';
        return expression;
    }
    
    function _buildKeyItem(item,itemProps) {
        return '<input type="button" '+_buildDataAttrs(item, itemProps)+' class="keyword" value="'+item.text+'" title="'+item.text+'"/>';
    }

})(jQuery);