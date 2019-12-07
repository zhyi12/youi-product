/*!
 * youi JavaScript Library v3.0.0
 *
 *
 * Copyright 2016-2020, zhyi_12
 * Dual licensed under the MIT or GPL Version 2 licenses.
 *
 * Date: 2019-12-07
 */
(function ($) {
    'use strict';
    var _COMMAND = 'crossTableCommand',
        _POS_MAIN = 1,//主栏
        _POS_SLAVE = 2;//宾栏

    /**
     * 交叉表
     */
    $.widget("youi.crossTable", $.youi.abstractWidget, $.extend({}, {

        options: {
            maxMainColumns:1,//主栏最大列数
            filterDimensionIds:[],
        },

        canTransposition:true,//是否可以转置

        /**
         * 组件初始化
         * @private
         */
        _initWidget: function () {
            //初始化对象属性
            this.canTransposition = true;//是否可以转置
            this.slaveDimensions =[];//记录宾栏维度集合

            this._initContent();
            this._bindWidgetCommand();
        },

        /**
         * 初始化内容
         * @private
         */
        _initContent:function(){
            var contentClazz = this.widgetName+'-content';
            this.contentElem = this.element.find('>.'+contentClazz);
            if(this.contentElem.length==0){
                this.contentElem = $('<div class="'+contentClazz+'"></div>').appendTo(this.element);
            }
            this.contentElem.addClass('auto-height');
            //横向滚动条
            this.xScrollHelper = $('<div class="scroll-helper-x"><div class="scroll-helper-inner"></div></div>').insertAfter(this.contentElem);
            this.xScrollHelper.scroll(this._proxy('_xScroll'));
            //横向固定主栏层
            this.mainTableHelper = $('<div class="main-table-helper"></div>').appendTo(this.element);
         },

        _xScroll:function(event){
            var scrollLeft = $(event.currentTarget).scrollLeft();
            this.contentElem.css('marginLeft',scrollLeft*-1);
        },

        /**
         * 事件
         * @private
         */
        _initAction: function () {

        },

        /**
         * 前提：cubes的宾栏是相同的
         * 主栏、宾栏
         * @param cubes
         * @private
         */
        _parseCubes:function(cubes){
            if($.isArray(cubes) && cubes.length>0){
                this.canTransposition = cubes.length==1&&cubes[0].dimensions&&cubes[0].dimensions.length>1;//只有一个cube,至少包括两个维度时，支持转置
                this.filterDimensions = this.canTransposition?_findFilterDimensions(cubes[0],this.options.filterDimensionIds):[];//过滤的dimension,当能够转置时，也可以提取过滤的维度
                var skipCrossDimIds = _buildSkipCrossDimIds(this.filterDimensions);//不交叉的维度ID集合
                this.slaveDimensions = _findSlaveDimensions(cubes[0],skipCrossDimIds);//宾栏维度集合,只取第一个cube的宾栏维度集合
                var skipMainDimIds = [].concat(_buildSkipCrossDimIds(this.slaveDimensions)).concat(skipCrossDimIds),//宾栏维度ID集合
                    headerItems = cubes[0].headerItems||[],//公共头维度集合
                    mainColumns = 0,//主栏列数
                    expandedSlaveCrossItems = _expandCrossItems(this.slaveDimensions),//宾栏项展开
                    expandedMainCrossItemsArr = [],//主栏展开项
                    htmls = [];//主栏列数
                //主栏处理
                var rows = this.slaveDimensions.length;//初始化表格行数
                $(cubes).each(function () {
                    var mainCrossItems = _buildMainCrossItems(this,skipMainDimIds);
                    if(mainCrossItems.length){
                        rows+=mainCrossItems.length;//展开主栏增加表格行
                        expandedMainCrossItemsArr.push(mainCrossItems);
                        mainColumns = Math.max(mainColumns,mainCrossItems[0].length);//主栏最大列数
                    }
                });
                var cols = mainColumns+expandedSlaveCrossItems.length,
                    tableData = _buildTableData(rows,cols,mainColumns,cubes,expandedSlaveCrossItems,expandedMainCrossItemsArr);
                htmls.push(_buildTableHtmls(rows,cols,tableData));
                this.contentElem.html(htmls.join(''));

                //this.mainTableHelper
                var cloneTable = this.contentElem.find('>table').clone();
                cloneTable.find('td.cell.slave,td.cell.data').remove();
                this.mainTableHelper.empty().append(cloneTable);

                this._resize();//重新计算位置
            }else{
                //空cube
            }
            this.cubes = cubes||[];//记录cubes
            //
            this._trigger('afterParse',null,{cubes:this.cubes,canTransposition:this.canTransposition});
        },

        /*************************************** 方法 ****************************************/
        /**
         * 汇总交叉表
         * @param cubes
         */
        drawCubes:function(cubes){
            this._parseCubes(cubes);
        },

        /**
         * 转置交叉表
         */
        transposition:function(){
            if(this.canTransposition){
                //转置交叉表
            }
        },

        /**
         * resize
         * @private
         */
        _resize:function () {
            var width = this.element.width(),
                tableWidth = this.contentElem.find('>table').width();
            if(tableWidth>width){
                this.element.addClass('scroll-x');
                this.xScrollHelper.width(width);
                this.xScrollHelper.find('>.scroll-helper-inner').width(tableWidth);
            }else{
                this.element.removeClass('scroll-x');
            }
        }
    }));

    /**
     * 过滤维度,当dimensions大于2时，可以使用配置的过滤维度
     * @param cube
     * @param filterDimensionIds
     * @private
     */
    function _findFilterDimensions(cube,filterDimensionIds) {
        var filterDimensions = [];
        $(cube.dimensions).each(function () {
            if($.inArray(this,filterDimensionIds)!=-1){
                filterDimensions.push(this);
            }
        });
        var crossDims =  cube.dimensions.length - filterDimensions.length;//交叉表的维度数
        if(crossDims<2){
            filterDimensions = filterDimensions.slice(2-crossDims);
        }
        return filterDimensions;
    }
    /**
     * 查找主栏维度
     * @param cube
     */
    function _findSlaveDimensions(cube,skipIds) {
        var slaveDimensions = [];

        $(cube.dimensions).each(function () {
            if($.inArray(this.id,skipIds)==-1 && this.position==_POS_SLAVE){
                slaveDimensions.push(this);
            }
        });
        if(slaveDimensions.length==0){
            slaveDimensions = cube.dimensions.slice(1,cube.dimensions.length);
        }
        return slaveDimensions;
    }

    /**
     *
     * @param filterDimensions
     */
    function _buildSkipCrossDimIds(filterDimensions) {
        var skips = [];
        $(filterDimensions).each(function () {
            skips.push(this.id);
        });
        return skips;
    }

    /**
     * 展开维度
     * @param slaveDimensions
     * @private
     */
    function _expandCrossItems(dimensions) {
        var crossItems = [],
            dimensionCount = dimensions.length,
            columns = 1,
            spans = 1,
            spansList = [];
        //计算占位
        for (var i = dimensions.length; i > 0; i--) {
            if (i < dimensions.length) {
                spans = spans * (dimensions[i].items.length);
            }
            spansList.push(spans);
            columns = columns * dimensions[i - 1].items.length;
        }

        //遍历维度
        $(dimensions).each(function (index) {
            for (var i = 0; i < columns; i++) {
                var spans = spansList[dimensionCount - index - 1],
                    itemIndex = Math.floor(i / spans) % this.items.length,
                    item = this.items[itemIndex];
                crossItems[i] = crossItems[i]||[];
                crossItems[i].push($.extend({}, item, {dimId: this.id,spans:spans,itemCount:this.items.length}));
            }
        });
        return crossItems;
    }

    /**
     *
     * @param cube
     * @param skipMainDimIds
     * @private
     */
    function _buildMainCrossItems(cube,skipMainDimIds){
        var mainDimensions = [];
        $(cube.dimensions).each(function () {
            if($.inArray(this.id,skipMainDimIds)==-1){
                mainDimensions.push(this);
            }
        });
        return _expandCrossItems(mainDimensions);
    }

    /**
     *
     * @param rows 行
     * @param cols 列
     * @param mainColumns 主栏列数
     * @param cubes cube集合
     * @param expandedSlaveCrossItems 展开的宾栏
     * @param expandedMainCrossItemsArr 展开的主栏数组
     * @private
     */
    function _buildTableData(rows,cols,mainColumns,cubes,expandedSlaveCrossItems,expandedMainCrossItemsArr){
        if(!expandedSlaveCrossItems[0]){
            return [];
        }

        var slaveRows = expandedSlaveCrossItems[0].length,
            tableData = [],
            slaveColumns = expandedSlaveCrossItems.length;
        //公共区域和宾栏
        for(var row=0;row<slaveRows;row++){
            tableData.push(_buildPubAndSlaveRowData(expandedSlaveCrossItems,row,mainColumns,slaveColumns));
        }
        //主栏和数据区域
        $(expandedMainCrossItemsArr).each(function () {
            tableData = tableData.concat(_buildMainAndDataRowDatas(mainColumns,this,slaveColumns));
        });
        return tableData;
    }

    /**
     *
     * @param row
     * @param mainColumns
     * @param slaveColumns
     * @returns {Array}
     * @private
     */
    function _buildPubAndSlaveRowData(expandedSlaveCrossItems,row,mainColumns,slaveColumns) {
        var rowData = [],slaveDimCount = expandedSlaveCrossItems[0].length;
        for(var col=0;col< mainColumns;col++){
            rowData.push({cellType:'pub'});
        }
        for(var col=0;col< slaveColumns;col++){//宾栏行
            //行合并处理
            var cellItem = $.extend({cellType:'slave'},expandedSlaveCrossItems[col][row]);

            if(row<slaveDimCount && cellItem.spans>1 && col%cellItem.spans==0){
                cellItem.colspan = cellItem.spans;
            }else if(row<slaveDimCount && cellItem.spans && col%cellItem.spans!=0){
                cellItem.hide = true;
            }
            rowData.push(cellItem);
        }
        return rowData;
    }

    /**
     * 主栏和数据区
     * @param mainColumns
     * @param expandedMainCrossItems
     * @param slaveColumns
     * @private
     */
    function _buildMainAndDataRowDatas(mainColumns,expandedMainCrossItems,slaveColumns) {
        var rowDatas = [];
        $(expandedMainCrossItems).each(function (index) {
            var rowData = [];
            var crossItem = this;//交叉项集合
            //
            for(var i=0;i<crossItem.length;i++){
                //设置行合并
                var rowspan = 0,isHide = false;
                if(_isMergeItem(crossItem[i],index,i,crossItem[i].spans)){
                    rowspan = crossItem[i].spans;
                }else if(_isMergeHideCell(crossItem[i],index,i,crossItem[i].spans)){
                    isHide = true;
                }
                rowData.push($.extend({cellType:'main',hide:isHide,rowspan:rowspan},crossItem[i]));
            }

            if(crossItem.length<mainColumns){
                //设置主栏的列合并
                rowData[rowData.length-1].colspan = mainColumns - crossItem.length + 1;
                //补充空格
                for(var i=crossItem.length;i<mainColumns;i++){
                    rowData.push({cellType:'main',hide:true});
                }
            }

            //数据
            for(var i=0;i<slaveColumns;i++){
                rowData.push({cellType:'data'});
            }

            rowDatas.push(rowData);
        });
        return rowDatas;
    }

    /**
     * 是否为合并单元格的首单元格
     * @param item
     * @param i
     * @returns {boolean|*}
     * @private
     */
    function _isMergeItem(item,rowIndex,colIndex,count) {
        var isMerge =  colIndex<count-1 //最后一列主栏维度不需要行合并
            && item.spans>1 //合并的数量
            && item.itemCount //维度项总数
            && rowIndex%item.spans==0;
        return isMerge;
    }

    /**
     * 判断是否为合并的隐藏单元格
     * @param item
     * @param rowIndex
     * @param colIndex
     * @param count
     * @returns {boolean|*}
     * @private
     */
    function _isMergeHideCell(item,rowIndex,colIndex,count) {
        var isMerge =  colIndex<count-1 //最后一列主栏维度不需要行合并
            && item.spans>1 //合并的数量
            && item.itemCount //维度项总数
            && rowIndex%item.spans!=0;
        return isMerge;
    }

    /**
     *
     * @private
     */
    function _buildTableHtmls(rows, cols, tableData,cellType){
        var htmls = [];
        htmls.push('<table class="table">');
        for (var row = 0; row < rows; row++) {
            htmls.push('<tr class="' + (row % 2 == 1 ? 'odd' : 'even') + '">');
            var rowData = tableData[row]||[];
            for(var col=0;col<cols;col++){
                htmls.push(_buildCellHtmls(row,col,$.extend({cellType:cellType},rowData[col])));
            }
            htmls.push('</tr>');
        }

        htmls.push('</table>');
        return htmls.join('');
    }

    /**
     * 单元格
     * @param row
     * @param col
     * @param cellItem
     * @returns {string}
     * @private
     */
    function _buildCellHtmls(row,col,cellItem) {
        var htmls = [],cellStyles = ['cell',cellItem.cellType||''],attrHtmls = [];
        if(cellItem.hide){//设置隐藏样式
            cellStyles.push('hide');
        }
        if(cellItem.rowspan>1){//行合并
            attrHtmls.push('rowspan="'+cellItem.rowspan+'"');
        }
        if(cellItem.colspan>1){//列合并
            attrHtmls.push('colspan="'+cellItem.colspan+'"');
        }
        if(attrHtmls.length){//设置合并样式
            cellStyles.push('merge');
        }
        htmls.push('<td '+attrHtmls.join(' ')+' class="'+cellStyles.join(' ')+'">');
        htmls.push(cellItem.text||'');
        htmls.push('</td>');
        return htmls.join('');
    }

})(jQuery);