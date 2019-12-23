/**
 * Created by zhouyi on 2019/12/10.
 */
(function($) {

    var _LOG = $.youi.log,
        _MAIN_CELL_CLASS = 'main',
        _SLAVE_CELL_CLASS = 'slave',
        _POSITION_MAIN = 1,
        _POSITION_SLAVE = 2,
        _DIMENSION_INDICATOR = 'indicator',//指标维度
        _DIMENSION_ATTR = 'attr',//属性维度
        _DIMENSION_AREA = 'area',//行政区划维度
        _DIMENSION_PERIOD = 'period',//报告期维度
        _DIMENSION_YEAR = 'year',//年份维度
        _DIMENSION_MONTH = 'month',//月份维度
        _DIMENSION_QUARTER = 'quarter',//季度维度
        _DIMENSION_CATEGORY = 'category';//分类维度

    /**
     * 报表数据文件设计器
     */
    $.widget("youi.reportFileEditor", $.youi.abstractWidget, $.extend({}, $.youi.abstractDesigner,{

        options:{
            fileUrl:'',//获取数据文件内容
            getModelUrl:'',//获取报表文件模型
            saveModelUrl:'',//保存模型
            saveDataUrl:'',//保存数据
            bindResize:true,
            useModelTree:true,
        },

        /**
         * 组件初始化
         * @private
         */
        _initWidget:function () {
            this._initContent(function (contentElem) {
                contentElem.addClass('col-sm-12 no-padding').sheet({editable:true});
            });
            //关联组件初始化
            this._initRefWidget();

            this._bindWidgetCommand();

            if(this.options.fileUrl){
                this._loadSrc(this.options.fileUrl);
            }
        },

        /**
         *
         * @private
         */
        _initModel:function(){
            this.model = {
                mainAreas:[],//主栏区域数组 {id:'',text:'',dimensions:[{type:'indicator'}]}
                slaveAreas:[],//宾栏区域数组
            };
        },
        /**
         *
         * @private
         */
        _initAction:function(){

        },

        _loadSrc:function (src) {
            $.youi.ajaxUtils.ajax({
                url:src,
                success:this._proxy('_parseXlsReport')
            });
        },

        _parseXlsReport:function (result) {
            this.contentElem.sheet('loadSheetHtml',result.record.content);

            //刷新页面模型区域 getModelUrl
            this._loadModel();

            this._resize();
        },

        _loadModel:function(){
            if(this.options.getModelUrl){
                $.youi.ajaxUtils.ajax({
                    url:this.options.getModelUrl,
                    success: this._proxy('_parseModel')
                });
            }else{
                this._parseModel({});
            }
        },
        /**
         * 解析模型数据，生成模型树，同时标识sheet区域显示
         * @param result
         * @private
         */
        _parseModel:function(result){
            this.model = $.extend({},result.record.crossReport);
            this.model.mainAreas  = this.model.mainAreas||[];
            this.model.slaveAreas  = this.model.slaveAreas||[];

            var mainAreaNodes = [],slaveAreaNodes = [],headerNodes = [];

            if(this.model.headerItems){
                headerNodes = _buildItemNodes(this.model.headerItems,'','hi_');
            }

            if(this.model.mainAreas){//主栏
                for(var i=0;i<this.model.mainAreas.length;i++){
                    mainAreaNodes.push(_buildAreaNode(this.model.mainAreas[i],_MAIN_CELL_CLASS));
                    this._renderBlockArea(this.model.mainAreas[i],_MAIN_CELL_CLASS);
                }
            }

            if(this.model.slaveAreas){ //宾栏
                for(var i=0;i<this.model.slaveAreas.length;i++){
                    var slaveArea = this.model.slaveAreas[i];
                    slaveAreaNodes.push(_buildAreaNode(slaveArea,_SLAVE_CELL_CLASS));
                    this._renderBlockArea(slaveArea,_SLAVE_CELL_CLASS);
                }
            }

            var treeHtmls = [];
            treeHtmls.push($.youi.treeUtils.treeNodeHtml('header_node','表头项',{},{expanded:true,group:'header-items',children:headerNodes}));
            treeHtmls.push($.youi.treeUtils.treeNodeHtml(_MAIN_CELL_CLASS+'_node','主栏',{},{expanded:true,children:mainAreaNodes}));
            treeHtmls.push($.youi.treeUtils.treeNodeHtml(_SLAVE_CELL_CLASS+'_node','宾栏',{},{expanded:true,children:slaveAreaNodes}));

            this.modelTreeElem.find('>ul').html(treeHtmls.join(''));

            //
            this._activeDataSave();
        },

        _activeDataSave:function(){
            var groups = [];
            if(this.model.mainAreas && this.model.mainAreas.length
                && this.model.slaveAreas && this.model.slaveAreas.length){
                groups.push('data');
            }
            this.element.trigger('activeTools',{groups:groups});
        },

        _renderBlockArea:function(blockArea,type){
            this.contentElem.sheet('processArea',blockArea,function (record,cellElem) {
                cellElem.addClass(type).attr('data-'+type+'-area',blockArea.id);
            });
        },

        /**
         * 更新模型树
         * @private
         */
        _refreshModelTree:function(){
            //获取主栏区域
            //获取宾栏区域
        },

        _treeNodeSelect:function (treeNode) {
            var area = _parseAreaFromTreeNode(treeNode);
            if(area){
                this.contentElem.find('.selected').removeClass('selected');
                this.contentElem.sheet('processArea',area,function (record,cellElem) {
                    cellElem.addClass('selected');
                });
            }
        },

        /******************************************** action begin ******************************************/
        /**
         * 设置主栏区域
         */
        setMainArea:function(dom,commandOptions){
            this._setBlockArea(_MAIN_CELL_CLASS,_POSITION_MAIN,'主栏');
        },

        /**
         *
         * @param dom
         * @param commandOptions
         */
        setSlaveArea:function(dom,commandOptions){
            this._setBlockArea(_SLAVE_CELL_CLASS,_POSITION_SLAVE,'宾栏');
        },

        /**
         * 设置属性区域
         * @param dom
         * @param commandOptions
         */
        setAttrArea:function(dom,commandOptions){
            this._setMetaArea(_DIMENSION_ATTR,'属性');
        },

        setIndicatorArea:function(dom,commandOptions){
            this._setMetaArea(_DIMENSION_INDICATOR,'指标');
        },

        setPeriodArea:function(dom,commandOptions){
            this._setMetaArea(_DIMENSION_PERIOD,'报告期');
        },

        setCategoryArea:function(dom,commandOptions){
            this._setMetaArea(_DIMENSION_CATEGORY,'分类');
        },

        setAreaArea:function(dom,commandOptions){
            this._setMetaArea(_DIMENSION_AREA,'区域');
        },

        setMonthArea:function(dom,commandOptions){
            this._setMetaArea(_DIMENSION_MONTH,'月份');
        },

        setQuarterArea:function(dom,commandOptions){
            this._setMetaArea(_DIMENSION_QUARTER,'季度');
        },

        setYearArea:function(dom,commandOptions){
            this._setMetaArea(_DIMENSION_YEAR,'年份');
        },

        /**
         * 保存交叉表式
         */
        save:function(dom,commandOptions){
            var model = this._getModel();
            if(this.options.saveModelUrl){
                $.youi.ajaxUtils.ajax({
                    url:this.options.saveModelUrl,
                    data:$.youi.recordUtils.recordToAjaxParamStr({crossReport:model}),
                    success:function (result) {
                        //保存成功
                        $.youi.messageUtils.showMessage('保存成功');
                    }
                });
            }
        },

        /**
         * 保存数据
         */
        saveData:function(){
            var model = this._getModel(),
                datas = this._getDatas(model);//二维数组数据
            if(this.options.saveDataUrl) {
                $.youi.ajaxUtils.ajax({
                    url: this.options.saveDataUrl,
                    data: $.youi.recordUtils.recordToAjaxParamStr($.extend({}, model, {texts: datas})),
                    success: function (result) {

                    }
                });
            }
        },

        /**
         *
         * @param dom
         * @param commandOptions
         */
        showCrossTable:function(dom,commandOptions){
            var model = this._getModel(),
                cubes = _modelToCubes(model);
            this._openSubPage('crossTable',{record:{model:model,cubes:cubes}},{});
        },

        /**************************************  树右键菜单调用方法 **************************************/

        /**
         * 删除树节点
         * @param treeNode
         */
        removeTreeNode:function(treeNode){
            this._removeTreeNode(treeNode);
        },
        /**
         * 删除下方树节点
         * @param treeNode
         */
        removeNextAllNode:function(treeNode){
            var nextNode = treeNode.next('.treeNode');
            while(nextNode.length){
                var curNextNode = nextNode.next('.treeNode');
                nextNode.remove();
                nextNode = curNextNode;
            }
        },

        /**
         * 删除主栏区域
         * @param treeNode
         */
        removeMainArea:function(treeNode){
            this._removeBlockArea(treeNode,_MAIN_CELL_CLASS);
        },
        /**
         * 删除宾栏区域
         * @param treeNode
         */
        removeSlaveArea:function(treeNode){
            this._removeBlockArea(treeNode,_SLAVE_CELL_CLASS);
        },


        treeNodeMoveUp:function(treeNode){
            this._treeNodeMoveUp();
        },

        treeNodeMoveDown:function(treeNode){
            this._treeNodeMoveDown();
        },

        /**************************************** 弹出窗口回调方法 ***************************************/
        /**
         * 构建打开新增年份框填充的数据对象
         */
        _addPubYearFormRecord:function (record) {
            return this._addPubItemFormRecord(record);
        },
        /**
         * 新增公共年份成功后
         */
        _addPubYearAfterFormSubmit:function(record){
            var year = record.id.substr(0,4);
            this._addPubItemAfterFormSubmit($.extend({},record,{id:year,mappedId:year,text:year+'年',type:_DIMENSION_YEAR}));
        },

        _addPubIndicatorFormRecord:function (record) {
            return this._addPubItemFormRecord(record);
        },
        /**
         * 新增公共指标成功后
         */
        _addPubIndicatorAfterFormSubmit:function(record){
            this._addPubItemAfterFormSubmit($.extend({mappedId:record.id},record,{type:_DIMENSION_INDICATOR}));
        },
        /**
         * 窗口填充：新增公共项
         */
        _addPubItemFormRecord:function (record) {
            return {'parentId':record.id};
        },

        /**
         * 窗口提交：关联月份
         */
        _addPubItemAfterFormSubmit:function (record) {
            if(record.parentId){
                var parentNode = this._findTreeNodeById(record.parentId);
                this._addTreeNode(parentNode,'',record.text,{id:record.id,'mapped-id':record.mappedId||'','dim-id':record.type+'s'},{tooltips:record.mappedId||'',group:record.type+'-item'},record.parentId+record.id);
            }
        },

        /**
         * 关联月份
         */
        _refMonthAfterFormSubmit:function (record) {
            this._refItemAfterFormSubmit(record);
        },
        /**
         * 关联属性
         */
        _refAttrAfterFormSubmit:function (record) {
            this._refItemAfterFormSubmit(record);
        },
        /**
         * 关联报告期
         */
        _refPeriodAfterFormSubmit:function (record) {
            this._refItemAfterFormSubmit(record);
        },

        /**
         * 关联报告期维度
         */
        _refPeriodsAfterFormSubmit:function (record) {
            this._refItemsAfterFormSubmit(record);
        },

        _refItemsAfterFormSubmit:function (record) {
            var dimTreeNode = this._findTreeNodeById(record.id);
            for(var i=0;i<record.items.length;i++){
                var item = record.items[i];
                var itemNode = dimTreeNode.find('.treeNode[data-item-id='+item.id+']');
                if(item.mappedId){
                    itemNode.attr('data-mapped-id',item.mappedId).data('mappedId',item.mappedId);
                    itemNode.attr('data-item-id',item.mappedId).data('item',item.mappedId);
                    itemNode.attr('data-level',item.level).data('level',item.level);
                    this._setTreeNodeToolTips(itemNode,'['+item.mappedId+']');
                }
            }
        },
        /**
         * 窗口填充：关联维度报告期
         */
        _refPeriodsFormRecord:function (record) {
            return $.extend({items:this._parseItemsFromModelTree(record.id)},record);
        },

        //
        _refItemAfterFormSubmit:function (record) {
            var itemNode = this._findTreeNodeById(record.id);
            //
            this._setTreeNodeToolTips(itemNode,'['+record.mappedId+']');
            itemNode.data('mappedId',record.mappedId).attr('data-mapped-id',record.mappedId)
                    .data('itemId',record.mappedId).attr('data-item-id',record.mappedId);
        },


        _renameAfterFormSubmit:function(record){
            var treeNode = this._findTreeNodeById(record.id);
            if(treeNode){
                treeNode.find('>span>a').text(record.text);
            }
        },

        _findTreeNodeById:function(nodeId){
            return this.modelTreeElem.find('.treeNode#'+nodeId);
        },

        _processArea:function(callback){
            var area = this.contentElem.sheet('getSelectedArea');
            this.contentElem.sheet('processArea',area,callback);
            return area;
        },

        /**
         * 删除主栏宾栏区域块
         * @param treeNode
         * @param clazz
         * @private
         */
        _removeBlockArea:function(treeNode,clazz){
            var id = treeNode.attr('id');
            //删除显示样式
            this.contentElem.find('.'+clazz+'[data-'+clazz+'-area='+id+']').removeClass(clazz);
            //从模型删除
            this.model[clazz+'Areas'] = _removeAreaById(this.model[clazz+'Areas'],id);
            this._removeTreeNode(treeNode);
        },
        /**
         *
         * @param clazz
         * @param position
         * @param caption
         * @private
         */
        _setBlockArea:function(clazz,position,caption) {
            if(this._hasBlockArea()){
                $.youi.messageUtils.showMessage('该区域存在已经设置的单元格');
                return;
            }
            var blockAreaId = '', dataAttrName = "data-"+clazz,
                blockArea = this._processArea(function (cellRecord,cellElem,rowIndex,colIndex,curArea) {
                    if(rowIndex==curArea.startRow+1 && colIndex==curArea.startCol){
                        blockAreaId = 'B_'+rowIndex+'_'+colIndex;
                        cellElem.attr(dataAttrName,position);
                    }
                    cellElem.addClass(clazz).attr(dataAttrName+'-area',blockAreaId);
                });
            this.model[clazz+'Areas'].push($.extend({id:blockAreaId,dimensions:[]},blockArea));
            //增加宾栏树节点
            var children = [{id:blockAreaId+'Pub',text:'公共项',group:'pub-items'}];
            this.modelTreeElem.tree('addNode',this.modelTreeElem.find('.treeNode#'+clazz+'_node'),blockAreaId,caption+'区域'+blockAreaId,
                _buildAreaDataProp(blockArea),{group:clazz,expanded:true,children: children});
        },

        _hasBlockArea:function(){
            return this.element.find('.cell.selected.'+_MAIN_CELL_CLASS+',.cell.selected.'+_SLAVE_CELL_CLASS).length>0;
        },

        _setMetaArea:function(type,caption){
            //获取主栏或者宾栏
            var area = this.contentElem.sheet('getSelectedArea'),
                blockArea = _findBlockArea(this.model,area);

            if(!blockArea){//未找到块区域
                return;
            }
            var dimensionId = this._genUUID(blockArea.id+type+'s',this.modelTreeElem);
            var items = _buildMetaAreaItems(this.contentElem,area,blockArea,type),
                children = [];

            blockArea.dimensions = blockArea.dimensions||[];
            blockArea.dimensions.push($.extend({id:dimensionId,items:items,group:type},area));

            $(items).each(function () {
                children.push($.extend({group:type+'s-item',datas:{'dim-id':type}},this));
            });
            //
            var treeData = {children:children,group:type+'s',expanded:true};

            this.modelTreeElem.tree('addNode', this._findTreeNodeById(blockArea.id),dimensionId,caption,
                $.extend({group:type},_buildAreaDataProp(area)),treeData);
        },

        /**
         * 获取模型
         * @private
         */
        _getModel:function(){
            //主栏
            this._refreshDimensionItems(_MAIN_CELL_CLASS);
            //宾栏
            this._refreshDimensionItems(_SLAVE_CELL_CLASS);
            //headerItems
            this.model.headerItems = this._parseItemsFromModelTree('header_node');
            return this.model;
        },

        /**
         * 基于模型和sheet获取立方体数据
         * @param model
         * @private
         */
        _getDatas:function(model){
            //
            var datas = [],
                dataArea = {
                startRow:model.mainAreas[0].startRow,
                endRow:model.mainAreas[model.mainAreas.length-1].endRow,
                startCol:model.slaveAreas[0].startCol,
                endCol:model.slaveAreas[model.slaveAreas.length-1].endCol,
            };
            dataArea.rows = dataArea.endRow - dataArea.startRow +1;
            dataArea.cols = dataArea.endCol - dataArea.startCol +1;

            this.contentElem.sheet('processArea',dataArea,function (cellRecord,cellElem,rowIndex,colIndex,curArea){
                datas[rowIndex - curArea.startRow-1] = datas[rowIndex - curArea.startRow-1]||{texts:[]};
                datas[rowIndex - curArea.startRow-1].texts.push($.trim(cellRecord.text)||'-');
            });
            return datas;
        },
        /**
         *
         * @param block
         * @private
         */
        _refreshDimensionItems:function(block){
            var areas =  this.model[block+'Areas'];
            //主宾栏区块
            for(var i=0;i<areas.length;i++){
                var area = areas[i];
                area.text = this._findNodeText(area.id);
                area.pubItems = this._parseItemsFromModelTree(area.id+'Pub');

                if(area.dimensions){
                    var deletedDimensionIds = [];
                    for(var j=0;j<area.dimensions.length;j++){
                        var dimension = area.dimensions[j];
                        dimension.text = this._findNodeText(dimension.id);
                        dimension.items = this._parseItemsFromModelTree(dimension.id);
                        //
                        var dimensionNode = this._findTreeNodeById(dimension.id);

                        if(!dimensionNode||dimensionNode.length==0){
                            deletedDimensionIds.push(dimension.id);
                        }
                    }
                    var areaDimensions = [];
                    $(area.dimensions).each(function(){
                        if($.inArray(this.id,deletedDimensionIds)==-1){
                            areaDimensions.push(this);
                        }
                    });
                    //
                    area.dimensions = areaDimensions;
                }
            }
        },

        /**
         * 根据模型树的节点ID查找树节点文本
         * @param treeNodeId
         * @private
         */
        _findNodeText:function(treeNodeId){
            return $.youi.treeUtils.getNodeText(this._findTreeNodeById(treeNodeId));
        },

        /**
         * 在树的子节点中解析items
         * @param containerId
         * @returns {Array}
         * @private
         */
        _parseItemsFromModelTree:function(containerId){
            var items = [];
            this.modelTreeElem.find('.treeNode#'+containerId).find('.treeNode').each(function () {
                var elem = $(this);
                items.push($.extend({
                    id:elem.data('itemId')||elem.attr('id'),
                    text:$.youi.treeUtils.getNodeText(elem)
                },elem.data()));
            });
            return items;
        },

        /**
         *
         * @private
         */
        _resize:function () {
            this._resizePageHeight();
        }

    }));

    /**
     *
     * @param model
     * @param area
     * @private
     */
    function _findBlockArea(model,area) {
        //查找主栏区域
        for(var i=0;i<model.mainAreas.length;i++){
            var mainArea = model.mainAreas[i];
            if(mainArea.startRow == area.startRow
                && mainArea.endRow == area.endRow
                && area.startCol>=mainArea.startCol
                && area.endCol<=mainArea.endCol){
                //
                mainArea.position = _POSITION_MAIN;
                return mainArea;
            }
        }

        //查找宾栏区域
        for(var i=0;i<model.slaveAreas.length;i++){
            var slaveArea = model.slaveAreas[i];
            if(slaveArea.startCol == area.startCol
                && slaveArea.endCol == area.endCol
                && area.startRow>=slaveArea.startRow
                && area.endRow<=slaveArea.endRow){
                //
                slaveArea.position = _POSITION_SLAVE;
                return slaveArea;
            }
        }

        return null;
    }

    /**
     *
     * @param sheetElem
     * @param area
     * @param blockArea
     * @param type
     * @returns {Array}
     * @private
     */
    function _buildMetaAreaItems(sheetElem,area,blockArea,type) {
        var textsArr = [],items = [],position = blockArea.position;
        sheetElem.sheet('processArea',area,function (cellRecord,cellElem,rowIndex,colIndex,curArea) {
            var index = -1;
            if(_POSITION_MAIN == position){
                //在主栏区域
                index = rowIndex - curArea.startRow-1;
                position = _POSITION_MAIN;
            }else if(_POSITION_SLAVE == position){
                //在宾栏区域
                index = colIndex - curArea.startCol;
                position = _POSITION_SLAVE;
            }

            if(index>-1){
                textsArr[index] = textsArr[index]||[];
                textsArr[index].push($.trim(cellRecord.text));
            }
        });

        $(textsArr).each(function (index) {
            items.push({
                id:blockArea.id+(type||'I')+index,
                text:this.join('/')||'I'+index
            });
        });

        return items;
    }

    /**
     * 设置到dom对象的区域属性
     * @param area
     * @returns {{"start-col": number, "end-row": number, "start-row": number, "end-col": number}}
     * @private
     */
    function _buildAreaDataProp(area) {
        return {
            'start-row':area.startRow,
            'end-row':area.endRow,
            'start-col':area.startCol,
            'end-col':area.endCol,
        }
    }

    function _removeAreaById(areas,id) {
        var newAreas = [];
        for(var i=0;i<areas.length;i++){
            if(areas[i].id != id){
                newAreas.push(areas[i]);
            }
        }
        return newAreas;
    }

    function _parseAreaFromTreeNode(treeNode){
        var area = treeNode.data();
        if(area.startRow>-1 && area.startCol>-1 && area.endCol>-1 && area.endRow>-1){
            area.rows = area.endRow - area.startRow+1;
            area.cols = area.endCol - area.startCol+1;
            return area;
        }
        return null;
    }

    /**
     *
     * @param blockArea
     * @private
     */
    function _buildAreaNode(blockArea,type) {
        //pubItems
        blockArea.pubItems = blockArea.pubItems||[];

        return {id:blockArea.id,text:blockArea.text||blockArea.id,group:type,expanded:true,datas:{
                    'start-row':blockArea.startRow,
                    'start-col':blockArea.startCol,
                    'end-row':blockArea.endRow,
                    'end-col':blockArea.endCol,
                },children:[{'id':blockArea.id+'Pub',expanded:true,text:'公共项',group:'pub-items',children:_buildItemNodes(blockArea.pubItems,'',blockArea.id+'Pub')}].concat(_buildDimensionNodes(blockArea.dimensions))};
    }

    /**
     *
     * @param dimensions
     * @private
     */
    function _buildDimensionNodes(dimensions) {
        var nodes = [];
        $(dimensions).each(function () {
            nodes.push({
                id:this.id,
                text:this.text||this.id,
                group:this.group+'s',
                datas:{
                    'start-row':this.startRow,
                    'start-col':this.startCol,
                    'end-row':this.endRow,
                    'end-col':this.endCol,
                },
                children:_buildItemNodes(this.items,this.group,this.id)
            });
        });
        return nodes;
    }

    function _buildItemNodes(items,group,prefix) {
        var nodes = [];
        prefix = prefix||'';
        $(items).each(function () {
            nodes.push({
                id:prefix+this.id,
                dimId:this.dimId||'',
                tooltips:this.mappedId||'',
                datas:{'item-id':this.id,'mapped-id':this.mappedId,'dim-id':this.dimId||'',level:this.level},
                group:(this.dimId||group)+'s-item',
                text:this.text||this.id
            });
        });
        return nodes;
    }

    /**
     * 模型转立方体集合，只输出一个cube，主宾栏多项合并
     * @private
     */
    function _modelToCubes(model) {
        return [{dimensions:[
            {id:'main',items:_buildCubeItems(model.mainAreas,'M')},
                {id:'slave',items:_buildCubeItems(model.slaveAreas,'S')}]}];

    }

    /**
     * 合并区域集合的维度项为一个维度（主、宾）
     * @param areas
     * @param type
     * @returns {Array}
     * @private
     */
    function _buildCubeItems(areas,type) {
        var crossItems = [],items = [];
        $(areas).each(function () {
            crossItems = crossItems.concat($.youi.crossTableUtils.expandCrossItems(this.dimensions));
        });
        //
        $(crossItems).each(function (index) {
            var texts = [];
            $(this).each(function () {
                texts.push(this.text);
            });
            items.push({
                id:type+index,
                text:texts.join('/')
            });
        });
        return items;
    }
})(jQuery);