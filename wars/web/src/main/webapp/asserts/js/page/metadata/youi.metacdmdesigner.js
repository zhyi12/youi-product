/*!
 * youi JavaScript Library v3.0.0
 *
 *
 * Copyright 2016-2020, zhyi_12
 * Dual licensed under the MIT or GPL Version 2 licenses.
 *
 * Date: 2019-9-2
 */
(function($) {

    var _COMMAND = "metaCdmDesignerCommand";
    /**
     * 概念模型设计器
     */
    $.widget("youi.metaCdmDesigner",$.youi.abstractWidget,$.extend({},$.youi.abstractDesigner,{

        options:{
            useModelTree:true,
            bindResize:true,
            getDataTablesUrl:'',//获取物理表集合url
            getDataTableColumnsUrl:'',//获取物理表列属性集合
            getModelTreeUrl:'',//模型树url
            getConceptDiagramTreeUrl:'',//模块子节点树url
            getDiagramContentUrl:'',//获取模型图html内容
            saveDiagramUrl:'',//保存模型图url
            removeConceptItemUrl:'',
        },
        /**
         *
         * @private
         */
        _initWidget:function () {
            this._initContent(function (contentElem) {
                contentElem.css({width:800,height:300}).html(_buildContentHtml()).flow({
                    overpanels:[
                        {name:'addRefNode',subname:'entity',groups:['node'],caption:'子实体'},
                        {name:'startSequence',subname:'startSequence',groups:['node'],caption:'线条'},
                        {name:'removeNode',groups:['node'],caption:'删除'},
                    ],
                    nodeHtml:function (id,text,nodeModel) {
                        return _buildNodeHtml(id,text,nodeModel||{});
                    },
                    change:function (event,ui) {
                        contentElem.parent().metaCdmDesigner('flowEditorChange',event.currentTarget,ui);
                    },
                    nodeDblClick:function (event,ui) {
                        contentElem.parent().metaCdmDesigner('openEditEntity',event.currentTarget,ui);
                    }
                });
            });
            this._initRefWidget();//初始化关联组件
            this._loadModelTree();//加载模型树
            this._bindWidgetCommand();//组件事件代理
            this._loadDataTables();//加载实体表列表
            this._resize();
        },

        _initAction:function () {
            this._on({
                'click [data-command="metaCdmDesignerCommand"]:not(.disabled)':function(event){
                    var elem = $(event.currentTarget);
                    var commandOptions = $.extend({ctrlKey:event.ctrlKey},elem.data());
                    this.execCommand(event.currentTarget,commandOptions);
                    event.stopPropagation();
                },
            });
        },
        /********************************************** 外部调用方法 **************************************************/
        toggleAttributes:function (dom,commandOptions) {
            var entityElem = $(dom).parents('.node.entity:first');
            entityElem.toggleClass('hide-attributes').css({
                width:'auto',height:'auto'
            });
            this.contentElem.flow('resize');
        },
        /**
         *
         * @param dom
         * @param commandOptions
         */
        openAddSubSystem:function (dom,commandOptions) {
            this._openSubPage('addSubSystem',{record:commandOptions},{});
        },

        /**
         *
         * @param dom
         * @param commandOptions
         */
        openEditEntity:function (dom,commandOptions) {
            var nodeElem = $(dom),record = $.extend({},commandOptions,nodeElem.data());
            delete record.name;
            if(nodeElem.is('.node')){
                record.id = nodeElem.attr('id');
                record.caption =  nodeElem.find('.entity-header:first').text();
                record.entityCode = nodeElem.find('.entity-identify:first').text();
                record.attributes = [];

                nodeElem.find('.entity-attribute').each(function () {
                    record.attributes.push(_parseAttributeRecord($(this)));
                });
            }
            this._openSubPage('editEntity',{record:record},{});
        },

        /**
         *
         * @param dom
         * @param commandOptions
         */
        flowEditorChange:function (dom,commandOptions) {
            if(commandOptions && $.isArray(commandOptions.groups)){
                var groups = ['module'].concat(commandOptions.groups);
                this.element.trigger('activeTools',{groups:groups});
            }
        },

        /**
         * 通过数据表增加实体
         */
        addEntityByTable:function (dom,commandOptions) {
            if(commandOptions && commandOptions.value){
                commandOptions.tableName = commandOptions.value;
                delete commandOptions.value;

                var tableId = commandOptions.tableId;
                //跳过加入已经存在的数据表
                if(tableId && this.contentElem.find('[data-table-id='+tableId+']').length){
                    $.youi.messageUtils.showMessage(commandOptions.tableCaption+'在模型图中已经存在.');
                    return;
                }
                $.youi.ajaxUtils.ajax({
                    url:$.youi.recordUtils.replaceByRecord(this.options.getDataTableColumnsUrl,{tableName:commandOptions.tableName}),
                    success:this._proxy('_addEntityByTableWithColumns',commandOptions)
                });
            }
        },

        undo:function () {
            this.contentElem.flow('undo');
        },

        redo:function () {
            this.contentElem.flow('redo');
        },

        /**
         * 树节点回调 - 删除节点
         */
        removeConceptItem:function (treeNode,commandOptions) {
            if(treeNode && treeNode.length){
                var text = $.youi.treeUtils.getNodeText(treeNode);
                $.youi.messageUtils.confirm('确认删除'+text+'?',this._proxy('_doRemoveConceptItem',treeNode.attr('id'),treeNode.data('id')));
            }
        },

        /**
         * 新实体
         * @param dom
         * @param commandOptions
         */
        newEntity:function (dom,commandOptions) {
            var nodeElement = this.contentElem.flow('createNode','entity','新实体');
            this.contentElem.flow('executeCommand','doAddNode',nodeElement);
        },

        /**
         * 保存
         * @param dom
         * @param commandOptions
         */
        save:function (dom,commandOptions) {
            var xml = this.contentElem.flow('getXml'),
                moduleId = this._getModuleId();
            if(moduleId && this.options.saveDiagramUrl){
                $.youi.ajaxUtils.ajax({
                    url:this.options.saveDiagramUrl,
                    data:{moduleId:moduleId,content:xml}
                });
            }
        },

        /********************************************** 内部私有方法 **************************************************/

        /**
         *
         * @private
         */
        _doRemoveConceptItem:function (treeNodeId,dataId) {
            if(this.options.removeConceptItemUrl){
                $.youi.ajaxUtils.ajax({
                    url:this.options.removeConceptItemUrl,
                    data:{id:dataId},
                    success:this._proxy('_afterRemoveConceptItem',treeNodeId)
                });
            }
        },

        /**
         *
         * @param result
         * @param treeNodeId
         * @private
         */
        _afterRemoveConceptItem:function (result,treeNodeId) {
            var treeNode = this.modelTreeElem.find('li#'+treeNodeId);
            this.modelTreeElem.tree('removeNode',treeNode);
        },
        /**
         *
         * @private
         */
        _loadDataTables:function () {
            if(this.options.getDataTablesUrl){
                $.youi.ajaxUtils.ajax({
                    url:this.options.getDataTablesUrl,
                    success:this._proxy('_parseDataTables')
                })
            }
        },

        _parseCdmContent:function (result,after) {
            this.contentElem.find('>.node,>.transition').remove();
            this.contentElem.append(result.record.html);
            this.contentElem.flow('resize');
            if($.isFunction(after)){
                after(this.modelTreeElem,_findTableTreeNodes(this.contentElem),_findRelationshipsTreeNodes(this.contentElem));
            }
        },

        /**
         *
         * @param result
         * @private
         */
        _parseDataTables:function (result) {
            var htmls = [];

            $(result.records).each(function () {
                htmls.push('<li class="youi-toolbarItem toolbar-item disabled" data-table-id="'+this.id+'" data-entity-code="'+(this.entityCode||'')+'" data-table-caption="'+this.tableCaption+'" data-groups="module" data-value="'+this.tableName+'" data-name="addEntityByTable"><a title="" data-command="toolbarCommand" href="#"><span class="youi-icon icon-table"></span>'+this.tableCaption+'</a></li>');
            });

            this.element.find('.dropdown.entity-repository:first>ul').html(htmls.join(''));
        },

        /**
         *
         * @param result
         * @param tableRecord
         * @private
         */
        _addEntityByTableWithColumns:function (result,tableRecord) {
            var nodeElement = this.contentElem.flow('createNode','entity',tableRecord.tableCaption,100,100,
                $.extend({},tableRecord,{'attributes':result.records}));
            this.contentElem.flow('executeCommand','doAddNode',nodeElement);
        },
        /**
         *
         */
        _loadModuleContent:function (moduleId,after) {
            if(moduleId && this.options.getDiagramContentUrl){
                $.youi.ajaxUtils.ajax({
                    url:this.options.getDiagramContentUrl,
                    data:{moduleId:moduleId},
                    success:this._proxy('_parseCdmContent',after)
                });
            }
        },
        /**
         *
         * @private
         */
        _getModuleId:function () {
            return this.element.find('[data-module-id]:first').data('moduleId');
        },

        /**
         *
         * @private
         */
        _loadModelTree : function () {
            if(this.options.getModelTreeUrl){
                $.youi.ajaxUtils.ajax({
                    url:this.options.getModelTreeUrl,
                    success:this._proxy('_parseModelTree')
                });
            }
        },

        /**
         * 解析树
         * @param result
         * @private
         */
        _parseModelTree:function (result) {
            if(!result || !$.isArray(result.records)){
                return;
            }
            var rootId = 'ROOT',rootText = '概念模型',getConceptDiagramTreeUrl = this.options.getConceptDiagramTreeUrl;
            //
            $(result.records).each(function () {
                $(this.children).each(function () {
                    if(getConceptDiagramTreeUrl){
                        this.src =  $.youi.parameterUtils.connectParameter(getConceptDiagramTreeUrl,'moduleId',this.id);
                    }
                });
            });

            var html = $.youi.treeUtils.treeNodeHtml(rootId,rootText,{group:'root'},{children:result.records});
            this.modelTreeElem.find('>ul:first').html(html);
        },

        /**
         *
         * @private
         */
        _treeNodeSelect:function (treeNode) {
            //模块
            if(treeNode.hasClass('module')){
                //设置标题
                var moduleCaption = $.youi.treeUtils.getNodeText(treeNode),
                    moduleId = treeNode.data('id');
                this.element.find('.module-caption:first').attr('data-module-id',moduleId).text(moduleCaption);
                this.element.trigger('activeTools',{groups:['module']});

                this._loadModuleContent(moduleId,function(modelTreeElem,entityNodes,relationshipsTreeNodes){
                    //如果未展开
                    if(!treeNode.find('li').length){
                        modelTreeElem.tree('expandAll',treeNode,function () {
                            if($(this).hasClass('entities')){
                                _expandTreeNode($(this),entityNodes);
                            }else if($(this).hasClass('relationships')){
                                _expandTreeNode($(this),relationshipsTreeNodes);
                            }
                        });
                    }
                });
            }
        },
        /********************************************** 窗口回调方法 **************************************************/
        /**
         *
         * @param record
         * @private
         */
        _addSubSystemAfterFormSubmit:function (record) {
            var parentNode = this.modelTreeElem.find('li.treeNode:first');
            this._addTreeNode(parentNode,'sub-system',record.caption,{'data-id':record.id},{group:'sub-system',icon:'th-large'});
        },

        /**
         *
         * @private
         */
        _addModuleFormRecord:function (record) {
            return {subSystemId:record.id};
        },

        /**
         *
         * @param record
         * @private
         */
        _addModuleAfterFormSubmit:function (record) {
            var parentNode = this.modelTreeElem.tree('getSelected');
            this._addTreeNode(parentNode,'module',record.caption,{'data-id':record.id},{group:'module',icon:'tasks',children:[{
                id:'DataItems'+record.id,
                text:'数据项',
                icon:'list'
            },{
                id:'Entities'+record.id,
                text:'实体',
                icon:'table'
            },{
                id:'Relationships'+record.id,
                text:'关联关系',
                icon:'link'
            }]});
        },
        /**
         *
         * @param record
         * @private
         */
        _editEntityAfterFormSubmit:function (record) {
            var tableId = $.youi.stringUtils.getStrValue(record.tableId),
                nodeId = $.youi.stringUtils.getStrValue(record.id);
            //
            var nodeElem = this.contentElem.find('.node.entity#'+nodeId);
            //
            nodeElem.find('.entity-header .table-caption').text(record.caption);
            nodeElem.find('.entity-identify').text(record.entityCode);

            nodeElem.attr('data-table-id',tableId).data('tableId',tableId)
                .attr('data-table-name',record.tableName).data('tableName',record.tableName);
            //写入属性
            nodeElem.find('.entity-attributes:first').html(_buildAttrsHtml(record.attributes));
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
        return htmls.join('');
    }

    /**
     * 构建entity 节点的html
     * @param id
     * @param text
     * @param nodeModel
     * @returns {string}
     * @private
     */
    function _buildNodeHtml(id, text, nodeModel) {
        var htmls = [];
        // '新实体'+this.UUID+'</div>'
        htmls.push('<div class="entity-header"><span data-name="toggleAttributes" data-command="'+_COMMAND+'" class="youi-icon icon-plus-sign"></span><span class="table-caption">'+$.trim(text)+'</span></div>');
        htmls.push('<div class="entity-attributes col-sm-12 no-padding">');

        if(nodeModel){
            htmls.push(_buildAttrsHtml(nodeModel.attributes));
        }
        htmls.push('</div>');
        htmls.push('<div class="entity-identify col-sm-12">'+(nodeModel.entityCode||'')+'</div>');
        return htmls.join('');
    }

    function _buildAttrsHtml(attributes) {
        var htmls = [];
        $(attributes).each(function (index) {
            var primaryStyle = this.primary=='1'?'primary':'';
            htmls.push('<div data-data-item-id="'+(this.dataItemId||'')+'" class="entity-attribute '+primaryStyle+'">');
            htmls.push('<span class="col-sm-6">'+(this.columnCaption||('COL'+(index+1)))+'</span>');
            htmls.push('<span class="col-sm-6">'+(this.dataItemId||'&nbsp;')+'</span>');
            htmls.push('</div>');
        });
        return htmls.join('');
    }

    /**
     *
     * @param attrElement
     * @private
     */
    function _parseAttributeRecord(attrElement) {
        var attrRecord = $.extend({columnCaption:attrElement.find('>span:first').text()},attrElement.data());
        return attrRecord;
    }

    /**
     *
     * @param contentElem
     * @private
     */
    function _findTableTreeNodes(contentElem) {
        var treeNodes = [];
        contentElem.find('>.node.entity').each(function () {
            var nodeElem = $(this);
            treeNodes.push({
                id:nodeElem.attr('id'),
                text:(nodeElem.find('.entity-identify:first').text()||'')+nodeElem.find('.entity-header:first').text(),
                icon:'table'
            });
        });
        return treeNodes;
    }

    function _findNodeCaption(nodeElem) {
        return nodeElem.find('.entity-header:first').text();
    }

    function _findNodeElem(contentElem,id) {
        return contentElem.find('.node#'+id);
    }

    function _findRelationshipsTreeNodes(contentElem) {
        var treeNodes = [];
        contentElem.find('>.transition').each(function () {
            var elem = $(this),record =elem.data();
            treeNodes.push({
                id:elem.attr('id'),
                text:_findNodeCaption(_findNodeElem(contentElem,record.sourceRef))+'_'+_findNodeCaption(_findNodeElem(contentElem,record.targetRef)),
                icon:'link'
            });
        });
        return treeNodes;
    }

    function _expandTreeNode(treeNode,children) {
        var ulElem = treeNode.find('>ul');
        if(ulElem.length==0){
            ulElem = $('<ul></ul>').appendTo(treeNode);
        }

        var htmls = [];

        $(children).each(function () {
            htmls.push($.youi.treeUtils.treeNodeHtml(this.id,this.text,{},this));
        });
        ulElem.html(htmls.join(''));

        treeNode.addClass('expanded expandable').prepend('<div class="tree-trigger"></div>');
    }
})(jQuery);