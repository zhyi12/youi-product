/**
 * Created by zhouyi on 2019/7/23.
 */
(function($) {
    'use strict';

    $.youi = $.youi||{};

    $.extend($.youi,{

        /**
         * 设计器工具
         */
        designerUtils: {
            /**
             * 根据item的level生成树结构
             * @param items
             * @param treeNodeCallback
             * @returns {Array}
             * @private
             */
            buildItemTreeNodes:function (items,treeNodeCallback,level) {
                return _buildItemTreeNodes(items,treeNodeCallback,level||0);
            },

            /**
             * 使用树节点的层级自动生成level
             * @param treeNode
             * @returns {Array}
             */
            parseItemsFromTreeNode:function (treeNode,filter) {
                return _parseItemsFromTreeNode(treeNode,0,filter);
            },
            /**
             *
             * @param treeNode
             * @param filter
             * @returns {*}
             */
            parseTreeNodeItemList:function(treeNode,filter) {
                return _parseTreeNodeItemList(treeNode,filter);
            },

            getFormFieldStrValue:function (formRecord,property) {
                if(!formRecord || !property) return '';
                var value = formRecord[property];
                if($.isArray(value)){
                    if(value.length==0){
                        value.push('');
                    }
                    value = value[0];
                }
                return value;
            }
        },

        itemUtils:{
            /**
             * 在items中插入item
             * @param items
             * @param item
             * @param insertIndex
             * @returns {Array.<*>}
             */
            insertItem:function(items,item,insertIndex) {
                return [].concat(items.slice(0,insertIndex))
                    .concat([item])
                    .concat(items.slice(insertIndex,items.length));
            },

            /**
             * 删除item
             * @param items
             * @param removeIndex
             * @returns {Array.<*>}
             */
            removeItem:function(items,removeIndex) {
                var  updatingItems = [].concat(items.slice(0,removeIndex));
                if(removeIndex!=items.length-1){
                    updatingItems = updatingItems.concat(items.slice(removeIndex+1,items.length));
                }
                return updatingItems;
            },
            /**
             * 删除指定元素
             * @param items
             * @param itemId
             * @returns {Array}
             */
            removeItemById:function (items,itemId) {
                var updatingItems = [];
                for(var i=0;i<items.length;i++){
                    if(items[i].id != itemId){
                        updatingItems.push(items[i]);
                    }
                }
                return updatingItems;
            },

            /**
             * 查找item位置
             * @param items
             * @param itemIds
             * @returns {{}}
             */
            findItemIndexes:function(items,itemIds) {
                var indexes = {};
                if($.isArray(items) && $.isArray(itemIds)){
                    for(var i=0;i<items.length;i++){
                        if($.inArray(items[i].id,itemIds)!=-1){
                            indexes[items[i].id] = i;
                        }
                    }
                }
                return indexes;
            },

            /**
             *  根据父亲节点信息及子树节点数量获取树型结构的items的插入位置
             */
            findInsertIndex:function(items,parentId,childCount){
                var insertIndex;
                if(parentId){
                    var indexes = $.youi.itemUtils.findItemIndexes(items,[parentId]);
                    insertIndex = indexes[parentId]+childCount+1;
                }else{
                    insertIndex = items.length;
                }
                return insertIndex;
            }

        },

        sheetEditor:{

            insertCol:function (dom,commandOptions) {
                this._proxySheetAreaAction('insertCol');
            },

            appendCol:function (dom,commandOptions) {
                this._proxySheetAreaAction('appendCol');
            },

            appendBlankCol:function (dom,commandOptions) {
                this._proxySheetAreaAction('appendBlankCol');
            },

            deleteCol:function (dom,commandOptions) {
                this._proxySheetAreaAction('deleteCol');
            },

            setTextIndent:function (dom,commandOptions) {
                this._proxySheetCommandAction('setTextIndent',dom,commandOptions);
            },

            setAlign:function (dom,commandOptions) {
                this._proxySheetCommandAction('setAlign',dom,commandOptions);
            },

            setFontSize:function (dom,commandOptions) {
                this._proxySheetCommandAction('setFontSize',dom,commandOptions);
            },

            setBorder:function (dom,commandOptions) {
                this._proxySheetCommandAction('setBorder',dom,commandOptions);
            },

            mergeArea:function (dom, commandOptions) {
                this._proxySheetCommandAction('mergeArea',dom,commandOptions);
            },

            setSequence:function (dom, commandOptions) {
                this._proxySheetCommandAction('setSequence',dom,commandOptions);
            },

            clearArea:function (dom, commandOptions) {
                $.youi.messageUtils.confirm('确认清除选区文本信息?',this._proxy('_proxySheetCommandAction','clearAreaText',dom,commandOptions))
            },
            /**
             *
             */
            moveAreaText:function (dom, commandOptions) {
                var value = parseInt(commandOptions.value);
                var area = this.contentElem.sheet('getSelectedArea');
                this.contentElem.sheet('moveArea',area,value);
            },
            
            _proxySheetAreaAction:function(action){
                var area = this.contentElem.sheet('getSelectedArea');
                this.contentElem.sheet(action,area);
            },

            _proxySheetCommandAction:function(action,dom,commandOptions){
                var area = this.contentElem.sheet('getSelectedArea');

                if(commandOptions.value=='mergeCellText'){
                    var texts = this.contentElem.sheet('getSelectedTexts');
                    this.contentElem.sheet('setCellText',{rowIndex:area.startRow,colIndex:area.startCol,text:texts.join('')})
                }
                this.contentElem.sheet(action,dom,commandOptions,area);
            },

        },
        /**
         * 设计器页面组件抽象类
         */
        abstractDesigner:$.extend({},$.youi.command,{

            options:{
                useModelTree:true
            },

            /**
             *
             * @private
             */
            _initContent:function (contentCallback) {
                this.UUID = 0;
                this.contentElem = this.element.find('>.'+this.widgetName+'-content:first').addClass('auto-height');
                if($.isFunction(contentCallback)){
                    contentCallback(this.contentElem);
                }
            },

            /**
             * 初始化关联组件
             * @private
             */
            _initRefWidget:function () {
                this._initSubPages();//初始子页面
                this._initModelTree();
            },

            /**
             * 初始化模型树
             * @private
             */
            _initModelTree:function () {
                if(_existModelTree(this.options.useModelTree,this.options.refs)){
                    this.modelTreeElem = $('#'+this.options.refs[0]);
                    if(!this.modelTreeElem.hasClass('youi-tree')){
                        this.modelTreeElem = null;
                    }else{
                        this._bindTreeMenuAction();
                        if(!this.element.parents('.youi-treePage').length){
                            window[this.options.refs[0]+'_select'] = this._proxy('_treeNodeSelect');
                        }
                    }
                }
            },

            _treeNodeSelect:function (treeNode) {
                //console.log(treeNode);
            },
            /**
             *
             */
            _bindTreeMenuAction:function () {
                var bindMethods = [], xmenuId= this.modelTreeElem.data('xmenu');
                //获取右键菜单功能项
                xmenuId&&$('#'+xmenuId).find('li.youi-xmenuitem[data-name]').each(function () {
                    bindMethods.push($(this).data('name'));
                });

                for(var i = 0; i<bindMethods.length; i++){
                    var funcName = this.options.refs[0] + '_xmenu_' + bindMethods[i];
                    if (!$.isFunction(window[funcName])) {
                        window[funcName] = this._proxy(bindMethods[i]);
                        //约定open前缀的方法，并且对应的dialog存在，动态生成打开窗口方法
                        if(bindMethods[i].indexOf('open')==0){//以open开头的，默认绑定打开窗口
                            var subpageId = bindMethods[i].substring(4,5).toLocaleLowerCase()+bindMethods[i].substring(5);
                            if(this.subPages[subpageId]&&!$.isFunction(this[bindMethods[i]])){
                                this[bindMethods[i]] = this._proxy('openSubPageByMenu',subpageId);
                            }
                        }
                    }
                }
            },

            /**
             * 刷新模型树
             * @private
             */
            _refreshModelTree:function (treeNodes,attrsConfig) {
                if(!this.modelTreeElem || !this.modelTreeElem.length) {
                    return;
                }

                var rootUl = this.modelTreeElem.find('ul:first').empty();
                var treeHtmls = [];
                $(treeNodes).each(function () {
                    var attrs = {};
                    if(attrsConfig){
                        for(var attr in attrsConfig){
                            if(this[attr] && attrsConfig[attr]){
                                attrs[attrsConfig[attr]] = this[attr];
                            }
                        }
                    }
                    treeHtmls.push($.youi.treeUtils.treeNodeHtml(this.id,this.text,attrs,{children:this.children,group:this.group}));
                });

                rootUl.html(treeHtmls.join(''));
            },

            _treeNodeMoveDown:function () {
                var selectedNode = this.modelTreeElem.tree('getSelected');
                var nextNode = selectedNode.next('li.treeNode');
                if(nextNode.length){
                    nextNode.after(selectedNode);
                    return selectedNode;
                }
            },

            _treeNodeMoveUp:function () {
                var selectedNode = this.modelTreeElem.tree('getSelected');
                var prevNode = selectedNode.prev('li.treeNode');
                if(prevNode.length){
                    prevNode.before(selectedNode);
                    return selectedNode;
                }
            },

            /**
             * 左移-减少层级
             * @private
             */
            _treeNodeMoveLeft:function () {
                var selectedNode = this.modelTreeElem.tree('getSelected');
                var parentElement = selectedNode.parents('li.treeNode:first');
                if(parentElement.length){
                    parentElement.after(selectedNode);
                    _checkTreeNodeStyle(parentElement);
                }
            },
            /**
             * 右移-增加层级
             * @private
             */
            _treeNodeMoveRight:function () {
                var selectedNode = this.modelTreeElem.tree('getSelected');

                var prevNode = selectedNode.prev('li.treeNode');

                if(prevNode.length){
                    //增加temp节点
                    this.modelTreeElem.tree('addNode',prevNode,'temp_','temp_');
                    prevNode.find('>ul>li:last').remove();

                    var ulElem = prevNode.find('>ul');
                    if(ulElem.length==0){
                        ulElem = $('<ul></ul>').appendTo(prevNode);
                    }
                    ulElem.append(selectedNode);
                }
            },

            /**
             *
             * @private
             */
            _updateTreeNode:function (id,group,text,datas,tooltips) {
                if($.isArray(id)){
                    id = id[0];
                }

                var treeNode = this.modelTreeElem.find('.treeNode.'+group+'[data-id='+id+']');

                if(text){
                    treeNode.find('>span>a').text(text);
                }
                if(tooltips){
                    this._setTreeNodeToolTips(treeNode,tooltips);
                }

                datas = datas||{};

                for(var attr in datas){
                    var value = datas[attr];
                    if($.youi.stringUtils.notEmpty(value)){
                        var dataAttrName = 'data-'+$.youi.stringUtils.convertDataProperty(attr,'-');
                        treeNode.attr(dataAttrName,value).data(attr,value);
                    }
                }
                return treeNode;
            },

            _removeTreeNode:function (treeNode) {
                this.modelTreeElem.tree('removeNode',treeNode);
            },

            /**
             * 在当前节点后添加tooltips
             * @param tooltips
             * @private
             */
            _setTreeNodeToolTips:function (treeNode,tooltips) {
                tooltips = tooltips||'';
                var elem = treeNode.find('>.tree-span>span.tree-node-hint');
                if (elem.length!=0){
                    elem.text(tooltips);
                }else {
                    treeNode.find('>.tree-span').append("<span class='tree-node-hint'>"+tooltips+"</span>");
                }
            },

            _buildChildItem:function (itemRecord) {
                var record = {};
                if(itemRecord && itemRecord.id){//在指标节点增加子指标
                    record.parentId = itemRecord.id;
                    record.parentText = itemRecord.text;
                }
                return record;
            },

            _addItemTreeNode:function (item,group,styles,prefix) {
                var parentId = item.parentId,
                    parentNode,
                    childCount = 0,groups = ['item',group];//
                if(parentId){
                    parentNode = this.modelTreeElem.find('.treeNode.'+group+'[data-id='+parentId+']');
                    childCount = parentNode.find('.treeNode.'+group).length;
                }

                if(!parentNode || !parentNode.length) {
                    parentNode = this.modelTreeElem.find('.treeNode.'+group+'-parent:first');
                }
                //
                item.id = this._genUUID(prefix||'M',this.modelTreeElem);
                if(styles){
                    groups = groups.concat(styles);
                }

                this.modelTreeElem.tree('addNode',parentNode,item.id,item.text,{
                    'id':item.id,
                    'mapped-id':item.mappedId,
                    'expression':item.expression
                },{tooltips:item.mappedId,group:groups.join(' ')});

                return childCount;
            },

            _addTreeNode:function (parentNode,prefix,text,datas,options,id) {
                var nodePaths = [parentNode.attr('id')];
                this.modelTreeElem.tree('visitParents',parentNode,function (liDom) {
                    nodePaths.push(liDom.getAttribute('id'));
                });
                nodePaths.reverse();
                this.modelTreeElem.tree('openPath',nodePaths.join('/')+'/').tree('addNode',parentNode,(id||this._genUUID(prefix,this.modelTreeElem)),text,datas,options||{});
            },

            /**
             * 初始化子页面
             * @private
             */
            _initSubPages:function () {
                this.subPages = [];

                if($.isArray(this.options.refs)){
                    for(var i=0;i<this.options.refs.length;i++){
                        var subPageSplits = this.options.refs[i].split('_subpage_');//subpage约定的id规则
                        if(subPageSplits.length==2){
                            var subPageKey = subPageSplits[1];
                            this.subPages[subPageKey] = {
                                subPageId:this.options.refs[i]
                            };
                            var funcFormAfterSubmit = 'P_'+this.options.refs[i]+'_form_afterSubmit';
                            if(!$.isFunction(window[funcFormAfterSubmit])){
                                window[funcFormAfterSubmit] = this._proxy('_afterSubmitSubPage',{name:subPageKey});
                            }
                        }
                    }
                }
            },

            /**
             *
             * @param dom
             * @param commandOptions
             * @param subPageId
             */
            openSubPageByMenu:function (dom,commandOptions,subPageId) {
                var treeNode = $(dom),
                    parentNode = treeNode.parents('.treeNode:first'),
                    parentText = parentNode.data('id')?$.youi.treeUtils.getNodeText(parentNode):'';
                var record = $.extend({commandValue:commandOptions.value,text:$.youi.treeUtils.getNodeText(treeNode),parentText:parentText,id:treeNode.attr('id')},treeNode.data());
                var result = {record:record};
                this._openSubPage(subPageId,result,record);
            },

            /**
             * 打开子页面（弹出、第二页面两种类型）
             * @private
             */
            _openSubPage:function (subPageId,result,paramRecord) {
                if(this.subPages && this.subPages[subPageId]){
                    var subPageDomId = this.subPages[subPageId].subPageId;
                    $('#'+subPageDomId)
                        .unbind('initPageData')
                        .bind('initPageData',this._proxy('_initPageData',result,subPageId))
                        .subpage('open',{},{},$.extend({},paramRecord,{subPageId:subPageDomId}));
                }
            },

            _initPageData:function(event,ui,result,subPageId){
                if(result && ui && $.isFunction(window[ui.callback])){
                    var formRecord = null,
                        formRecordFuncName = '_'+subPageId+'FormRecord';
                    if($.isFunction(this[formRecordFuncName])){
                        formRecord = this[formRecordFuncName](result.record);
                    }

                    formRecord = $.extend({},ui.record,formRecord||result.record);

                    var formId = 'P_'+this.subPages[subPageId].subPageId+ '_form';//约定的表单ID

                    $('#'+formId).form('reset').form('fillRecord',formRecord);

                    window[ui.callback](formRecord);
                }
            },

            /**
             * 子页面约定id为form的表单提交后的回调函数
             * @param result
             * @param commandOptions
             * @private
             */
            _afterSubmitSubPage:function (result,commandOptions) {
                var subPageId = commandOptions.name;
                if(this.subPages && this.subPages[subPageId]){
                    var subPageDomId = this.subPages[subPageId].subPageId;
                    $('#'+subPageDomId).dialog('close');
                    var funcName = '_'+subPageId+'AfterFormSubmit';//
                    if($.isFunction(this[funcName])){
                        this[funcName](result.record);
                    }
                }
            },

            /**
             * 销毁设计器
             * @private
             */
            _destroyDesigner:function () {
                this.model = null;
                this.contentElem = null;
                this.modelTreeElem = null;
                this.subPages = [];
            },

            /**
             * 生成UUID
             * @param prefix
             * @private
             */
            _genUUID:function (prefix,container) {
                prefix = prefix||'';
                container = container||this.contentElem||this.element;
                var uuid = prefix+this.UUID++;
                var elem = container.find('#'+uuid);
                if(!elem.length){
                    return uuid;
                }
                return this._genUUID(prefix,container);
            },

            /**
             * 设置组件变化，页面刷新将提示
             */
            setDirty:function () {
                this.element.attr('data-dirty','1');
            },

            isDirty:function () {
                return '1'===this.element.attr('data-dirty');
            },

            /**
             * 消除组件变化
             */
            removeDirty:function () {
                this.element.removeAttr('data-dirty');
            },

            _resizeHeight:function () {
                var height = this.element.innerHeight();
                if(height>0){
                    var fixedHeight = 0;
                    this.element.find('>.fixed-height').each(function () {
                        fixedHeight += $(this).outerHeight();
                    });
                    this.element.find('>.auto-height').height(height - fixedHeight - 10);
                }
            }

        })

    });

    /**
     * 判断组件是否包含模型树
     * @param useModelTree
     * @param refs
     * @returns {*|boolean}
     * @private
     */
    function _existModelTree(useModelTree,refs) {
        return useModelTree&&
            $.isArray(refs)&& refs.length>0;
    }

    /**
     *
     * @param items
     * @returns {Array}
     * @private
     */
    function _buildItemTreeNodes(items,treeNodeCallback,topLevel) {
        //构建树
        var levelParentTreeNode = {};
        var treeNodes = [];

        $(items).each(function () {
            var treeNode = $.extend({},this);//
            if($.isFunction(treeNodeCallback)){
                treeNodeCallback(treeNode);
            }
            var level = !this.level?topLevel:parseInt(this.level);
            if(level==topLevel){
                treeNodes.push(treeNode);
            }else if(level>topLevel){
                var parentNode = levelParentTreeNode[(level-1)+''];
                if(parentNode){
                    parentNode.children = parentNode.children||[];
                    parentNode.children.push(treeNode);
                    parentNode.expanded = true;
                }
            }
            levelParentTreeNode[this.level] = treeNode;
        });
        return treeNodes;
    }

    function _parseItemsFromTreeNode(treeNode,level,filter) {
        var items = [],level = level||0;
        var fliterClass  = !filter?'':'.'+filter;
        $('>ul>li.treeNode'+fliterClass,treeNode).each(function () {
            var elem = $(this),
                item = $.extend({id:elem.attr('id')},elem.data(),{
                    text:$.youi.treeUtils.getNodeText(elem),
                    level:level
                });
            delete item.group;
            items.push(item);
            items = items.concat(_parseItemsFromTreeNode(elem,level+1,filter));
        });
        return items;
    }

    function _parseTreeNodeItemList(treeNode,filter) {
        var items = [],
            fliterClass  = !filter?'':'.'+filter;
        $('li.treeNode'+fliterClass,treeNode).each(function () {
            var elem = $(this),
                item = $.extend({id:elem.attr('id')},elem.data(),{
                    text:$.youi.treeUtils.getNodeText(elem)
                });
            delete item.group;
            items.push(item);
        });
        return items;
    }

    function _checkTreeNodeStyle(treeNode){
        if(treeNode){
            if(treeNode.find('>ul>li').length==0){
                treeNode.removeClass('expandable');
            }else{
                treeNode.addClass('expandable');
            }
        }
    }

})(jQuery);