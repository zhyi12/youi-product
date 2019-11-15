/*!
 * youi JavaScript Library v3.0.0
 *
 *
 * Copyright 2018, zhyi_12
 * Dual licensed under the MIT or GPL Version 2 licenses.
 *
 * Date: 2018-7-5
 */
(function($) {
    'use strict';

    var _COMMAND = 'treeBuilderCommand';

    var _COMMAND_METHODS = 'addNodeCommand,removeNodeCommand,moveUpCommand,moveDownCommand,moveLeftCommand,moveRightCommand'.split(',');

    /**
     * 树构建组件
     */
    $.widget("youi.treeBuilder", $.youi.abstractWidget, $.extend({}, $.youi.command,{

        options:{
            name:'node',
            bindResize:true,
            idAttr:'id',
            textAttr:'text',
            pidAttr:'pid',
            levelAttr:'level'
        },

        _initWidget:function () {
            this.UUID = 0;
            var rootText = '根节点';
            this.element.addClass('youi-field')
                .attr('data-field-type',this.widgetName)
                .attr('data-property',this.options.name+'s')
                .html('<ul><li class="root treeNode"><span class="tree-span"><a class="tree-a root node-text '+this.options.name+'">'+rootText+'</a></span><ul></ul></ul>')
                .tree({
                    cancel: "input,textarea,button,select,option,.node-text",
                    delay:100,
                    dragable:true,
                    dropStyle:this.options.name,
                    select:this._proxy('_selectTreeNode')
                }
            );
            this.rootNode = this.element.find('li.root:first');

            this._initRefWidgets();
            this._bindWidgetCommand();//关联组件的事件绑定

            this._initRegisterCommands();//可撤销命令绑定
        },

        _initRefWidgets:function () {
            var refs = this.options.refs;
            if(refs && refs.length>0){
                this.uploadXlsFormId = this.options.refs[0];
                window[this.uploadXlsFormId+'_afterSubmit'] = this._proxy('_parseTreeNodes');
            }
        },

        /**
         * 组件事件绑定
         * @private
         */
        _initAction:function () {
            //'dropStop .topic-indicator':function (event,ui) {
            var actions = {
                'dblclick .root':function (event,ui) {
                    return false;
                },
                'click .tree-node-hint':function (event,ui) {
                    var treeNode = $(event.currentTarget).parents('li.treeNode:first');
                    this._callGloablFunc('hint_click',treeNode[0]);
                },
                'mousedown .tree-node-remove': function (event, ui) {
                    var elem = $(event.currentTarget),
                        treeNodeElem = elem.parents('li.treeNode:first');
                    if (treeNodeElem.hasClass('selected')) {
                        this.executeCommand('removeNodeCommand', treeNodeElem);
                    }
                    return false;
                },'blur span.node-text':function (event,ui) {
                    var elem = $(event.currentTarget);
                    var currentText = elem.text();
                    var oldText = elem.data('text');
                    if(oldText&&oldText==currentText){
                        return false;
                    }
                    elem.text(currentText).data('text',currentText);
                    return false;
                },
            };
            //接收节点
            actions['dropStop .'+this.options.name] = function (event,ui) {
                var dropElem = $(event.currentTarget),
                    treeNodeElem = dropElem.parents('li.treeNode:first'),
                    movingNode = this.element.find('li#'+ui.id);

                if(movingNode.length){//移动本树中的节点
                    var oldParentNode = movingNode.parent().parent();
                    if(!$.contains(movingNode[0],dropElem[0])){
                        if(dropElem.hasClass('node-text')){
                            this.element.tree('moveNode',movingNode,treeNodeElem);
                        }else if(dropElem.hasClass('tree-span')){
                            //移动到节点后
                            treeNodeElem.after(movingNode);
                        }
                    }
                    this._checkNodeStyle(oldParentNode);
                }else{//接收外部拖动过来的节点
                    var nodeId = ui.code;
                    if(this.element.find('li.treeNode#'+nodeId).length==0){
                        var addType;
                        if(dropElem.is('.tree-span')){
                            //在节点后插入
                            addType = 'after';
                        }else if(dropElem.is('.node-text')){
                            //插入子节点
                            addType = 'child';
                        }else if(dropElem.is('.root')){
                            addType = 'child';
                        }

                        if(addType){
                            this.element.tree('addNode',treeNodeElem,nodeId,_buildNodeText(ui.text,this.options.name),{},
                                _buildTreeData(ui,this.options.name),function(){},addType);
                            //
                            this.element.tree('openPath',nodeId);//定位到新增加的节点
                        }
                    }else{
                        this.element.tree('openPath',nodeId);//
                        $.youi.messageUtils.showMessage('指标['+ui.text+']已经在树中。');
                    }
                }

                return false;
            }
            this._on(actions);
        },

        /**
         * 可撤销操作命令注册
         * @private
         */
        _initRegisterCommands:function () {
            this._initCommands();//
            var commands = _COMMAND_METHODS;
            for(var i=0;i<commands.length;i++){
                if(commands[i]&&$.isFunction(this[commands[i]])){
                    this.registerCommand(commands[i]);
                }
            }
        },

        /**
         *
         * @param event
         * @param ui
         * @private
         */
        _selectTreeNode:function (event,ui) {
            this._activeToolGroups(ui.selectedNode);
        },

        /**
         * 激活绑定的toolbar上的按钮
         * @param treeNode
         * @private
         */
        _activeToolGroups:function (treeNode) {
            var groups = [];
            if(treeNode&&!treeNode.hasClass('root')){

                groups.push(this.options.name);

                if(treeNode.prev().length>0){//可以右移动，可上移动
                    groups = groups.concat(['moveRight','moveUp']);
                }

                if(treeNode.next().length>0){//可下移
                    groups.push('moveDown');
                }

                //可左移动
                if(treeNode.parents('li.treeNode:not(.root):first').length){
                    groups.push('moveLeft');
                }
            }else{
                groups =groups.concat(this.rootNode[0].className.split(' '));
            }

            groups = groups.concat(this._getCommandGroups());
            this.element.trigger('activeTools',{groups:groups});
        },

        _commandChanged:function () {
            var selectedNode = this.element.tree('getSelected');
            this._activeToolGroups(selectedNode);
            this._setDirty();
        },

        _setDirty:function () {
            if(this.element.hasClass('dirty-field')){
                this.element.parents('.youi-form:first').attr('data-dirty','1');
            }
        },

        /*********************************************************/
        loadTreeNodes:function(params){
            $.youi.ajaxUtils.ajax({
                url:this.options.loadTreeNodesUrl,
                data:params,
                success:this._proxy('_parseTreeNodes')
            });
        },

        /**
         *
         */
        openXlsFileUpload:function () {
            if(this.options.refs&&this.options.refs.length>0){
                var fileUploadFormId = this.options.refs[0];
                $('#'+fileUploadFormId).subpage('open');
            }
        },

        /**
         *
         * @param treeNode
         */
        addFromTreeNode:function (treeNodeElem) {
            var clone = treeNodeElem.clone();
            var parentNode = this._getSelectedNode();
            if(!parentNode||!parentNode.length){
                parentNode = this.rootNode;
            }

            var ulElem = parentNode.find('>ul');
            if(ulElem.length==0){
                ulElem = $('<ul></ul>').appendTo(parentNode);
            }

            //全部的id
            var exsitTreeNodeIds = [];
            this.element.find('li.'+this.options.name).each(function () {
                exsitTreeNodeIds.push(this.getAttribute('id'));
            });

            _convertTreeNodeElems(clone.find('>ul>li'),this.options.name,exsitTreeNodeIds);

            clone.find('li:not(.added)').remove();

            ulElem.append(clone.find('>ul>li'));

            this._checkNodeStyle(parentNode);
        },

        setRoot:function (rootText,rootStyle) {
            this.element.find('li.root:first').addClass(rootStyle).find('>span>a').text(rootText);
        },

        /********************************   外部调用   **********************************/
        /**
         * 增加节点
         * @param dom
         * @param commandOption
         */
        addTreeNode:function (dom,commandOption) {
            var parentNode = this.element.tree('getSelected');
            if(!parentNode||!parentNode.length){
                parentNode = this.rootNode;
            }
            this.executeCommand('addNodeCommand',parentNode);
        },

        /**
         * 节点操作
         * @param dom
         * @param commandOption
         */
        nodeCommand:function (dom,commandOption) {
            var selectedNode = this._getSelectedNode();
            if(selectedNode&&commandOption.value){
                this.executeCommand(commandOption.value+'Command',selectedNode);
            }
        },

        /***************************   registered commands ******************************/
        /**
         * 添加节点
         */
        addNodeCommand:function (parentNode) {
            //
            var nodeId = this._genNodeId();
            var treeNodeModel = {
                id:nodeId,
                text:'节点'+nodeId
            }
            this.element.tree('addNode',parentNode,nodeId,
                _buildNodeText(treeNodeModel.text,this.options.name),{},
                _buildTreeData(treeNodeModel,this.options.name));

            return {
                nodeId:nodeId
            }
        },
        /**
         * 撤销添加节点
         */
        addNodeCommandUndo:function (params,execResult) {
            this.element.tree('removeNode',this._findTreeNode(execResult.nodeId));
        },

        /**
         * 删除节点
         * @param node
         */
        removeNodeCommand:function (node) {
            node.removeClass('selected').find('.selected').removeClass('selected');
            var result = {
                parentNode: node.parent().parent(),
                node:node.clone()
            }
            this.element.tree('removeNode',node);
            return result;
        },

        /**
         *
         * @param params
         * @param execResult
         */
        removeNodeCommandUndo:function (params,execResult) {
            var ulElem = execResult.parentNode.find('>ul:first');
            if(!ulElem.length){
                ulElem = $('<ul></ul>').appendTo(execResult.parentNode);
            }
            ulElem.append(execResult.node);
            this._checkNodeStyle(execResult.parentNode);
            this.element.tree('expandAll',execResult.parentNode);
        },

        /**
         *
         * @param treeNode
         */
        moveUpCommand:function (selectNode) {
            var prevNode = selectNode.prev('li.treeNode');
            if(prevNode.length){
                prevNode.before(selectNode);
                return selectNode;
            }
        },

        /**
         *
         * @param treeNode
         */
        moveUpCommandUndo:function (params,execResult) {
            if(execResult){
                execResult.next('li.treeNode').after(execResult);
            }
        },

        moveDownCommand:function (selectNode) {
            var nextNode = selectNode.next('li.treeNode');
            if(nextNode.length){
                nextNode.after(selectNode);
                return selectNode;
            }
        },

        moveDownCommandUndo:function (params,execResult) {
            if(execResult){
                execResult.prev('li.treeNode').before(execResult);
            }
        },

        /**
         *
         * @param selectNode
         */
        moveLeftCommand:function (selectNode) {
            var parentElement = selectNode.parents('li.treeNode.'+this.options.name+':first');
            if(parentElement.length){
                parentElement.after(selectNode);
                this._checkNodeStyle(parentElement);
            }
        },

        moveRightCommand:function (selectNode) {
            var prevNode = selectNode.prev('li.treeNode');

            if(prevNode.length){
                //增加temp节点
                this.element.tree('addNode',prevNode,'temp_','temp_');
                prevNode.find('>ul>li:last').remove();

                var ulElem = prevNode.find('>ul');
                if(ulElem.length==0){
                    ulElem = $('<ul></ul>').appendTo(prevNode);
                }
                ulElem.append(selectNode);
            }
        },
        /*****************************field相关****************************/

        getValue:function () {
            return _buildNodes(this.options,this.element.find('li.root>ul>li'),null,0);
        },

        /**
         * 设置值
         * @param treeNodes
         */
        setValue:function(treeNodes){
            this._buildTree(treeNodes,this.options.name);
        },

        reset:function(){
            this._buildTree();
            this.commands = [];//执行过的的命令数组，供撤销使用
            this.undoCommands = [];//撤销过的命令数组,供重做使用
        },

        /*****************************************************************/

        _checkNodeStyle:function (treeNode) {
            if(treeNode){
                if(treeNode.find('>ul>li').length==0){
                    treeNode.removeClass('expandable');
                }else{
                    treeNode.addClass('expandable');
                }
            }
        },
        /**
         *
         * @param id
         * @returns {*|{}}
         * @private
         */
        _findTreeNode:function (id) {
            return this.element.find('li.treeNode#'+id);
        },

        _getSelectedNode:function () {
            var selectedNode = this.element.tree('getSelected');
            return selectedNode.hasClass(this.options.name)?selectedNode:null;
        },

        /**
         * 生成节点ID
         * @returns {*}
         * @private
         */
        _genNodeId:function () {
            var nodeId = 'N'+this.UUID++;
            if(this.element.find('#'+nodeId).length){
                return this._genNodeId();
            }
            return nodeId;
        },
        /**
         *
         * @param result
         * @private
         */
        _parseTreeNodes:function (result) {
            this.commands = [];//执行过的的命令数组，供撤销使用
            this.undoCommands = [];//撤销过的命令数组,供重做使用
            this.setValue(result.records);
            this._resize();
            this._activeToolGroups();
        },

        _buildTree:function (treeNodes,treeNodeName) {
            var rootText = 'root';
            var ulElem = this.element.find('>ul>li.root>ul').empty();
            var htmls = [''];
            $(treeNodes).each(function () {
                htmls.push($.youi.treeUtils.treeNodeHtml(this.id,_buildNodeText(this.text,treeNodeName),{code:this.code},_buildTreeData(this,treeNodeName)));
            });
            htmls.push('</li>');

            ulElem.html(htmls.join(''));
        },

        _resize:function () {
            var height = this.element.offsetParent().parent().innerHeight();
            if(height>0&&this.element.hasClass('auto-height')){
                var fixedHeight = 0;
                this.element.parent().find('>.fixed-height:visible').each(function () {
                    fixedHeight += $(this).outerHeight();
                });
                this.element.height(height - fixedHeight - 35);
            }
        }

    }));

    /**
     *
     * @param text
     * @param treeNodeName
     * @returns {string}
     * @private
     */
    function _buildNodeText(text,treeNodeName){
        return '<span class="'+treeNodeName+' node-text" data-text="'+text+'" contenteditable=true>'+text+'</span><span class="tree-node-remove icon-remove"></span>';
    }

    /**
     *
     * @param treeModel
     * @param treeNodeName
     * @returns {{group: *, tooltips: *, expanded: boolean}}
     * @private
     */
    function _buildTreeData(treeModel,treeNodeName) {
        var treeData = {group:treeNodeName,tooltips:treeModel.code||treeModel.id,expanded:true};

        if(treeModel.children){
            treeData.children = _convertChildren(treeModel.children,treeNodeName);
        }
        return treeData;
    }

    /**
     *
     * @param children
     * @private
     */
    function _convertChildren(children,treeNodeName){
        var convertedChildren = [];
        $(children).each(function () {
            var child = $.extend(this,{text:_buildNodeText(this.text,treeNodeName),expanded:true,tooltips:this.code||this.id,group:treeNodeName});
            if(child.children&&child.children.length){
                child.children = _convertChildren(child.children,treeNodeName);
            }
            convertedChildren.push(child);
        });

        return convertedChildren;
    }


    /**
     *
     * @param treeNodeElems
     * @param pid
     * @returns {Array}
     * @private
     */
    function _buildNodes(options,treeNodeElems,pid,level) {
        var nodes = [];
        treeNodeElems.each(function () {
            var elem = $(this),
                node = {};
            node[options.idAttr] = elem.attr('id');
            node[options.pidAttr] = pid;
            node[options.textAttr] = elem.find('>span>a>.node-text:first').text();
            node[options.levelAttr] = level;

            var childrenElements = $('>ul>li.treeNode',this);

            if(childrenElements.length==0){
                node['leaf'] = 1;
            }

            nodes.push(node);

            nodes = nodes.concat(_buildNodes(options,childrenElements,
                elem.attr('id'),level+1));
        });
        return nodes;
    }

    function _convertTreeNodeElems(treeNodeElems,treeNodeName,exsitTreeNodeIds) {
        if(treeNodeElems){
            treeNodeElems.each(function(){
                var nodeElem = $(this);
                var code = nodeElem.data('code'),
                    treeTextElem = nodeElem.find('>span>a'),
                    text = treeTextElem.text();

                if(code){
                    nodeElem.attr('id',code);
                }else{
                    code = nodeElem.attr('id');
                }

                if($.inArray(code,exsitTreeNodeIds)==-1){
                    nodeElem.addClass('added');
                    treeTextElem.html(_buildNodeText(text,treeNodeName)).after('<span class="tree-node-hint">['+code+']</span>');
                    _convertTreeNodeElems(nodeElem.find('>ul>li'),treeNodeName,exsitTreeNodeIds);
                }

            });
        }
    }

})(jQuery);