/*!
 * youi JavaScript Library v3.0.0
 *
 *
 * Copyright 2016-2020, zhyi_12
 * Dual licensed under the MIT or GPL Version 2 licenses.
 *
 * Date: 2016-1-4
 */
(function($) {
//	var _log = $.youi.log;
    /**
     *  sheet 组件
     * @author  zhouyi
     * @version 1.0.0
     */
    var _CELL_STYLE = 'cell',
        _CELL_EDITING_STYLE = 'cell-editing',
        _VALUE_STYLE = 'value-cell',
        _CLICKED_STYLE = 'cell-click',
        _SELECTED_STYLE = 'selected',
        _SELECT_HELPER_STYLE = 'select-helper',
        _MODE_HORIZONTAL = 'horizontal',
        _MODE_VERTICAL = 'vertical'

    $.widget("youi.sheet",$.youi.abstractWidget,$.extend({},{
        options:{
            bindResize:true,
            width:800,
            editable:false,
            mode: _MODE_HORIZONTAL,
            expression: 'records[{recordName}][{recordIndex}].items[{itemIndex}].values[valueIndex]'
        },

        _initWidget:function(){
            this._mouseInit();
            this.tableDom = this.element.find('table')[0];
            this.element.addClass('page-editor');
            var xmenu = this.options.xmenu||(this.options.id+'_xmenu'),
                xmenuElem = $('#'+xmenu);
            if(xmenuElem.length){
                this.pasteHelper = $('<div class="paste-helper" style="position:absolute;z-index: -1;left:0px;top:0px;" contenteditable="true"></div>').insertBefore(this.element);
                this.options.xmenu = xmenu;
                new ClipboardJS(xmenuElem.find('.xmenu-item[data-name=copy]')[0], {
                    text:this._proxy('_findCopyAreaText')
                });
                this.pasteHelper.bind('paste',this._proxy('_paste',this.options.customPaste));

                this.pasteHelper.bind('keypress',this._proxy('_copy'));
            }
        },

        _copy:function (event) {
            if(event.ctrlKey&&event.keyCode==3){//ctrl+c
                //通过快捷键触发copy操作
                $('#'+this.options.xmenu).find('.xmenu-item[data-name=copy]').trigger('click');
            }
        },
        /**
         * 拷贝内容
         * @param trigger
         * @private
         */
        _findCopyAreaText:function (trigger) {
            return this.getSelectedTexts(true).join('');
        },

        _paste:function(event,customPaste){
            if(event&&event.originalEvent){
                $(event.currentTarget).empty();
                var clipboardData = event.originalEvent.clipboardData;
                if (!clipboardData) { // for IE
                    clipboardData =  window.clipboardData;
                }
                var text = clipboardData.getData("text");
                if(text){
                    var texts = text.replace(/\n|\r/g,'\t').split('\t');
                    this.element.find('.'+_CELL_STYLE+'.'+_SELECTED_STYLE).each(function (index) {
                        var cellElem = $(this);
                        if($.trim(texts[index])!=null && !cellElem.hasClass('readonly')){
                            if($.isFunction(customPaste)){
                                customPaste(cellElem,texts[index]);
                            }else {
                                cellElem.text(texts[index]);
                            }
                        }
                    });
                }
            }
        },

        /**
         * 启动拖动
         */
        _mouseStart:function(event){
            var dragElem = $(event.target);
            if(dragElem.hasClass(_CELL_STYLE)){
                this._clearSelectedArea();
                this.dragElem = dragElem;
                this.helper = this._createHelper();
            }else if(dragElem.is('span.cell-text')){

            }else if(dragElem.hasClass('col-resize-handler')){//列宽度调整
                this.dragElem = dragElem.parent();
                this.colResizeHelper = this._getResizeHelper().css({
                    left:this.dragElem[0].offsetLeft+this.element.find('.pubHeader').outerWidth(),
                    width:this.dragElem.outerWidth(),
                    height:this.element.innerHeight()-10
                }).show();
            }

        },
        /**
         * 拖动过程中
         */
        _mouseDrag:function(event){
            var dropElem = $(event.target);
            if(this.colResizeHelper){
                var pos = this._computeHelperPos(event);
                this.colResizeHelper.width(pos.width-25);
            }else if(this.dragElem){//拖动选择区域　
                var pos = this._computeHelperPos(event);
                this.helper.css(pos);

                if(dropElem.hasClass(_CELL_STYLE)){
                    this.dropElem = dropElem;
                }

                var scrollTop = this.helper[0].offsetTop + this.helper.height() - this.element.height(),
                    scrollLeft = this.helper[0].offsetLeft + this.helper.width() - this.element.width();
                if(scrollTop>0&&this.element.scrollTop()<scrollTop&&scrollTop<$(this.tableDom).height() - this.element.height()+50){
                    this.element.scrollTop(scrollTop);
                }
                if(scrollLeft>0&&this.element.scrollLeft()<scrollLeft){
                    this.element.scrollLeft(scrollLeft);
                }
            }
        },
        /**
         * 拖动停止
         */
        _mouseStop:function(event){
            this._colResizeStop();
            if(this.dragElem&&this.dropElem){
                this._showSelectedArea(this.dragElem,this.dropElem);
            }
            this.helper&&this.helper.hide();
            this.dropElem = null;
            this.dragElem = null;
        },
        /**
         * 停止列拖动
         * @private
         */
        _colResizeStop:function () {
            if(this.colResizeHelper){
                this.dragElem.width(Math.max(this.colResizeHelper.width()+10,20));
                this.colResizeHelper.hide();
                this.colResizeHelper = null;
            }
        },
        /**
         * 获取列宽度或者行高度调整的辅助元素
         * @param type
         * @private
         */
        _getResizeHelper:function (type) {
            var className = (type||'col')+'-resize-helper';
            var resizeHandler = this.element.find('>.'+className);
            if(!resizeHandler.length){
                resizeHandler = $('<div class="'+className+'">').appendTo(this.element);
            }
            return resizeHandler;
        },

        /**
         *
         * @param startCell
         * @param endCell
         * @private
         */
        _showSelectedArea:function(startCell,endCell){
            var cellArea = this._cellAreaIter(startCell,endCell);//获取区域
            this._selectCellsByArea(cellArea);
            this.pasteHelper&&this.pasteHelper.focus();
        },

        /**
         *
         * @param cellArea
         * @private
         */
        _selectCellsByArea:function (cellArea) {
            var mergedIds = [];
            //查找区域内合并的单元格
            this._cellIter(cellArea,function (cellElem) {
                var mergeId = '';
                if(cellElem.hasClass('merged-cell')){
                    mergeId = cellElem.attr('id');
                }else if(cellElem.hasClass('hide')){
                    mergeId = cellElem.attr('data-merge-id');
                }
                if(mergeId&&$.inArray(mergeId,mergedIds)===-1){
                    mergedIds.push(mergeId);
                }
            });
            if(mergedIds.length){
                this._showSelectedAreaWithMerge(cellArea,mergedIds);
            }else{
                this._addAreaSytle(cellArea,_SELECTED_STYLE);
            }
        },

        /**
         *
         * @param cellArea
         * @param mergedIds
         * @private
         */
        _showSelectedAreaWithMerge:function (cellArea,mergedIds) {
            if(mergedIds.length>0){
                var startRow = cellArea.startRow,
                    endRow = cellArea.endRow,
                    startCol = cellArea.startCol,
                    endCol = cellArea.endCol;

                for(var i=0;i<mergedIds.length;i++){
                    var mergeCell = this.element.find('#'+mergedIds[i]);

                    if(mergeCell.length===0){
                        continue;
                    }

                    var	rowIndex = mergeCell.parent().prevAll().length,
                        colIndex = mergeCell.prevAll().length,
                        rows = parseInt(mergeCell.attr('rowspan')),
                        cols = parseInt(mergeCell.attr('colspan'));

                    startRow =  Math.min(startRow,rowIndex);
                    endRow = Math.max(endRow,rowIndex+rows-1);
                    startCol = Math.min(startCol,colIndex);
                    endCol = Math.max(endCol,colIndex+cols-1);
                }
                //通过合并单元格扩展的区域
                var extendedArea = {
                    startRow:startRow,
                    endRow:endRow,
                    rows:endRow-startRow+1,
                    startCol:startCol,
                    endCol:endCol,
                    cols:endCol - startCol+1
                };

                if(this._needReSelectedArea(cellArea,startRow,endRow,startCol,endCol)){
                    //执行区域选择
                    this._selectCellsByArea(extendedArea);
                }else{
                    this._addAreaSytle(extendedArea,_SELECTED_STYLE);
                }
            }
        },

        /**
         * 判断是否需要重新进行区域选择设置
         * @param cellArea
         * @param startRow
         * @param endRow
         * @param startCol
         * @param endCol
         * @returns {boolean}
         * @private
         */
        _needReSelectedArea:function(cellArea,startRow,endRow,startCol,endCol){
            return cellArea.startRow!==startRow||cellArea.endRow!==endRow||cellArea.startCol!==startCol||cellArea.endCol!==endCol;
        },
        /**
         *
         * @returns {Array}
         */
        getSelectedTexts:function(splitLine){
            var selectedTexts = [];
            if(!this.tableDom){
                this.tableDom = this.element.find('table')[0];
            }
            if(this.tableDom){
                $('.cell.'+_SELECTED_STYLE,this.tableDom).each(function () {
                    var cellElem = $(this),
                        cellText = $.trim(cellElem.text());
                    //当选项存在下拉项时；获取下拉的选中项文本
                    if (cellElem.hasClass('dropdown')) {
                        var dropdownElem =  cellElem.find('span:first');
                        if (dropdownElem.length) {
                            cellText = $.trim(dropdownElem.text());
                        } else {
                            cellText = '';
                        }
                    }
                    if(splitLine){
                        if(cellElem.next('.'+_SELECTED_STYLE).length){
                            cellText = cellText+'\t';
                        }else {
                            cellText = cellText+'\n';
                        }
                    }
                    selectedTexts.push(cellText);
                });
            }
            return selectedTexts;
        },

        _getSelectedAreaTexts:function () {
            var areaTextArr = [[]];
            if(!this.tableDom){
                this.tableDom = this.element.find('table')[0];
            }
            if(this.tableDom){
                $('.cell.'+_SELECTED_STYLE,this.tableDom).each(function () {
                    var cellElem = $(this),
                        cellText = $.trim(cellElem.text());
                    //当选项存在下拉项时；获取下拉的选中项文本
                    if (cellElem.hasClass('dropdown')) {
                        var dropdownElem =  cellElem.find('span:first');
                        if (dropdownElem.length) {
                            cellText = $.trim(dropdownElem.text());
                        } else {
                            cellText = '';
                        }
                    }
                    var rowTexts = areaTextArr[areaTextArr.length-1];
                    rowTexts.push(cellText);
                    if(!cellElem.next('.'+_SELECTED_STYLE).length){
                        //换行
                        areaTextArr.push([]);
                    }
                });
            }
            return areaTextArr;
        },

        getSelectedRowTexts:function () {
            var texts = this.getSelectedTexts(true);
            return texts.join('').replace(/\t/g,'/').split('\n');
        },

        getSelectedColTexts:function (maxRow) {
            maxRow = maxRow||1;
            var textArr = this._getSelectedAreaTexts();
            var texts = [];

            var rows = Math.min(textArr.length -1,maxRow);

            for(var i=0;i<textArr[0].length;i++){
                var colTexts = [];
                for(var j=0;j<rows;j++){
                    colTexts.push(textArr[j][i]);
                }
                texts.push(colTexts.join(''));
            }
            return texts;
        },
        /**
         *
         */
        _getAreaRecords:function (area) {
            return this._cellAreaRecordsIter(area);
        },

        moveArea:function (area,step) {
            for(var rowIndex=area.startRow;rowIndex<=area.endRow;rowIndex++){
                var prevCell = $(this.tableDom.rows[rowIndex+1].cells[area.startCol-1]);
                var nextCell = $(this.tableDom.rows[rowIndex+1].cells[area.endCol+1]);

                if(step>0){
                    prevCell.before(nextCell);
                }else if (step<0){
                    nextCell.after(prevCell);
                }
            }
        },


        /**
         *
         * @param callback
         * @returns {Array}
         * @private
         */
        _cellAreaRecordsIter:function (area,callback) {
            var records = [];
            this._cellIter(area,function (td,rowIndex,colIndex,curArea) {
                var cellElem = $(td),
                    record = $.extend({
                        rowIndex:rowIndex,
                        colIndex:colIndex
                    },_getCellRecord(cellElem));

                if($.isFunction(callback)){
                    callback(record,cellElem,rowIndex,colIndex,curArea);
                }
                records.push(record);
            });
            return records;
        },

        /**
         * 遍历区域单元格
         */
        _cellAreaIter:function(startCell,endCell,callback){
            var startRowIndex = startCell.parent().prevAll().length,
                endRowIndex = endCell.parent().prevAll().length,
                startColIndex = startCell.prevAll().length,
                endColIndex = endCell.prevAll().length;

            var area = {
                startRow : Math.min(startRowIndex,endRowIndex),
                endRow : Math.max(startRowIndex,endRowIndex),
                startCol : Math.min(startColIndex,endColIndex),
                endCol : Math.max(startColIndex,endColIndex),
                rows : Math.abs(startRowIndex-endRowIndex)+1,
                cols : Math.abs(startColIndex-endColIndex)+1
            };
            if($.isFunction(callback)){
                this._cellIter(area,callback);
            }
            return area;
        },
        /**
         * 遍历区域单元格
         */
        _cellIter:function(area,callback){
            if(!this.tableDom){
                this.tableDom = this.element.find('table')[0];
            }

            if(this.tableDom&&$.isFunction(callback)){
                for(var i=0;i<area.rows;i++){
                    for(var j=0;j<area.cols;j++){
                        var rowIndex = i+area.startRow+1,
                            colIndex = j+area.startCol;
                        callback($(this.tableDom.rows[rowIndex].cells[colIndex]),rowIndex,colIndex,area);
                    }
                }
            }
        },

        cellIter:function(area,callback){
            this._cellIter(area,callback);
        },

        _clearSelectedArea:function(){
            this.element.find('.'+_CELL_STYLE+'.'+_SELECTED_STYLE).removeClass(_SELECTED_STYLE);
        },

        /**
         * 创建拖动元素
         */
        _createHelper:function(event){
            var helper = this.element.find('.'+_SELECT_HELPER_STYLE);
            if(helper.length==0){
                helper = $('<div class="'+_SELECT_HELPER_STYLE+'"></div>').appendTo(this.element);
            }else{
                helper.show();
            }
            return helper;
        },
        /**
         *
         * @param event
         * @returns {{left: *, top: *, width: number, height: number}}
         * @private
         */
        _computeHelperPos:function(event){
            var pageX = event.pageX,pageY = event.pageY;
            var offset = this.options.container?this.options.container.offset():this.element.offset();
            var dragOffset = this.dragElem.offset();

            var deltLeft = dragOffset.left>pageX?5:0,
                deltWidth = dragOffset.left>pageX?0:5,
                scrollLeft = this.element.scrollLeft(),
                scrollTop = this.element.scrollTop();

            return {
                left:Math.min(dragOffset.left,pageX) - offset.left+deltLeft+scrollLeft,
                top:Math.min(dragOffset.top,pageY) - offset.top+scrollTop,
                width:Math.abs(pageX - dragOffset.left)-deltWidth,
                height:Math.abs(pageY - dragOffset.top)
            }
        },
        /**
         *
         * @private
         */
        _initAction:function(){
            this._on({
                'click':function () {
                    this._closeEditor();
                },
                'mouseout .cell .editor:not(.select-value)':function (event) {
                    var editor = $(event.target),
                        cell = editor.parents('.cell:first');

                    var oldValue = cell.data('oldValue');

                    this._closeEditor();

                    var newValue = cell.data('value')||cell.text();

                    if((!oldValue&&newValue)||oldValue!=newValue){
                        cell.data('oldValue',newValue);
                        cell.trigger('cellChange',{oldValue:oldValue,value:newValue});
                    }
                },
                'click .cell .editor':function () {
                    return false;//阻止冒泡
                },
                'click .cell .editor.select-value':function (event) {
                    $(event.currentTarget).parents('.cell:first').trigger('click');
                    return false;//阻止冒泡
                },
                'cellValue .cell':function (event,ui) {
                    var cellElem = $(event.currentTarget);
                    if(ui.value||ui.value=='0'){
                        if(ui.text){
                            if (cellElem.hasClass('fieldSelect')) {
                                cellElem.data('value',ui.value).attr('data-value',ui.value).empty().append('<span data-value="'+ui.value+'" class="editor select-value">'+ui.text+'</span>');
                            } else {
                                cellElem.data('value',ui.value);
                                cellElem.text(ui.text);
                            }
                        }else{
                            cellElem.text(ui.value);
                        }
                    }
                },
                'click .cell .option-item':function (event) {
                    var itemElem = $(event.currentTarget),
                        cellElem = itemElem.parents('.cell:first');

                    var oldValue = cellElem.data('value'),
                        value = itemElem.data('value');

                    cellElem.data('value',value).attr('data-value',value).find('span.editor:first').text(itemElem.text());
                    cellElem.removeClass('open').removeClass(_CELL_EDITING_STYLE);

                    //radio-item
                    if(cellElem.is('[data-editor="fieldRadioGroup"]')){
                        //只读模式不可切换选项
                        if(this.options.editable !== true){
                            return false;
                        }
                        itemElem.addClass('icon-ok-circle').removeClass('icon-circle-blank');
                        cellElem.find('.option-item').not(itemElem).removeClass('icon-ok-circle').addClass('icon-circle-blank');
                    }

                    if((!oldValue&&value)||oldValue!=value){
                        cellElem.trigger('cellChange',{oldValue:oldValue,value:value});
                    }
                    return false;
                },
                'click .cell':function(event){
                    this._hiddenContextMenu();
                    var cellElem = $(event.target);
                    this._clickCell(cellElem);
                    if(this.options.editable===true && !cellElem.hasClass('readonly')){
                        this._openEditor(cellElem);
                    } else if (cellElem.hasClass('readonly')) {
                        this._closeEditor();//关闭
                    }
                    return false;//阻止冒泡
                },
                'click .rowHeader':function (event) {
                    this._selectedRow($(event.currentTarget).parent().prevAll().length+1);
                },
                'click .colHeader':function (event) {
                    this._selectedCol($(event.currentTarget).prevAll().length);
                },
                'click .pubHeader':function (event) {

                },
                'contextmenu .cell':function(event){
                    if(this.options.xmenu){
                        var cellElem = $(event.currentTarget);

                        if (cellElem.hasClass('catalog-value')) {
                            //不定长表自定义右键删除
                            cellElem.trigger('contextmenuCatalog', {pageY:event.pageY,pageX:event.pageX});
                            return false;
                        }

                        if(!cellElem.hasClass(_SELECTED_STYLE)){
                            this._clickCell(cellElem);
                        }
                        //渲染右键菜单
                        this._showContextmenu(event,cellElem);

                        return false;
                    }
                },
                'contextmenuAction .cell':function(event,ui){
                    //右键菜单动作监听
                    if(ui.name){
                        this.execCommand(event.currentTarget,{name:'xmenu_'+ui.name,event:event});
                    }
                }
            });
        },
        /**
         * 行选择
         * @param index
         * @private
         */
        _selectedRow:function (index) {
            this._clearSelectedArea();
            $(this.tableDom).find('tr:eq('+(index)+') td').addClass(_SELECTED_STYLE);
            this._selectAreaChanged();
        },
        /**
         *列选择
         * @param index
         * @private
         */
        _selectedCol:function (index) {
            this._clearSelectedArea();
            $(this.tableDom).find('tr').find('td:eq('+index+')').addClass(_SELECTED_STYLE);
            this._selectAreaChanged();
        },
        /**
         *  单击单元格
         * @param cellElem
         * @private
         */
        _clickCell:function(cellElem){
            this._clearSelectedArea();
            this.element.find('.'+_CELL_STYLE+'.'+_CLICKED_STYLE).not(cellElem).removeClass(_CLICKED_STYLE);
            cellElem.addClass(_CLICKED_STYLE).addClass(_SELECTED_STYLE);

            this._selectAreaChanged();
        },

        /**
         * 区域选择
         * @param cellArea
         * @param styleName
         * @private
         */
        _addAreaSytle:function(cellArea,styleName){
            this._cellIter(cellArea,function (cellElem) {
                cellElem.addClass(styleName);
            });
            this._selectAreaChanged();
        },

        /**
         * 区域选择变化事件
         * @private
         */
        _selectAreaChanged:function () {
            this._trigger('areaChange',{});
        },
        /**
         * 打开编辑框
         * @param cellDom
         * @private
         */
        _openEditor:function(cellElem){
            //todo 后续需要调整
            if(cellElem.hasClass(_CELL_EDITING_STYLE) || cellElem.hasClass('conditional-item')){
                if (cellElem.hasClass('fieldSelect')) {
                    this._closeEditor();//下拉项切换关闭
                }
                return;
            }

            this._closeEditor();//关闭上一个editor

            var oldValue = cellElem.data('value')|| cellElem.text();
            cellElem.addClass(_CELL_EDITING_STYLE).data('oldValue',oldValue);//使单元格处于编辑状态并记录原始值

            var editorName = cellElem.data('editor')||cellElem.attr('data-editor')||'fieldText';

            //TODO 扩展编辑器类型
            if('fieldText'==editorName){
                //101目录选择器选中后文本显示
                if (!cellElem.hasClass("catalog-value")) {
                    $('<input style="width:100%;" class="editor fieldText" value="'+$.trim(oldValue)+'"/>').appendTo(cellElem.empty()).focus();
                } else {
                    $('<input style="width:100%;" class="editor fieldText" data-value="'+cellElem.data('value')+'" value="'+cellElem.text()+'"/>').appendTo(cellElem.empty()).focus();
                }
            }else if('fieldRadioGroup'==editorName){
                $(_buildFieldRadioGroup({},cellElem.data('convert'),this.options.converts)).appendTo(cellElem.empty()).focus();
            }else if('fieldSelect'==editorName){
                cellElem.addClass('open');
                var itemElem = $('.editor.select-value:first',cellElem);
                var item = {value:itemElem.data('value'),text:itemElem.text()};
                if(cellElem.parent().prevAll().length>11){
                    cellElem.addClass('dropup');
                } else {
                    cellElem.addClass('dropdown');
                }
                $(_buildDropdownSelect(item,cellElem.data('convert'),this.options.converts)).appendTo(cellElem.empty()).focus();
            }else if('fieldAutocomplete'==editorName&&this.options.searchUrl){
                var editor = {
                    id : cellElem.data('editorId'),
                    key : cellElem.data('editorKey')
                };

                var oldText = cellElem.text();

                var searchUrl = $.youi.recordUtils.replaceByRecord(this.options.searchUrl,editor),
                    searchValueAttr = this.options.searchValueAttr||'id',
                    searchLabelAttr = this.options.searchTextAttr||'text';

                var ajaxOptions = {
                    url:searchUrl,
                    notShowLoading:true,
                    success:function (result) {
                        var items = [];
                        $(result.records).each(function () {
                            items.push($.extend({
                                value:this[searchLabelAttr],
                                label:this[searchLabelAttr]
                            },this));
                        });
                        this.response(items);
                    }
                };

                $('<input style="width:100%;" class="editor select-value fieldAutocomplete" data-value="'+oldValue+'" value="'+oldValue+'"/>').appendTo(cellElem.empty()).autocomplete({
                    position: { my : "right bottom", at: "right top" },
                    source:function( request, response ) {
                        if ( this.xhr ) {
                            this.xhr.abort();
                        }
                        this.xhr = $.youi.ajaxUtils.ajax($.extend({data:request,response:response},ajaxOptions));
                    },
                    select: this._proxy('_itemValueSelect',cellElem),
                    close:this._proxy('_closeEditorBySelect',$.extend({},{cellElem:cellElem,oldText:oldText}))
                });
            }
        },

        _itemValueSelect:function (event,ui,cellElem) {
            var inputElem = $(event.target);
            inputElem.attr('data-value',ui.item.value);
            ui.cellElem = cellElem;
            this._trigger('itemValueSelect',event,ui);
        },

        /**
         * 通过下拉项
         * @param event
         * @param ui
         * @private
         */
        _closeEditorBySelect:function (event,ui,value) {
            var inputElem = $(event.target);
            $(value.cellElem).data("oldText", value.oldText);
            if(!inputElem.data('value')){
                inputElem.val('');
            }
            this._closeEditor();
        },

        /**
         * 关闭编辑并设置值
         * @private
         */
        _closeEditor:function () {
            var editingElem = $('.'+_CELL_STYLE+'.'+_CELL_EDITING_STYLE,this.tableDom),
                oldText = editingElem.data('oldText')||'',
                inputEditor = $('input.editor',editingElem),
                newText = inputEditor.val()||'';

            if(editingElem.length){

                if(editingElem.find('.dropdown-menu').length){
                    editingElem.removeClass(_CELL_EDITING_STYLE).removeClass('open');
                    return;
                }

                //fieldAutocomplete 输入检索时 写入当前文本框文本
                if(editingElem.data('editor') === 'fieldAutocomplete' && oldText){
                    newText = oldText;
                }

                if(inputEditor.hasClass('select-value')){
                    //防止输入检索数据覆盖历史数据
                    if (editingElem.data('editor') !== 'fieldAutocomplete') {
                        newText = inputEditor.data('value')||'';
                    }
                }

                editingElem.removeClass(_CELL_EDITING_STYLE).empty().text(newText);
                if(newText!==oldText){
                    var record = $.extend({
                        rowIndex:editingElem.parent().prevAll().length,
                        colIndex:editingElem.prevAll().length
                    },_getCellRecord(editingElem));
                    this._trigger('cellTextChange',null,{record:record,oldText:oldText});
                }
            }
        },

        /**
         * 显示xmenu
         */
        _showContextmenu:function(event,cellElem){
            var menuElem = $('#'+this.options.xmenu);
            if(!menuElem.length)return;
            if(menuElem.hasClass("hidden")){
                menuElem.removeClass("hidden")
            }
            var pageOffset = menuElem.parents('.page-container:first').offset(),
                groups = cellElem[0].className.split(' ');

            if(this.element.find('.cell.'+_SELECTED_STYLE).length>1){
                groups.push('area');
            }
            //修改为完全依照鼠标在浏览器中的坐标，
            // 于layout.css中添加了.youi-gatherPage>.youi-xmenu{position: fixed;}

            menuElem.trigger('open',{
                bind:cellElem[0],
                left:event.pageX+10 - pageOffset.left,
                top:event.pageY+10 - pageOffset.top,
                groups:groups
            });
        },

        /**
         * 隐藏xmenu
         */
        _hiddenContextMenu:function(){
            $('#'+this.options.xmenu).hide();
        },

        /**
         * 加载默认单元格
         * @param cells
         * @param rows
         * @param cols
         */
        loadDatas:function(cells,rows,cols){
            //String.fromCharCode(97)
            var htmls = ['<table class="table">'];

            htmls.push(this._buildColHeaders(cols));
            htmls.push(this._buildBody(cells,rows,cols));

            htmls.push('</table>');

            this.element.find('table').remove();
            this.element.append(htmls.join(''));

            this.tableDom = this.element.find('table')[0];
            //合并单元格处理


        },
        /**
         * 填充数据记录
         * @param records
         */
        fillRecords:function (records) {
            if(!$.isArray(records)){
                return;
            }

            for(var i=0;i<records.length;i++){
                var record = records[i];
                this._fillCellByRecord(record);
            }
        },
        /**
         * 填充单元格
         * @param record
         * @private
         */
        _fillCellByRecord:function (record) {
            if(record&&record.rowIndex>-1&&record.colIndex>-1){
                var cellElem = $(this.tableDom.rows[record.rowIndex].cells[record.colIndex]);
                var tdHtml = _buildCellHtml(record);
                cellElem.after($(tdHtml)).remove();
            }
        },

        /**
         *
         */
        getActiveGroups:function () {
            //
            var groups = [];

            var selectedCount = this.element.find('.selected').length;

            if(selectedCount){
                groups.push('cell');
            }

            var mergedCell = this.element.find('.selected.merged-cell');

            if(mergedCell.length>0||selectedCount>1){
                groups.push('merge');
            }
            return groups;
        },

        /**
         * 加载保存的xml格式文本
         * @param xmlStr
         */
        loadXml:function (xmlStr) {
            var htmls = ['<table class="table">'];

            htmls.push(xmlStr);
            htmls.push('</table>');

            this.element.find('table').remove();
            this.element.append(htmls.join(''));

            this.tableDom = this.element.find('table')[0];
            var colWidthsValue = $('tbody',this.tableDom).data('colwidths');
            var colWidths = colWidthsValue.split(',');
            //写列序号
            $(this.tableDom).prepend('</thead>'+this._buildColHeaders(colWidths.length,colWidths)+'</thead>');
            //写行序号
            $('tbody>tr',this.tableDom).each(function (index) {
                $(this).prepend('<td class="rowHeader">'+(index+1)+'</td>');
            });

        },

        loadSheetHtml:function (html) {
            this.element.html(html);
            this.tableDom = this.element.find('table')[0];
        },

        /************************执行区域动作*********************/

        /**
         * 获取选区
         * @returns {*}
         */
        getSelectedArea:function (dom,options) {
            return this._selectedAreaIter();
        },

        /**
         *
         * @param cellElem
         * @returns {{id, text: *, class, rowspan, colspan, datas}}
         */
        getCellRecord:function (cellElem) {
            return $.extend({
                rowIndex : cellElem.parent().prevAll().length,
                colIndex : cellElem.prevAll().length
            },_getCellRecord(cellElem));
        },

        /**
         *
         * @param text
         * @param className
         */
        processCellRecord:function (cellRecord,callback) {
            var cellElem = $(this.tableDom.rows[cellRecord.rowIndex+1].cells[cellRecord.colIndex]);
            //cellElem.text(cellRecord.text);
            //cellElem[0].className = cellRecord['class'];
            if($.isFunction(callback)){
                callback(cellRecord,cellElem);
            }
        },

        /**
         * 遍历区域
         * @param dom
         * @param commandOptions
         * @param area
         * @returns {*|Array}
         */
        processArea:function (area,callback) {
            return this._cellAreaRecordsIter(area,callback);
        },

        /**
         * 设置文本
         * @param cellRecord
         */
        setCellText:function (cellRecord) {
            $(this.tableDom.rows[cellRecord.rowIndex+1].cells[cellRecord.colIndex]).text(cellRecord.text);
        },

        /**
         * 插入行
         * @param area
         */
        insertRow:function (area,rowRecords) {
            var cols = this.tableDom.rows[0].cells.length-1,
                htmls = [''],rowRecords=rowRecords||[],
                rowMerges = [],mergeIds = {},
                insertIndex = area.startRow+1,
                beforeRowElem = null;

            if(insertIndex<this.tableDom.rows.length-1){
                //TODO 处理合并单元格
                beforeRowElem = $(this.tableDom.rows[area.startRow+1]);
                $('td',beforeRowElem).each(function (index) {
                    var mergeId = $(this).attr('data-merge-id');
                    rowMerges[index] = mergeId;

                    if(mergeId&&!mergeIds[mergeId]){
                        var mergeCell = $('.cell#'+mergeId,this.parentNode.parentNode);
                        var rowspan = parseInt(mergeCell.attr('rowspan'));

                        mergeCell.attr('rowspan',rowspan+area.rows);
                        mergeIds[mergeId] = mergeId;
                    }
                });
            }

            for(var i=0;i<area.rows;i++){
                htmls.push('<tr class="rowTr"><td class="rowHeader"></td>');
                for(var j=0;j<cols;j++){
                    var record = $.extend({text:'','class':'cell',datas:{}},rowRecords[j]);

                    if(rowMerges[j]){
                        record['class'] = 'cell hide';
                        record.datas['mergeId'] = rowMerges[j];
                    }

                    htmls.push(_buildCellHtml(record));
                }
                htmls.push('</tr>');
            }

            //mergeCell 增加rowspan
            if(beforeRowElem){
                beforeRowElem.before($(htmls.join('')));
            }else{
                $('tbody',this.tableDom).append(htmls.join(''));
            }

            this._reBuildRowHeader();
            return area;
        },

        /**
         * 追加行
         */
        appendRow:function () {
            var cols = this.tableDom.rows[0].cells.length-1,rowHtmls = [];
            rowHtmls.push('<tr class="rowTr"><td class="rowHeader"></td>');
            for(var i=0;i<cols;i++){
                rowHtmls.push(_buildCellHtml({'class':'cell'}));
            }
            rowHtmls.push('</tr>');
            $('tbody',this.tableDom).append(rowHtmls.join(''));
            this._reBuildRowHeader();
        },

        reBuildRowHeader:function () {
            this._reBuildRowHeader();
        },
        /**
         *
         * @param area
         * @param rowHtmls
         */
        insertRowHtmls:function (area,rowHtmls) {
            var insertIndex = area.startRow+1;
            if(insertIndex<this.tableDom.rows.length-1){
                $(this.tableDom.rows[area.startRow+1]).before($(rowHtmls));
            }else{
                $('tbody',this.tableDom).append($(rowHtmls));
            }
            //处理合并单元格
            $('[data-old-rowspan]',this.tableDom).each(function () {
                var elem = $(this),
                    oldRowspan = parseInt(elem.attr('data-old-rowspan')),
                    mergeId = elem.attr('data-merge-id');
                elem.removeAttr('data-old-rowspan');

                $('#'+mergeId,elem.parents('tbody:first')).attr('rowspan',oldRowspan);

            });
            this._reBuildRowHeader();
        },

        /**
         *根据选择区域删除行
         * @param area
         */
        deleteRow:function (area) {
            var tempTable = $('<table></table>');

            for(var i=0;i<area.rows;i++){
                $(this.tableDom.rows[area.startRow+1]).appendTo(tempTable);
            }
            this._reBuildRowHeader();

            //处理合并单元格
            var mergeIds = {};
            tempTable.find('[data-merge-id]').each(function () {
                //合并单元格处理
                var mergeId = $(this).attr('data-merge-id');
                if(mergeId&&!mergeIds[mergeId]){
                    mergeIds[mergeId] = mergeId;
                }
            });

            for(var mergeId in mergeIds){
                var mergeCell = $('#'+mergeId,this.tableDom);
                var rowspan = parseInt(mergeCell.attr('rowspan')),
                    mergeEndRow = mergeCell.parent().prevAll().length+rowspan-1,
                    delt = 0;

                if(area.endRow>mergeEndRow){
                    delt = area.endRow - mergeEndRow;
                }

                mergeCell.attr('rowspan',rowspan - area.rows+delt);

                tempTable.find('[data-merge-id='+mergeId+']:first').attr('data-old-rowspan',rowspan);
            }

            var htmls = tempTable.find('tbody').html();
            tempTable.remove();

            return htmls;
        },

        /**
         * 插入列
         * @param area
         */
        insertCol:function (area,colRecords) {
            var rows = this.tableDom.rows.length,mergeIds = {},
                insertIndex = area.startCol,colRecords = colRecords||[];

            var mergeCells = {};
            for(var i=0;i<rows;i++){
                var insertColHtmls = [];
                var insertCellElem = $(this.tableDom.rows[i].cells[insertIndex]);

                var mergeId = insertCellElem.attr('data-merge-id');

                if(insertCellElem && mergeId){
                    var mergeCell = mergeCells[mergeId] || $('#'+mergeId,this.tableDom);
                    //在合并单元格内部插入
                    if(mergeCell.prevAll().length<=insertIndex){
                        if(!mergeCells[mergeId]){//设置合并单元格插入列后的列占位
                            var colspan = parseInt(mergeCell.attr('colspan'));
                            mergeCell.attr('colspan',colspan+area.cols);
                        }
                    }else{
                        mergeId = '';
                    }
                    mergeCells[mergeId] = mergeCell;
                }

                for(var j=0;j<area.cols;j++){
                    if(i===0){
                        insertColHtmls.push('<th class="colHeader" style="width:100px;"></th>');
                    }else{
                        var record = $.extend({text:'','class':'cell',datas:{}},colRecords[i]);
                        if(mergeId){
                            record['class'] = 'cell hide';
                            record.datas.mergeId = mergeId;
                        }
                        insertColHtmls.push(_buildCellHtml(record));
                    }
                }

                insertCellElem.before(insertColHtmls.join(''));
            }
            this._reBuildColHeader();
            return area;
        },

        appendCol:function () {
            $('thead>tr',this.tableDom).append('<th class="colHeader"></th>');

            $('tr.rowTr',this.tableDom).each(function () {
                $(this).append(_buildCellHtml({'class':'cell'}));
            });
            this._reBuildColHeader();
        },

        appendBlankCol:function () {
            //去掉table标签上的边框
            $(this.tableDom).addClass('tableNoBorder');

            $('thead>tr',this.tableDom).append('<th class="blankHead"></th>');

            $('tr.rowTr',this.tableDom).each(function () {
                $(this).append(_buildCellHtml({'class':'cell hide'}));
            });
            this._reBuildColHeader();
        },

        /**
         * 删除区域对应的列
         * @param area
         */
        deleteCol:function (area) {
            var rows = this.tableDom.rows.length,mergeIds = {},
                deletedColHtmls = [],
                deleteIndex = area.startCol;

            for(var i=0;i<rows;i++) {
                var tempRowElem = $('<tr></tr>')
                for(var j=0;j<area.cols;j++){
                    var deleteCellElem = $(this.tableDom.rows[i].cells[deleteIndex]),
                        mergeId = deleteCellElem.attr('data-merge-id');

                    if(mergeId&&!mergeIds[mergeId]){

                        var mergeCell = $('#'+mergeId,this.tableDom);
                        var colspan = parseInt(mergeCell.attr('colspan')),
                            mergeEndCol = mergeCell.prevAll().length+colspan-1,
                            delt = 0;

                        if(area.endCol>mergeEndCol){
                            delt = area.endCol - mergeEndCol;
                        }
                        mergeCell.attr('colspan',colspan - area.cols + delt);
                        deleteCellElem.attr('data-old-colspan',colspan);
                        mergeIds[mergeId] = mergeId;
                    }
                    tempRowElem.append(deleteCellElem);
                }
                //处理合并的单元格
                deletedColHtmls.push(tempRowElem.html());
                tempRowElem.remove();
            }
            this._reBuildColHeader();

            return deletedColHtmls;
        },
        /**
         *
         * @param area
         * @param deletedColHtmls
         */
        insertColHtmls:function (area,deletedColHtmls) {
            var rows = this.tableDom.rows.length,mergeIds = {},
                deleteIndex = area.startCol,
                isAppend = this.tableDom.rows[0].cells.length===deleteIndex;

            for(var i=0;i<rows;i++) {
                //如果删除的是末尾的列
                if(isAppend){
                    $(this.tableDom.rows[i]).append(deletedColHtmls[i]);
                }else{
                    $(this.tableDom.rows[i].cells[deleteIndex]).before(deletedColHtmls[i]);
                }

                //还原合并的单元格
                $('[data-old-colspan]',this.tableDom).each(function () {
                    var elem = $(this),
                        oldColspan = parseInt(elem.attr('data-old-colspan')),
                        mergeId = elem.attr('data-merge-id');
                    elem.removeAttr('data-old-colspan');

                    $('#'+mergeId,elem.parents('tbody:first')).attr('colspan',oldColspan);

                });
            }

            this._reBuildColHeader();
        },
        /**
         * 清空选区的单元格,并记录原始区域单元格数据信息
         */
        clearArea:function (dom,commandOptions,area) {
            return this._cellAreaRecordsIter(area,function (record,cellElem,rowIndex,colIndex) {
                cellElem.before($('<td class="'+_CELL_STYLE+' '+_SELECTED_STYLE+'"></td>')).remove();
            });
        },

        /**
         * 清空文本
         */
        clearAreaText:function (dom,commandOptions,area) {
            return this._cellAreaRecordsIter(area,function (record,cellElem,rowIndex,colIndex) {
                cellElem.text('');
            });
        },

        /**
         * 合并区域单元格
         */
        mergeArea:function (dom,commandOptions,area) {
            var hasMergedCell = false,
                records = this._cellAreaRecordsIter(area,function (record,cellElem,rowIndex,colIndex) {
                    if(cellElem.hasClass('merged-cell')||cellElem.is('[data-merge-id]')){
                        hasMergedCell = true;
                    }
                });
            if(hasMergedCell){//如果区域内存在已经合并的单元格，撤销合并
                this._cellAreaRecordsIter(area,function (record,cellElem,rowIndex,colIndex) {
                    if(cellElem.hasClass('merged-cell')){
                        var mergeId = cellElem.attr('id');
                        cellElem.removeClass('merged-cell').removeAttr('id').removeAttr('rowspan').removeAttr('colspan');
                        //显示hide的单元格
                        $('[data-merge-id='+mergeId+']',cellElem.parent().parent()).removeClass('hide').removeAttr('data-merge-id');
                    }
                });
            }else{
                //合并单元格
                var mergeId;
                this._cellAreaRecordsIter(area,function (record,cellElem,rowIndex,colIndex) {
                    if(area.startRow===rowIndex-1&&area.startCol===colIndex){
                        mergeId = 'm_'+rowIndex+'_'+colIndex+'_'+new Date().getTime();
                        cellElem.attr('id',mergeId).addClass('merged-cell').attr('rowspan',area.rows).attr('colspan',area.cols).css('textAlign','center');
                    }else{
                        cellElem.addClass('hide').attr('data-merge-id',mergeId);
                    }
                });
            }
            return records;
        },

        /**
         * 设置缩进
         * @param area
         * @param indent
         */
        setTextIndent:function (dom,commandOptions,area) {
            return this._cellAreaRecordsIter(area,function (record,cellElem,rowIndex,colIndex) {
                //值校验
                cellElem.css('textIndent',commandOptions.value);
            });
        },
        /**
         * 单元格文本对齐
         * @param align
         */
        setAlign:function (dom,commandOptions,area) {
            return this._cellAreaRecordsIter(area,function (record,cellElem,rowIndex,colIndex) {
                cellElem.css('textAlign',commandOptions.value);
            });
        },
        /**
         * 单元格文本颜色
         * @param color
         */
        setColor:function (dom,commandOptions,area) {
            return this._cellAreaRecordsIter(area,function (record,cellElem,rowIndex,colIndex) {
                cellElem.css('color',commandOptions.value);
            });
        },

        /**
         * 单元格背景颜色
         * @param color
         */
        setBgColor:function (dom,commandOptions,area) {
            return this._cellAreaRecordsIter(area,function (record,cellElem,rowIndex,colIndex) {
                cellElem.css('backgroundColor',commandOptions.value);
            });
        },

        setFontSize:function (dom,commandOptions,area) {
            return this._cellAreaRecordsIter(area,function (record,cellElem,rowIndex,colIndex) {
                cellElem.css('fontSize',commandOptions.value);
            });
        },

        setSequence:function (dom,commandOptions,area) {
            var index = 0;
            return this._cellAreaRecordsIter(area,function (record,cellElem,rowIndex,colIndex) {
                cellElem.text(++index);
            });
        },
        /**
         * 单元格边框（commandOptions.value）
         *
         * outerBorder 外边框
         * 1,1,1,1 全边框
         * 0,0,0,0 无边框
         * 1,0,0,0 上边框
         * 0,1,0,0 右边框
         * 0,1,0,0 下边框
         * 0,1,0,0 左边框
         * @param dom
         * @param commandOptions
         * @param area
         */
        setBorder:function (dom,commandOptions,area) {
            //扩展区域
            //if(startCol>1)
            //if(startRow>0)
            var extendedArea = $.extend({},area,{
                startRow:(area.startRow>0?area.startRow-1:area.startRow),
                startCol:(area.startCol>1?area.startCol-1:area.startCol)
            });

            extendedArea.cols = extendedArea.endCol - extendedArea.startCol +1;
            extendedArea.rows = extendedArea.endRow - extendedArea.startRow +1;

            if('outerBorder'===commandOptions.value){
                return this._setOuterBorder(area,extendedArea);
            }

            //上边框 和 左边框取上一个单元格的下边框和 右边框
            var values = commandOptions.value.split(',');
            return this._cellAreaRecordsIter(extendedArea,function (record,cellElem,rowIndex,colIndex) {
                if(rowIndex===area.startRow&&colIndex===area.startCol-1){
                    return;
                }else if(rowIndex===area.startRow){
                    // 补充区域上边框
                    if(values[0]==='1'){
                        cellElem.css('borderBottomColor','black');
                    }else if(commandOptions.value=='0,0,0,0'){
                        cellElem.css('borderBottomColor','');
                    }
                }else if(colIndex===area.startCol-1&&rowIndex>area.startRow){
                    if(values[3]=='1'){
                        cellElem.css('borderRightColor','black');
                    }else if(commandOptions.value=='0,0,0,0'){
                        cellElem.css('borderRightColor','');
                    }
                }else if(rowIndex>area.startRow-1&&colIndex>area.startCol-1){
                    var cssOptions = {
                        borderRightColor:'',
                        borderBottomColor:''
                    };

                    if(values[0]==='1'&&extendedArea.startRow!=rowIndex&&(commandOptions.value=='1,0,0,0'&&area.endRow>=rowIndex)){//上
                        cssOptions.borderBottomColor = 'black';//写上边框
                    }
                    if(values[1]==='1'){//右
                        cssOptions.borderRightColor = 'black';
                    }
                    if(values[2]==='1'){//下
                        cssOptions.borderBottomColor = 'black';
                    }
                    if(values[3]==='1'&&extendedArea.startCol!=colIndex&&(commandOptions.value=='0,0,0,1'&&area.endCol>colIndex)){//左
                        cssOptions.borderRightColor = 'black';
                    }
                    cellElem.css(cssOptions);
                }
            });
        },
        /**
         * 设置外边框
         * @param area
         * @param extendedArea
         * @returns {*|Array}
         * @private
         */
        _setOuterBorder:function (area,extendedArea) {
            var color = 'black';
            return this._cellAreaRecordsIter(extendedArea,function (record,cellElem,rowIndex,colIndex) {
                if(colIndex>area.startCol-1&&(rowIndex === area.startRow||rowIndex === area.endRow+1)){
                    //上边框
                    cellElem.css('borderBottomColor',color);
                }

                if(rowIndex>area.startRow&&((colIndex === extendedArea.startCol&&area.startCol!=extendedArea.startCol)||colIndex === extendedArea.endCol)){
                    //左边框
                    cellElem.css('borderRightColor',color);
                }
            });
        },

        _selectedAreaIter:function (callback) {
            var firstCell = $('.selected.cell:first',this.tableDom),
                lastCell = $('.selected.cell:last',this.tableDom);

            return this._cellAreaIter(firstCell,lastCell,callback);
        },

        /**
         * 构建xml
         * @returns {string}
         */
        buildXml:function(datas){
            var xml = [],
                colWidths = [];
            //保存列宽度
            this.element.find('thead .colHeader').not('.pubHeader').each(function () {
                colWidths.push($(this).outerWidth());
            });

            datas = $.extend({},datas,{
                colWidths:colWidths.join()
            });

            //var conut = 1, lastCount = 1;//计数器，记录最后一行有数据的行数
            xml.push('<tbody ');

            for(var iProp in datas){
                if(datas[iProp]){
                    xml.push('data-'+iProp+'="'+datas[iProp]+'" ');
                }
            }

            xml.push('>');

            this.element.find('.rowTr').each(function(){
                var rowXml = [];
                var notNull = false;

                $(this).find('.cell').each(function(){
                    rowXml.push(_buildCellXml($(this)));
                });

                if(rowXml.length) {
                    xml.push('<tr class="rowTr">');
                    xml = xml.concat(rowXml);
                    xml.push('</tr>');
                }
            });
            xml.push('</tbody>');
            return xml.join('');
        },

        _isEmptyRow:function(rowElem){
            if($(rowElem.text())){
                return false;
            }

            return true;
        },
        //
        _buildColHeaders:function(cols,colWidths){
            var htmls = [],colWidths = colWidths||[];

            htmls.push('<thead><tr><th style="width:25px;" class="pubHeader">&nbsp;</th>');
            for(var i=0;i<cols;i++){
                htmls.push('<th class="colHeader" style="width:'+(colWidths[i]||100)+'px">'+_num2SheetChar(i+1)+' <span class="col-resize-handler pull-right"></span></th>');
            }
            htmls.push('</tr></thead>');
            return htmls.join('');
        },
        //
        _buildBody:function(datas,rows,cols){
            var htmls = ['<tbody>'];
            for(var row=0;row<rows;row++){
                htmls.push('<tr class="rowTr"><td class="rowHeader">'+(row+1)+'</td>');
                for(var col=0;col<cols;col++){
                    htmls.push('<td class="cell"></td>');
                }
                htmls.push('</tr>');
            }
            htmls.push('</tbody>');
            return htmls.join('');
        },

        /**
         * 计算table行列是否对齐，没有则补齐
         */
        rebuildTable:function(){
            var maxTrLength = 0;
            this.element.find('table').find('tr').each(function(){
                var trLength = $(this)[0].cells.length;
                if(trLength > maxTrLength){
                    maxTrLength = trLength
                }
            })

            var cellHtml = this._buildCell('');
            this.element.find('table').find('tr').each(function(){
                var trLength = $(this)[0].cells.length;
                var count = maxTrLength - trLength;
                var cellIndex = trLength - 1
                while(count > 0){
                    $(this).find('th:eq(' + cellIndex + ')').each(function(){
                        $(this).after('<th class="colHeader"></th>')
                    })
                    $(this).find('td:eq(' + cellIndex + ')').each(function(){
                        $(this).after(cellHtml)
                    })
                    count -= 1
                }
            })
            this.removeLastEmptyCol();
            this._reBuildRowHeader();
            this._reBuildColHeader();
        },

        _removeLastEmptyRow:function(){
            var lastEmptyIndex = this.getLastEmptyRowIndex();
            var rowLength = this.element.find('table')[0].rows.length;
            while(rowLength > lastEmptyIndex){
                this.element.find('table').find('tr:eq(' + rowLength + ')').each(function(){
                    $(this).remove();
                })
                rowLength -= 1;
            }
        },

        removeLastEmptyCol:function(){
            var lastEmptyIndex = this.getLastEmptyColIndex();
            var cols = this.element.find('table').find('.rowHeader').length
            while(lastEmptyIndex < cols){
                cols -= 1;
                this.element.find('table').find('tr').find('th:eq(' + cols + ')').each(function(){
                    $(this).remove();
                })
                this.element.find('table').find('tr').find('td:eq(' + cols + ')').each(function(){
                    $(this).remove();
                })
            }
        },

        getLastEmptyRowIndex:function(){
            var conut = 1, lastCount = 1;//计数器，记录最后一行有数据的行数
            this.element.find('.rowTr').each(function(){
                var notNull = false;
                $(this).find('.cell').each(function(){
                    if($(this).data('group')){
                        notNull = true;
                    }
                    if($(this).data('expression')){
                        notNull = true;
                    }
                    if($(this).text()){
                        notNull = true;
                    }
                });

                if(notNull){
                    lastCount = conut;
                }

                conut += 1;
            });
            return lastCount;
        },

        getLastEmptyColIndex:function(){
            var lastCount = 1;//计数器，记录最后一列有数据的列数
            this.element.find('.rowTr').each(function(){
                var notNull = false;
                var count = 1
                $(this).find('.cell').each(function(){
                    if($(this).data('group')){
                        notNull = true;
                    }
                    if($(this).data('expression')){
                        notNull = true;
                    }
                    if($(this).text()){
                        notNull = true;
                    }
                    if(notNull){
                        count = $(this)[0].cellIndex + 1;
                    }
                });

                if(lastCount < count){
                    lastCount = count;
                }
            });
            return lastCount;
        },

        _reBuildRowHeader:function(){
            this.element.find('table').find('.rowHeader').each(function(index){
                $(this).text(index+1);
            })
        },

        _reBuildColHeader:function(){
            this.element.find('table').find('.colHeader').not('.pubHeader').each(function(index){
                $(this).html(_num2SheetChar(index+1)+'<span class="col-resize-handler pull-right"></span>');
            });
        },

        _destroy:function(){
            //调用页面销毁函数
            this.tableDom = null;
            this.pasteHelper&&this.pasteHelper.remove();
            this.pasteHelper=null;
        },

        setEditable:function(editable){
            this.options.editable = editable;
        },

        getEditable:function() {
            return this.options.editable;
        },

        xmenu_paste:function (dom,commandOptions) {
            this.pasteHelper.trigger('paste');
        },

        _resize:function () {
            //var height = this.element.offsetParent().height();
            //this.element.height(height);
        }

    }));

    /**
     *  数字转26个字母进制
     */
    function _num2SheetChar(num){
        var str="",sheetNum = num;
        while (sheetNum > 0){
            var m = sheetNum % 26;
            if (m == 0){
                m = 26;
            }
            str = String.fromCharCode(m + 64) + str;
            sheetNum = (sheetNum - m) / 26;
        }
        return str;
    }
    /**
     *  26个字母进制转数字
     */
    function _sheetChar2Num(str) {
        var n = 0;
        var s = str.match(/./g);//求出字符数组
        var j = 0;
        for (var i = str.length - 1, j = 1; i >= 0; i--, j *= 26) {
            var c = s[i].toUpperCase();
            if (c < 'A' || c > 'Z') {
                return 0;
            }
            n += (c.charCodeAt(0) - 64) * j;
        }
        return n;
    }

    /**
     * 构建单元格xml
     * @param cellElem
     * @returns {string}
     * @private
     */
    function  _buildCellXml(cellElem) {
        var record = _getCellRecord(cellElem);
        return _buildCellHtml(record);
    }

    function _buildCellHtml(record){
        var xml = [],
            text = record.text;//单元格模型

        delete record.text;
        delete record.rowIndex;
        delete record.colIndex;

        xml.push('<td ');//tag 开始

        for(var attrName in record.datas){
            $.youi.stringUtils.notEmpty(record.datas[attrName])&&xml.push('data-'+$.youi.stringUtils.convertDataProperty(attrName)+'="'+record.datas[attrName]+'" ');
        }

        for(var attrName in record){
            if(typeof(record[attrName])==='string'){
                $.youi.stringUtils.notEmpty(record[attrName])&&xml.push(attrName+'="'+record[attrName]+'" ');
            }
        }

        xml.push('>');//tag开始的结束符

        xml.push(text||'');//写入文本
        xml.push('</td>');//tag结束

        return xml.join('');
    }

    /**
     * 获取单元格数据
     * @param cellElem
     * @returns {{id, text: *, class, rowspan, colspan, datas}}
     * @private
     */
    function _getCellRecord (cellElem) {
        var record = {
                id:cellElem.attr('id'),//id
                text : cellElem.text()||cellElem.find('input:first').val(),//文本
                'class':cellElem[0].className,//class
                rowspan:cellElem.attr('rowspan'),//
                colspan:cellElem.attr('colspan'),//
                datas:{
                    'mergeId':cellElem.attr('data-merge-id'),
                    'expression':cellElem.attr('data-expression')
                }//data-  属性数据
            },
            cellCss = {
                textIndent:'0px',
                fontSize:'14px',
                color:'rgb(51, 51, 51)',
                backgroundColor:'rgba(0, 0, 0, 0)',
                textAlign:'start',
                borderTopColor:'rgb(192, 192, 192)',
                borderRightColor:'rgb(192, 192, 192)',
                borderBottomColor:'rgb(192, 192, 192)',
                borderLeftColor:'rgb(192, 192, 192)'};//默认样式

        cellElem.removeClass('selected');

        var styles = [];
        for(var attrName in cellCss){
            var cssValue = cellElem.css(attrName);
            if(cssValue&&cellCss[attrName]!=cssValue){
                styles.push($.youi.stringUtils.convertDataProperty(attrName)+':'+cssValue);
            }
        }

        if(styles.length){
            record.style = styles.join(';')+';';
        }

        cellElem[0].className = record['class'];

        return record;
    }

    /**
     * 构建下拉框
     * @private
     */
    function _buildDropdownSelect(item,convertName,converts) {
        var htmls = [];

        htmls.push('<span class="editor select-value" data-value="'+item.value+'" data-toggle="dropdown" aria-expanded="true">'+(item.text||'')+'</span>');
        htmls.push('<ul class="dropdown-menu">');

        htmls.push( '<li class="option-item" data-value="">请选择</li>');
        if(converts&&convertName&&converts[convertName]){
            var convertKey = [];
            $.each(converts[convertName], function(key, val) {  convertKey[convertKey.length] = key;  });
            convertKey.sort(sequence);
            $.each(convertKey, function(i, key) {
                htmls.push( '<li class="option-item" data-value="'+key+'">'+key+' '+(converts[convertName][key]||'')+'</li>');
            });
        }

        htmls.push('</ul>');
        return htmls.join('');
    }

    /**
     * "0" "1" "2" 排序
     * @param a
     * @param b
     * @returns {number}
     */
    function sequence(a,b){
        return a - b;
    }

    function _buildCheckboxGroup(item,convertName,converts){
        var htmls = [];
        //htmls.push('<ul>');
        if(converts&&convertName&&converts[convertName]){
            for(var value in converts[convertName]){
                htmls.push( '<span class="option-item youi-icon icon-check" data-value="'+value+'">'+(converts[convertName][value]||'')+'</span>');
            }
        }
        //htmls.push('</ul>');
        return htmls.join('');
    }

    function _buildFieldRadioGroup(item,convertName,converts){
        var htmls = [];
        //htmls.push('<ul>');
        if(converts&&convertName&&converts[convertName]){
            for(var value in converts[convertName]){
                htmls.push( '<span class="option-item radio-item youi-icon icon-circle-blank" data-value="'+value+'">'+(converts[convertName][value]||'')+'</span>');
            }
        }
        //htmls.push('</ul>');
        return htmls.join('');
    }

})(jQuery);